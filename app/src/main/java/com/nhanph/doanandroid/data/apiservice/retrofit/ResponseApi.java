package com.nhanph.doanandroid.data.apiservice.retrofit;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

import com.google.gson.annotations.SerializedName;

@Data
public class ResponseApi<T> {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private T data;

    public ResponseApi(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return code == 400;
    }
}
