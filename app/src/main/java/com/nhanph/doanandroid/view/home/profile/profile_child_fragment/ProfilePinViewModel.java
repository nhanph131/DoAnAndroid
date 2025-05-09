package com.nhanph.doanandroid.view.home.profile.profile_child_fragment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.data.apiservice.response.pin.PinResponse;
import com.nhanph.doanandroid.data.apiservice.retrofit.ResponseApi;
import com.nhanph.doanandroid.data.entities.Pin;
import com.nhanph.doanandroid.utility.helper.ListPinActionHelper;
import com.nhanph.doanandroid.utility.validator.AppNotificationCode;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Getter
public class ProfilePinViewModel extends ViewModel {
    private final MutableLiveData<List<Pin>> listPin = new MutableLiveData<>();

    private final MutableLiveData<AppNotificationCode> notification = new MutableLiveData<>();

    public void getList(){
        ListPinActionHelper.getSavedCreatedPins(MainApplication.getUid(), new ListPinActionHelper.OnPinListLoaded() {
            @Override
            public void onSuccess(List<Pin> pins, AppNotificationCode code) {
                listPin.setValue(pins);
                notification.setValue(code);
            }
            @Override
            public void onError(AppNotificationCode code) {
                notification.setValue(code);
            }
        });
    }

}
