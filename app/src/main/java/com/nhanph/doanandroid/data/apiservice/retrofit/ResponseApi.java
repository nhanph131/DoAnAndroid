package com.nhanph.doanandroid.data.apiservice.retrofit;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ResponseApi<T> {

    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private T data;

    // Constructor, Getters and Setters
    public ResponseApi(int code, T data) {
        this.code = code;
        this.data = data;
    }
}
