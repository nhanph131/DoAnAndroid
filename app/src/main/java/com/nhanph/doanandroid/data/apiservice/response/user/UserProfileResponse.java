package com.nhanph.doanandroid.data.apiservice.response.user;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserProfileResponse {
    private String userId;

    private String nickname;

    private String avatarUrl;
    private String bio;

    private String createdAt;
}
