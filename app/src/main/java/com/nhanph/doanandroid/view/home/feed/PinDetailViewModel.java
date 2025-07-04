package com.nhanph.doanandroid.view.home.feed;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.data.entities.Pin;
import com.nhanph.doanandroid.utility.helper.ListPinActionHelper;
import com.nhanph.doanandroid.utility.helper.PinActionHelper;
import com.nhanph.doanandroid.utility.validator.AppNotificationCode;

import java.util.List;

import lombok.Getter;

@Getter
public class PinDetailViewModel extends ViewModel {
    private final MutableLiveData<List<Pin>> listPin = new MutableLiveData<>();

    private final MutableLiveData<AppNotificationCode> notification = new MutableLiveData<>();

    public void getList(){
        ListPinActionHelper.getFeedPins(MainApplication.getUid(), new ListPinActionHelper.OnPinListLoaded() {
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

    public void savePin(Pin pin){
        PinActionHelper.savePin(pin, notification);
    }

    public void unsavePin(Pin pin){
        PinActionHelper.unsavePin(pin, notification);
    }
}
