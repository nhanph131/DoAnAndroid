package com.nhanph.doanandroid.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.databinding.ActivityLoginBinding;
import com.nhanph.doanandroid.view.home.HomeActivity;

import android.text.InputType;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initEvent();
    }

    private void initView() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        registerNotification();
    }

    private void initEvent() {
        binding.btnUnseenPassword.setOnClickListener(v -> togglePasswordVisibility(binding.edtPassword, binding.btnUnseenPassword));

        binding.btnLogin.setOnClickListener(v -> {
            String username = binding.edtUsername.getText().toString();
            String password = binding.edtPassword.getText().toString();
            viewModel.login(username, password);
        });

        binding.txvSignup.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });
    }

    private void registerNotification(){
        viewModel.getNotification().observe(this, notification -> {
            switch (notification) {
                case LOGIN_SUCCESS:
                    Toast.makeText(this, notification.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                    break;

                case EMPTY_USERNAME:
                case INVALID_USERNAME:
                    binding.edtUsername.requestFocus();
                    binding.edtUsername.setError(notification.getMessage());
                    break;

                case EMPTY_PASSWORD:
                case INVALID_PASSWORD:
                    binding.edtPassword.requestFocus();
                    binding.edtPassword.setError(notification.getMessage());
                    break;

                default:
                    Toast.makeText(this, notification.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    public void togglePasswordVisibility(EditText editText, ImageView imageView) {
        if ((editText.getInputType() & InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            imageView.setImageResource(R.drawable.visibility_off);
        } else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            imageView.setImageResource(R.drawable.visibility);
        }
        editText.setSelection(editText.getText().length());
    }
}
