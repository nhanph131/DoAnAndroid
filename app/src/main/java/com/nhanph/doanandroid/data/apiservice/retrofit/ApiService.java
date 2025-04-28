package com.nhanph.doanandroid.data.apiservice.retrofit;

import com.nhanph.doanandroid.data.apiservice.request.user.UserLoginRequest;
import com.nhanph.doanandroid.data.apiservice.request.user.UserRegisterRequest;
import com.nhanph.doanandroid.data.apiservice.response.user.UserLoginResponse;
import com.nhanph.doanandroid.data.apiservice.response.user.UserRegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {
    @POST("/user/login")
    Call<ResponseApi<UserLoginResponse>> login(@Body UserLoginRequest loginRequest);

    @POST("/user/register")
    Call<ResponseApi<UserRegisterResponse>> register(@Body UserRegisterRequest registerRequest);

//    @PUT("/user/change-password")
//    Call<ResponseApi<>>

//    @PUT("/user/update-profile")

}
