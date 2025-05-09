package com.nhanph.doanandroid.data.models;

import com.nhanph.doanandroid.data.entities.Pin;

public class PinLongClickState {
    public final boolean visible;
    public final Pin pin;
    public final float x;
    public final float y;

    public PinLongClickState(boolean visible, Pin pin, float x, float y) {
        this.visible = visible;
        this.pin = pin;
        this.x = x;
        this.y = y;
    }
}
