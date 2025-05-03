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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.data.entities.Board;
import com.nhanph.doanandroid.data.entities.Pin;
import com.nhanph.doanandroid.view.home.CreateNew.PinViewModel;
import com.nhanph.doanandroid.view.home.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CreatePinActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;

    private ImageView imgPin;
    private EditText edtTitle, edtDescription;
    private CheckBox cbPublish, cbCommentAllow;
    private Spinner spinnerBoards;
    private Button btnSelectImage, btnTakePhoto, btnCreatePin;
    private PinViewModel viewModel;
    private Uri imageUri;
    private String imageUrl = "";
    private List<Board> userBoards;

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
        btnTakePhoto = findViewById(R.id.btn_take_photo);
        btnCreatePin = findViewById(R.id.btn_create_pin);
    }

    private void setupListeners() {
        btnSelectImage.setOnClickListener(v -> selectImageFromGallery());
        btnTakePhoto.setOnClickListener(v -> takePhotoWithCamera());
        btnCreatePin.setOnClickListener(v -> createPin());
    }

    private void selectImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void takePhotoWithCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    }

    private void loadUserBoards() {
        String userId = MainApplication.getUid();
        if (userId != null) {
            viewModel.getUserBoards(userId).observe(this, boards -> {
                userBoards = boards;
                ArrayAdapter<Board> adapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        boards
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
        MediaManager.get().upload(imageUri)
                .option("folder", "pins")
                .callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                        runOnUiThread(() ->
                                Toast.makeText(CreatePinActivity.this,
                                        "Uploading image...", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {
                        // Update progress if needed
                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        String url = (String) resultData.get("url");
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
    }

    private void savePinToDatabase(String title, String description) {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(new Date());
        String userId = MainApplication.getUid();

        if (userId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
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
        pin.setImageUrl(imageUrl); // Use the Cloudinary URL

        Board selectedBoard = (Board) spinnerBoards.getSelectedItem();
        viewModel.insertPin(pin, selectedBoard != null ? selectedBoard.getId() : null);

        Toast.makeText(this, "Pin created successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST && data != null) {
                imageUri = data.getData();
                imgPin.setImageURI(imageUri);
            } else if (requestCode == CAMERA_REQUEST && data != null) {
                imageUri = data.getData();
                imgPin.setImageURI(imageUri);
            }
        }
    }
}