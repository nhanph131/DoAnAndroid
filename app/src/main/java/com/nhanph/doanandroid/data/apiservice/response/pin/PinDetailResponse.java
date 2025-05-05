package com.nhanph.doanandroid.data.apiservice.response.pin;

import lombok.Data;

@Data
public class PinDetailResponse {
    private int pinId;
    private String pinImageUrl;

    private String username;
    private String avatarUrl;

    private int followerCount;
    private int commentCount;

    private int likeCount;
}
