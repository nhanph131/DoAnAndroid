package com.nhanph.doanandroid.view.home.CreateNew;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.data.entities.Board;
import com.nhanph.doanandroid.view.home.HomeActivity;
import com.nhanph.doanandroid.view.home.CreateNew.BoardViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateBoardActivity extends AppCompatActivity {

    private EditText edtBoardName;
    private CheckBox cbPublish;
    private Button btnCreate;
    private BoardViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_board);

        viewModel = new BoardViewModel(getApplication());

        initViews();
        setupListeners();
        setupObservers();
    }

    private void initViews() {
        edtBoardName = findViewById(R.id.edt_board_name);
        cbPublish = findViewById(R.id.cb_publish);
        btnCreate = findViewById(R.id.btn_create_board);
    }

    private void setupListeners() {
        btnCreate.setOnClickListener(v -> createBoard());
    }

    private void setupObservers() {
        viewModel.getCreatedBoard().observe(this, board -> {
            if (board != null) {
                // Hiển thị thông tin board vừa tạo
                String message = String.format("Đã tạo board: %s\nPublic: %s\nNgày tạo: %s",
                        board.getName(),
                        board.isPublish() ? "Có" : "Không",
                        board.getCreatedAt());

                Toast.makeText(this, message, Toast.LENGTH_LONG).show();

                // Quay về HomeActivity
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("new_board_id", board.getId()); // Gửi ID board mới tạo
                startActivity(intent);
            }
        });
    }

    private void createBoard() {
        String name = edtBoardName.getText().toString().trim();

        if (name.isEmpty()) {
            edtBoardName.setError("Board name cannot be empty");
            return;
        }

        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        String userId = MainApplication.getUid();

        if (userId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Board board = new Board();
        board.setName(name);
        board.setPublish(cbPublish.isChecked());
        board.setCreatedAt(currentDate);
        board.setThumbnailUrl(""); // Có thể thêm sau
        board.setUserId(userId);

        viewModel.insertBoard(board);
    }
}