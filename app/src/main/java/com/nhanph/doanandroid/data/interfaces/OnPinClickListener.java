package com.nhanph.doanandroid.data.interfaces;

public interface OnPinClickListener {
    void onPinClick(int position);

    void onPinLongClick(int position, float x, float y);

    void onPinLongClickRelease(int position, float x, float y);

}
