package com.nhanph.doanandroid.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.view.home.HomeActivity;

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

        checkLogin();
    }

    private void checkLogin() {
        if (MainApplication.getUid() != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void initEvent(){
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        btnSignUp.setOnClickListener(v -> {
            // Xử lý sự kiện khi người dùng nhấn nút đăng ký
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
