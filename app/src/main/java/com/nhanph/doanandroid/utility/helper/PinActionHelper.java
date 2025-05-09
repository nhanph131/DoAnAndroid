package com.nhanph.doanandroid.utility.helper;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.data.apiservice.request.pin.PinSaveRequest;
import com.nhanph.doanandroid.data.apiservice.retrofit.ResponseApi;
import com.nhanph.doanandroid.data.entities.Pin;
import com.nhanph.doanandroid.utility.validator.AppNotificationCode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class PinActionHelper {

    static MutableLiveData<AppNotificationCode> notification = new MutableLiveData<>();
    static AppNotificationCode successCode;

    private static final Callback<ResponseApi<String>> callback = new Callback<>() {
        @Override
        public void onResponse(Call<ResponseApi<String>> call, Response<ResponseApi<String>> response) {
            if (response.isSuccessful()) {
                ResponseApi<String> apiResponse = response.body();

                if (apiResponse != null) {
                    if (apiResponse.isSuccess()) {
                        notification.setValue(successCode);
                        Log.d("APISERVICE_PIN", "onResponse: " + apiResponse.getData());
                    } else {
                        notification.setValue(AppNotificationCode.SERVER_ERROR);
                        Log.d("APISERVICE_PIN", "Thất bại: " + apiResponse.getMessage());
                    }
                } else {
                    notification.setValue(AppNotificationCode.SERVER_ERROR);
                    Log.d("APISERVICE_PIN", "Response body null");
                }
            } else {
                notification.setValue(AppNotificationCode.SERVER_ERROR);
                Log.d("APISERVICE_PIN", "HTTP Error: " + response.code());
            }
        }

        @Override
        public void onFailure(Call<ResponseApi<String>> call, Throwable t) {
            notification.setValue(AppNotificationCode.SERVER_ERROR);
            Log.d("APISERVICE_PIN", "onFailure: " + t.getMessage());
        }
    };

    public static void savePin(Pin pin, MutableLiveData<AppNotificationCode> notif) {
        requestPinAction(pin, notif, AppNotificationCode.SAVE_PIN_SUCCESS, MainApplication.getApiService()::savePin);
    }

    public static void unsavePin(Pin pin, MutableLiveData<AppNotificationCode> notif) {
        requestPinAction(pin, notif, AppNotificationCode.UNSAVE_PIN_SUCCESS, MainApplication.getApiService()::unsavePin);
    }

    private static void requestPinAction(Pin pin,
                                         MutableLiveData<AppNotificationCode> notif,
                                         AppNotificationCode success,
                                         PinRequestAction action) {
        if (notif != null){
            notification = notif;
        }
        successCode = success;
        PinSaveRequest pinSaveRequest = createPinSaveRequest(pin);
        action.enqueue(pinSaveRequest).enqueue(callback);
    }

    private static PinSaveRequest createPinSaveRequest(Pin pin) {
        PinSaveRequest request = new PinSaveRequest();
        request.setPinId(pin.getPinId());
        request.setUserId(MainApplication.getUid());
        return request;
    }

    public interface PinRequestAction {
        Call<ResponseApi<String>> enqueue(PinSaveRequest request);
    }
}
