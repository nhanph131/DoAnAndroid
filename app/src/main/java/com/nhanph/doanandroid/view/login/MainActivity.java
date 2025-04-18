package com.nhanph.doanandroid.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.nhanph.doanandroid.R;

public class MainActivity extends AppCompatActivity {

    Button btnLogin, btnSignUp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    public void initView(){
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
    }

    public void initEvent(){
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        btnSignUp.setOnClickListener(v -> {
            // Xử lý sự kiện khi người dùng nhấn nút đăng ký
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}
