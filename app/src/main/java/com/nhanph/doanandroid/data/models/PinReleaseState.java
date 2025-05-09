package com.nhanph.doanandroid.data.models;

import com.nhanph.doanandroid.data.entities.Pin;

public class PinReleaseState {
    public final Pin pin;
    public final float x;
    public final float y;

    public PinReleaseState(Pin pin, float x, float y) {
        this.pin = pin;
        this.x = x;
        this.y = y;
    }
}
