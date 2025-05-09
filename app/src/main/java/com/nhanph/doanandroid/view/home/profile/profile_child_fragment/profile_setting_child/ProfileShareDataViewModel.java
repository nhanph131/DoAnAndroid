package com.nhanph.doanandroid.view.home.profile.profile_child_fragment.profile_setting_child;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nhanph.doanandroid.data.entities.Pin;

public class ProfileShareDataViewModel extends ViewModel {
    private final MutableLiveData<Pin> selectedPin = new MutableLiveData<>();

    private final MutableLiveData<String> userId = new MutableLiveData<>();

    public LiveData<Pin> getSelectedPin() {
        return selectedPin;
    }

    //cho frag create gui
    public void setSelectedCreatedPin(Pin pin) {
        selectedPin.setValue(pin);
    }

    // cho frag save gui
    public void setSelectedSavedPin(Pin pin) {
        selectedPin.setValue(pin);
    }

    public void setUserId(String uid) {
        userId.setValue(uid);
    }

    public LiveData<String> getUserId() {
        return userId;
    }
}
