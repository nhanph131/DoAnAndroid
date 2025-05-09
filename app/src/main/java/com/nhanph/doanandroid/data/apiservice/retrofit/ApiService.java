package com.nhanph.doanandroid.data.apiservice.retrofit;

import com.nhanph.doanandroid.data.apiservice.request.pin.PinSaveRequest;
import com.nhanph.doanandroid.data.apiservice.request.user.UserLoginRequest;
import com.nhanph.doanandroid.data.apiservice.request.user.UserRegisterRequest;
import com.nhanph.doanandroid.data.apiservice.response.pin.PinDetailResponse;
import com.nhanph.doanandroid.data.apiservice.response.pin.PinResponse;
import com.nhanph.doanandroid.data.apiservice.response.user.UserLoginResponse;
import com.nhanph.doanandroid.data.apiservice.response.user.UserProfileResponse;
import com.nhanph.doanandroid.data.apiservice.response.user.UserRegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    //user auth
    @POST("user/login")
    Call<ResponseApi<UserLoginResponse>> login(@Body UserLoginRequest loginRequest);

    @POST("user/register")
    Call<ResponseApi<UserRegisterResponse>> register(@Body UserRegisterRequest registerRequest);

//    @PUT("/user/change-password")
//    Call<ResponseApi<>>

//    @PUT("/user/update-profile")

    //pin
    @GET("pin/get-feed/{userId}")
    Call<ResponseApi<List<PinResponse>>> getFeed(@Path("userId") String userId);

    @GET("pin/detail/{pinId}/{userId}")
    Call<ResponseApi<PinDetailResponse>> getPinDetail(@Path("pinId") int pinId, @Path("userId") String userId);

    @GET("pin/get-all/{userId}")
    Call<ResponseApi<List<PinResponse>>> getCreatedPinByUserId(@Path("userId") String userId);

    @GET("pin/saved-pin/{userId}")
    Call<ResponseApi<List<PinResponse>>> getSavedPinByUserId(@Path("userId") String userId);

    @GET("pin/saved-created/{userId}")
    Call<ResponseApi<List<PinResponse>>> getSavedCreatedByUserId(@Path("userId") String userId);

    @POST("pin/save")
    Call<ResponseApi<String>> savePin(@Body PinSaveRequest pinSaveRequest);

    @POST("pin/unsave")
    Call<ResponseApi<String>> unsavePin(@Body PinSaveRequest pinSaveRequest);

    //profile
    @GET("user/get-profile/{userId}")
    Call<ResponseApi<UserProfileResponse>> getProfileById(@Path("userId") String userId);
}
