package com.nhanph.doanandroid.view.login;
import static com.nhanph.doanandroid.utility.validator.AppNotificationCode.*;
import static com.nhanph.doanandroid.utility.validator.AppNotificationCode.EMPTY_CONFIRM_PASSWORD;
import static com.nhanph.doanandroid.utility.validator.AppNotificationCode.EMPTY_PASSWORD;
import static com.nhanph.doanandroid.utility.validator.AppNotificationCode.EMPTY_USERNAME;
import static com.nhanph.doanandroid.utility.validator.AppNotificationCode.INVALID_PASSWORD;
import static com.nhanph.doanandroid.utility.validator.AppNotificationCode.INVALID_USERNAME;
import static com.nhanph.doanandroid.utility.validator.AppNotificationCode.PASSWORD_NOT_MATCH;
import static com.nhanph.doanandroid.utility.validator.AppNotificationCode.REGISTER_SUCCESS;
import static com.nhanph.doanandroid.utility.validator.AppNotificationCode.USER_EXISTED;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.nhanph.doanandroid.R;
import com.nhanph.doanandroid.databinding.ActivitySignupBinding;
import android.view.animation.AnimationUtils;


public class SignUpActivity extends AppCompatActivity {

     private SignUpViewModel viewModel;
    private ActivitySignupBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initEvent();
    }

    private void initView() {
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        registerNotification();
    }

    private void initEvent() {
        binding.btnUnseenPassword.setOnClickListener(v ->
                togglePasswordVisibility(binding.edtPassword, binding.btnUnseenPassword));

        binding.btnUnseenConfPassword.setOnClickListener(v ->
                togglePasswordVisibility(binding.edtConfirmPassword, binding.btnUnseenConfPassword));

        binding.btnRegister.setOnClickListener(v -> {
            String nickname = binding.edtName.getText().toString();
            String username = binding.edtUsername.getText().toString();
            String password = binding.edtPassword.getText().toString();
            String confirmPassword = binding.edtConfirmPassword.getText().toString();

            viewModel.signUp(nickname, username, password, confirmPassword);
        });
    }

    private void registerNotification() {
        viewModel.getNotification().observe(this, notification -> {
            switch (notification) {
                case REGISTER_SUCCESS:
                    Toast.makeText(this, notification.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;

                case EMPTY_USERNAME:
                case INVALID_USERNAME:
                case USER_EXISTED:
                    binding.edtUsername.requestFocus();
                    binding.edtUsername.setError(notification.getMessage());
                    binding.edtUsername.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
                    break;

                case EMPTY_PASSWORD:
                case INVALID_PASSWORD:
                    binding.edtPassword.requestFocus();
                    binding.edtPassword.setError(notification.getMessage());
                    binding.edtPassword.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
                    break;

                case EMPTY_CONFIRM_PASSWORD:
                case PASSWORD_NOT_MATCH:
                    binding.edtConfirmPassword.requestFocus();
                    binding.edtConfirmPassword.setError(notification.getMessage());
                    binding.edtConfirmPassword.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
                    break;

                default:
                    Toast.makeText(this, notification.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }

        });
    }

    public void togglePasswordVisibility(EditText editText, ImageView imageView) {
        Typeface currentTypeface = editText.getTypeface();
         if ((editText.getInputType() & InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            imageView.setImageResource(R.drawable.visibility_off);
        } else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            imageView.setImageResource(R.drawable.visibility);
        }
        editText.setTypeface(currentTypeface);
        editText.setSelection(editText.getText().length());
    }
}
