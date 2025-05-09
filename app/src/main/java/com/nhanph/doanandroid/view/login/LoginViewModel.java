package com.nhanph.doanandroid.view.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.data.apiservice.request.user.UserLoginRequest;
import com.nhanph.doanandroid.data.apiservice.response.user.UserLoginResponse;
import com.nhanph.doanandroid.data.apiservice.response.user.UserRegisterResponse;
import com.nhanph.doanandroid.data.apiservice.retrofit.ResponseApi;
import com.nhanph.doanandroid.data.entities.User;
import com.nhanph.doanandroid.data.roomdatabase.AppDatabase;
import com.nhanph.doanandroid.utility.validator.AppNotificationCode;
import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    @Getter
    private final MutableLiveData<AppNotificationCode> notification = new MutableLiveData<>();

    public void login(String username, String password) {
        if (username.isEmpty()) {
            notification.setValue(AppNotificationCode.EMPTY_USERNAME);
            return;
        }
        if (password.isEmpty()) {
            notification.setValue(AppNotificationCode.EMPTY_PASSWORD);
            return;
        }

        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        MainApplication.getApiService().login(loginRequest).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseApi<UserLoginResponse>> call, Response<ResponseApi<UserLoginResponse>> response) {
                if (response.isSuccessful()) {
                    ResponseApi<UserLoginResponse> apiResponse = response.body();

                    if (apiResponse != null) {
                        if (apiResponse.isSuccess()) {
                            UserLoginResponse userLoginResponse = apiResponse.getData();
                            if (userLoginResponse != null) {
                                MainApplication.saveUid(userLoginResponse.getUserId());
                                MainApplication.saveUsername(userLoginResponse.getUsername());
                                MainApplication.saveNickname(userLoginResponse.getNickname());
                                MainApplication.saveAvatarUrl(userLoginResponse.getAvatarUrl());
                                MainApplication.saveToken(userLoginResponse.getToken());

                                notification.setValue(AppNotificationCode.LOGIN_SUCCESS);
                                Log.d("APISERVICE_LOGIN", "onResponse: " + userLoginResponse.getUsername() + userLoginResponse.getAvatarUrl());
                            }
                        } else {
                            notification.setValue(AppNotificationCode.LOGIN_FAILED);
                            Log.d("APISERVICE_LOGIN", "Đăng nhâp thất bại: " + apiResponse.getMessage());
                        }
                    } else {
                        notification.setValue(AppNotificationCode.LOGIN_FAILED);
                        Log.d("APISERVICE_LOGIN", "Response body null");
                    }
                } else {
                    notification.setValue(AppNotificationCode.LOGIN_FAILED);
                    Log.d("APISERVICE_LOGIN", "HTTP Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<UserLoginResponse>> call, Throwable t) {
                Log.d("APISERVICE_LOGIN", "onFailure: " + t.getMessage());
                notification.setValue(AppNotificationCode.LOGIN_FAILED);
            }
        });

//        AppDatabase.databaseWriteExecutor.execute(() -> {
//            User user = MainApplication.getDatabase().getUserDAO().getUserByUsername(username);
//            if (user != null && user.getPassword().equals(password)) {
//                notification.postValue(AppNotificationCode.LOGIN_SUCCESS);
//
//                MainApplication.saveUid(user.getId());
//                MainApplication.saveUsername(user.getUsername());
//                MainApplication.saveAvatarUrl(user.getAvatarUrl());
//                MainApplication.saveAvatarFileName();
//            } else {
//                notification.postValue(AppNotificationCode.LOGIN_FAILED);
//            }
//        });
    }
}
