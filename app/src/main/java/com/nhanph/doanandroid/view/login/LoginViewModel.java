package com.nhanph.doanandroid.view.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.data.entities.User;
import com.nhanph.doanandroid.data.roomdatabase.AppDatabase;
import com.nhanph.doanandroid.utility.validator.AppNotificationCode;
import lombok.Getter;

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

        AppDatabase.databaseWriteExecutor.execute(() -> {
            User user = MainApplication.getDatabase().getUserDAO().getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                notification.postValue(AppNotificationCode.LOGIN_SUCCESS);

                MainApplication.saveUid(user.getId());
                MainApplication.saveUsername(user.getUsername());
                MainApplication.saveAvatarUrl(user.getAvatarUrl());
                MainApplication.saveAvatarFileName();
            } else {
                notification.postValue(AppNotificationCode.LOGIN_FAILED);
            }
        });
    }
}
