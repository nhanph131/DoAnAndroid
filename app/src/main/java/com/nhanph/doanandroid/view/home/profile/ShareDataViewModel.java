package com.nhanph.doanandroid.view.home.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nhanph.doanandroid.data.entities.Pin;

public class ShareDataViewModel extends ViewModel {
    private final MutableLiveData<Pin> selectedPin = new MutableLiveData<>();

    public LiveData<Pin> getSelectedPin() {
        return selectedPin;
    }

    public void setSelectedPin(Pin pin) {
        selectedPin.setValue(pin);
    }
}
