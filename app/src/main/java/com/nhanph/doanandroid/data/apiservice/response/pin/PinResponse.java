package com.nhanph.doanandroid.data.apiservice.response.pin;

import lombok.Data;

@Data
public class PinResponse {
    private int pinId;
    private String title;
    private String description;
    private String imageUrl;
    private boolean privacy;
    private boolean commentAllow;
    private String createdAt;
    private String userId;
}
