package com.nhanph.doanandroid.data.apiservice.request.user;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String username;
    private String password;
}
