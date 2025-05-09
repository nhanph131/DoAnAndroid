package com.nhanph.doanandroid.data.apiservice.response.user;

import lombok.Data;

@Data
public class UserLoginResponse {
    private String token;

    private String userId;

    private String username;
    private String nickname;

    private String avatarUrl;
    private String email;
    private String bio;

    private String createdAt;
}
