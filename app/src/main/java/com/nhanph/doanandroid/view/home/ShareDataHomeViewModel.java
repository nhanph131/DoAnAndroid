package com.nhanph.doanandroid.view.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nhanph.doanandroid.data.entities.Pin;
import com.nhanph.doanandroid.data.models.PinLongClickState;
import com.nhanph.doanandroid.data.models.PinReleaseState;

import lombok.Getter;

@Getter
public class ShareDataHomeViewModel extends ViewModel {
    private final MutableLiveData<PinLongClickState> pinLongClickState = new MutableLiveData<>();
    private final MutableLiveData<PinReleaseState> pinReleaseState = new MutableLiveData<>();

    public void showHiddenFrame(Pin pin, float x, float y) {
        pinLongClickState.setValue(new PinLongClickState(true, pin, x, y));
    }

    public void hideHiddenFrame() {
        pinLongClickState.setValue(new PinLongClickState(false, null, 0, 0));
    }

    public void setPinReleaseState(Pin pin, float x, float y) {
        pinReleaseState.setValue(new PinReleaseState(pin, x, y));
    }

}
