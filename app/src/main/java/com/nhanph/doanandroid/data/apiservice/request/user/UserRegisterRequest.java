package com.nhanph.doanandroid.data.apiservice.request.user;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String nickname;
    private String username;
    private String password;
}
