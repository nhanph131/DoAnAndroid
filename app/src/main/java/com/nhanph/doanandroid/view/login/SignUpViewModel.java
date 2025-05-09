package com.nhanph.doanandroid.view.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.data.apiservice.request.user.UserRegisterRequest;
import com.nhanph.doanandroid.data.apiservice.response.user.UserRegisterResponse;
import com.nhanph.doanandroid.data.apiservice.retrofit.ResponseApi;
import com.nhanph.doanandroid.data.entities.User;
import com.nhanph.doanandroid.data.roomdatabase.AppDatabase;
import com.nhanph.doanandroid.utility.validator.AppNotificationCode;

import java.time.LocalDateTime;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpViewModel extends ViewModel {
    @Getter
    private final MutableLiveData<AppNotificationCode> notification = new MutableLiveData<>();

    public void signUp(String nickname, String username, String password, String confirmPassword) {
        if (validateData(username, password, confirmPassword)) {
            UserRegisterRequest registerRequest = new UserRegisterRequest();
            registerRequest.setNickname(nickname);
            registerRequest.setUsername(username);
            registerRequest.setPassword(password);

            MainApplication.getApiService().register(registerRequest).enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<ResponseApi<UserRegisterResponse>> call, Response<ResponseApi<UserRegisterResponse>> response) {
                    if (response.isSuccessful()) {
                        ResponseApi<UserRegisterResponse> apiResponse = response.body();

                        if (apiResponse != null) {
                            if (apiResponse.isSuccess()) {
                                UserRegisterResponse userRegisterResponse = apiResponse.getData();
                                if (userRegisterResponse != null){
                                    notification.setValue(AppNotificationCode.REGISTER_SUCCESS);
                                    Log.d("APISERVICE_REGISTER", "Đăng ký thành công. User ID: " + userRegisterResponse.getUserId());
                                }
                            } else {
                                notification.setValue(AppNotificationCode.REGISTER_FAILED);
                                Log.d("APISERVICE_REGISTER", "Đăng ký thất bại: " + apiResponse.getMessage());
                            }
                        } else {
                            notification.setValue(AppNotificationCode.REGISTER_FAILED);
                            Log.d("APISERVICE_REGISTER", "Response body null");
                        }
                    } else {
                        notification.setValue(AppNotificationCode.REGISTER_FAILED);
                        Log.d("APISERVICE_REGISTER", "HTTP Error: " + response.code());
                    }
                }


                @Override
                public void onFailure(Call<ResponseApi<UserRegisterResponse>> call, Throwable t) {
                    Log.d("APISERVICE_REGISTER", "onFailure: " + t.getMessage());
                    notification.setValue(AppNotificationCode.REGISTER_FAILED);
                }
            });
//            AppDatabase.databaseWriteExecutor.execute(() -> {
//               boolean isExists = MainApplication.getDatabase().getUserDAO().isExistsByUsername(username);
//               if (!isExists) {
//                   User user = new User();
//                   user.setNickname(nickname);
//                   user.setUsername(username);
//                   user.setPassword(password);
//                   user.setAvatarUrl("");
//                   user.setEmail("");
//                   user.setBio("This is a default bio.");
//                   user.setCreatedAt(LocalDateTime.now().toString());
//
//                   AppDatabase.databaseWriteExecutor.execute(() -> {
//                       MainApplication.getDatabase().getUserDAO().insertUser(user);
//                       notification.postValue(AppNotificationCode.REGISTER_SUCCESS);
//                   });
//               }
//               else {
//                   notification.postValue(AppNotificationCode.USER_EXISTED);
//               }
//            });
        }
        else {
            notification.setValue(AppNotificationCode.REGISTER_FAILED);
        }
    }
    public boolean validateData(String username, String password, String confirmPassword) {
        if (username.isEmpty()) {
            notification.setValue(AppNotificationCode.EMPTY_USERNAME);
            return false;
        }
        if (username.length() < 6) {
            notification.setValue(AppNotificationCode.INVALID_USERNAME);
            return false;
        }
        if (password.isEmpty()) {
            notification.setValue(AppNotificationCode.EMPTY_PASSWORD);
            return false;
        }
        if (password.length() < 6) {
            notification.setValue(AppNotificationCode.INVALID_PASSWORD);
            return false;
        }
        if (!password.equals(confirmPassword)) {
            notification.setValue(AppNotificationCode.PASSWORD_NOT_MATCH);
            return false;
        }

        return true;
    }

}
