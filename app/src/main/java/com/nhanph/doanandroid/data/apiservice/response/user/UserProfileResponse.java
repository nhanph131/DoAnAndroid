package com.nhanph.doanandroid.data.apiservice.response.user;

import lombok.Data;

@Data
public class UserProfileResponse {
    private String userId;

    private String nickname;

    private String avatarUrl;
    private String bio;

    private int followerCount;
    private int followingCount;

    private String createdAt;
}
