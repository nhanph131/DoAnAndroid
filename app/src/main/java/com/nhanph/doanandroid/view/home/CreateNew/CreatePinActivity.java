package com.nhanph.doanandroid.view.home.CreateNew;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.data.entities.Board;
import com.nhanph.doanandroid.data.entities.Pin;
import com.nhanph.doanandroid.data.entities.User;
import com.nhanph.doanandroid.data.roomdatabase.AppDatabase;
import com.nhanph.doanandroid.view.home.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CreatePinActivity extends AppCompatActivity {
    private ImageView imgPin;
    private EditText edtTitle, edtDescription;
    private CheckBox cbPublish, cbCommentAllow;
    private Spinner spinnerBoards;
    private Button btnSelectImage, btnCreatePin;
    private PinViewModel viewModel;
    private Uri imageUri;
    private String imageUrl = "";
    private List<Board> userBoards;

    // Thay thế startActivityForResult bằng ActivityResultLauncher
    private final ActivityResultLauncher<String> pickMedia = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    imageUri = uri;
                    imgPin.setImageURI(uri);
                }
            }
    );

    interface CloudinaryCallback {
        void onSuccess(String url);
        void onError(String error);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pin);

        viewModel = new PinViewModel(getApplication());
        initViews();
        setupListeners();
        loadUserBoards();
    }

    private void initViews() {
        imgPin = findViewById(R.id.img_pin);
        edtTitle = findViewById(R.id.edt_title);
        edtDescription = findViewById(R.id.edt_description);
        cbPublish = findViewById(R.id.cb_publish);
        cbCommentAllow = findViewById(R.id.cb_comment_allow);
        spinnerBoards = findViewById(R.id.spinner_boards);
        btnSelectImage = findViewById(R.id.btn_select_image);
        btnCreatePin = findViewById(R.id.btn_create_pin);

        // Ẩn nút chụp ảnh nếu có
        Button btnTakePhoto = findViewById(R.id.btn_take_photo);
        btnTakePhoto.setVisibility(Button.GONE);
    }

    private void setupListeners() {
        btnSelectImage.setOnClickListener(v -> selectImageFromGallery());
        btnCreatePin.setOnClickListener(v -> createPin());
    }

    private void selectImageFromGallery() {
        pickMedia.launch("image/*");
    }
    private void loadUserBoards() {
        String userId = MainApplication.getUid();
        if (userId != null) {
            viewModel.getUserBoards(userId).observe(this, boards -> {
                userBoards = boards;

                // Tạo danh sách tên Board
                List<String> boardNames = new ArrayList<>();
                for (Board board : boards) {
                    boardNames.add(board.getName());
                }

                // Thêm lựa chọn "Không chọn Board" ở đầu danh sách
                boardNames.add(0, "Không chọn Board");

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        boardNames
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerBoards.setAdapter(adapter);
            });
        }
    }


    private void createPin() {
        String title = edtTitle.getText().toString().trim();
        String description = edtDescription.getText().toString().trim();

        if (title.isEmpty()) {
            edtTitle.setError("Title cannot be empty");
            return;
        }

        if (imageUri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        uploadImageToCloudinary(imageUri, new CloudinaryCallback() {
            @Override
            public void onSuccess(String url) {
                imageUrl = url;
                savePinToDatabase(title, description);
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> Toast.makeText(CreatePinActivity.this,
                        "Upload failed: " + error, Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void uploadImageToCloudinary(Uri imageUri, CloudinaryCallback callback) {
        // Lấy username từ database
        String userId = MainApplication.getUid();
        if (userId == null) {
            callback.onError("User not logged in");
            return;
        }

        AppDatabase.databaseWriteExecutor.execute(() -> {
            User currentUser = MainApplication.getDatabase().getUserDAO().getUserById(userId);
            runOnUiThread(() -> {
                if (currentUser == null) {
                    callback.onError("User not found");
                    return;
                }

                String username = currentUser.getUsername();
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                String publicId = "users/" + username + "/upload/" + username + "_" + timestamp;
        MediaManager.get().upload(imageUri)
                .option("public_id", publicId)
                .option("folder", "users/" + username + "/upload")
                .option("use_filename", true)
                .option("unique_filename", false)
                .option("overwrite", false)
                .option("resource_type", "image")
                .callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                        runOnUiThread(() ->
                                Toast.makeText(CreatePinActivity.this,
                                        "Uploading image...", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {}

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        String url = (String) resultData.get("secure_url");
                        callback.onSuccess(url);
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        callback.onError(error.getDescription());
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {
                        callback.onError(error.getDescription());
                    }
                })
                .dispatch();
            });
        });
    }




    private void savePinToDatabase(String title, String description) {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(new Date());
        String userId = MainApplication.getUid();

        if (userId == null) {
            Toast.makeText(this, "Người dùng chưa đăng nhập", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Pin pin = new Pin();
        pin.setTitle(title);
        pin.setDescription(description);
        pin.setPublish(cbPublish.isChecked());
        pin.setCommentAllow(cbCommentAllow.isChecked());
        pin.setCreatedAt(currentDate);
        pin.setUserId(userId);
        pin.setImageUrl(imageUrl);

        // Xử lý Board được chọn
        String selectedBoardName = (String) spinnerBoards.getSelectedItem();
        String boardId = null;

        if (userBoards != null && !selectedBoardName.equals("Không chọn Board")) {
            for (Board board : userBoards) {
                if (board.getName().equals(selectedBoardName)) {
                    boardId = board.getId();
                    break;
                }
            }
        }

        viewModel.insertPin(pin, boardId);

         Toast.makeText(this, "Tạo Pin   thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    }