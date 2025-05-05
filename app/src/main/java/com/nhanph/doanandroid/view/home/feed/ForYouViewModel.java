package com.nhanph.doanandroid.view.home.feed;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.data.apiservice.response.pin.PinResponse;
import com.nhanph.doanandroid.data.apiservice.retrofit.ResponseApi;
import com.nhanph.doanandroid.data.entities.Pin;
import com.nhanph.doanandroid.utility.validator.AppNotificationCode;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Getter
public class ForYouViewModel extends ViewModel {
    private final MutableLiveData<List<Pin>> listPin = new MutableLiveData<>();

    private final MutableLiveData<AppNotificationCode> notification = new MutableLiveData<>();

    public void getList(){
        List<Pin> p = new ArrayList<>();
        //get list pin tu db, server

        MainApplication.getApiService()
                .getFeed()
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<ResponseApi<List<PinResponse>>> call, Response<ResponseApi<List<PinResponse>>> response) {
                        if (response.isSuccessful()) {
                            ResponseApi<List<PinResponse>> res = response.body();
                            if (res != null) {
                                List<PinResponse> pinResponses = res.getData();
                                if (pinResponses != null) {
                                    for (PinResponse pinResponse : pinResponses) {
                                        Pin pin = getPin(pinResponse);
                                        p.add(pin);
                                        Log.d("APISERVICE", "onResponse: " + pinResponse.getTitle());
                                    }
                                }
                            }
                        }

                        listPin.setValue(p);
                        notification.setValue(AppNotificationCode.GET_LIST_PIN_SUCCESS);
                    }

                    @NonNull
                    private Pin getPin(PinResponse pinResponse) {
                        Pin pin = new Pin();
                        pin.setPinId(pinResponse.getPinId());
                        pin.setTitle(pinResponse.getTitle());
                        pin.setPrivacy(pinResponse.isPrivacy());
                        pin.setCommentAllow(pinResponse.isCommentAllow());
                        pin.setCreatedAt(pinResponse.getCreatedAt());
                        pin.setUserId(pinResponse.getUserId());
                        pin.setImageUrl(pinResponse.getImageUrl());
                        pin.setDescription(pinResponse.getDescription());
                        return pin;
                    }

                    @Override
                    public void onFailure(Call<ResponseApi<List<PinResponse>>> call, Throwable t) {
                        notification.setValue(AppNotificationCode.NETWORK_ERROR);
                        Log.d("APISERVICE", "onFailure: " + t.getMessage() );
                    }
                });
    }

}
