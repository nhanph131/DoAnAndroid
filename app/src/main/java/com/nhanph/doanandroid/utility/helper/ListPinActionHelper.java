package com.nhanph.doanandroid.utility.helper;

import android.util.Log;

import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.data.apiservice.response.pin.PinResponse;
import com.nhanph.doanandroid.data.apiservice.retrofit.ResponseApi;
import com.nhanph.doanandroid.data.entities.Pin;
import com.nhanph.doanandroid.utility.validator.AppNotificationCode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPinActionHelper {

    public interface OnPinListLoaded {
        void onSuccess(List<Pin> pins, AppNotificationCode code);
        void onError(AppNotificationCode code);
    }

    public static void getFeedPins(String userId ,OnPinListLoaded callback) {
        fetchPins(userId, callback, MainApplication.getApiService()::getFeed);
    }
    public static void getSavedCreatedPins(String userId, OnPinListLoaded callback) {
        fetchPins(userId, callback, MainApplication.getApiService()::getSavedCreatedByUserId);
    }
    public static void getSavedPins(String userId, OnPinListLoaded callback) {
        fetchPins(userId, callback, MainApplication.getApiService()::getSavedPinByUserId);
    }

    public static void getCreatedPins(String userId, OnPinListLoaded callback) {
        fetchPins(userId, callback, MainApplication.getApiService()::getCreatedPinByUserId);
    }

    private static void fetchPins(String userId,
                                  OnPinListLoaded callback,
                                  ListPinRequestAction action) {
        action.enqueue(userId).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseApi<List<PinResponse>>> call, Response<ResponseApi<List<PinResponse>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseApi<List<PinResponse>> body = response.body();
                    if (body.isSuccess() && body.getData() != null) {
                        List<Pin> pinList = new ArrayList<>();
                        for (PinResponse pinResponse : body.getData()) {
                            pinList.add(convert(pinResponse));
                        }
                        callback.onSuccess(pinList, AppNotificationCode.GET_LIST_PIN_SUCCESS);
                    } else {
                        callback.onError(AppNotificationCode.SERVER_ERROR);
                    }
                } else {
                    callback.onError(AppNotificationCode.SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<List<PinResponse>>> call, Throwable t) {
                Log.e("ListPinActionHelper", "onFailure: ", t);
                callback.onError(AppNotificationCode.NETWORK_ERROR);
            }
        });
    }

    private static Pin convert(PinResponse res) {
        Pin pin = new Pin();
        pin.setPinId(res.getPinId());
        pin.setTitle(res.getTitle());
        pin.setPrivacy(res.isPrivacy());
        pin.setCommentAllow(res.isCommentAllow());
        pin.setCreatedAt(res.getCreatedAt());
        pin.setUserId(res.getUserId());
        pin.setImageUrl(res.getImageUrl());
        pin.setDescription(res.getDescription());
        return pin;
    }

    public interface ListPinRequestAction {
        Call<ResponseApi<List<PinResponse>>> enqueue(String userId);
    }
}
