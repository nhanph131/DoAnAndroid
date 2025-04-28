package com.nhanph.doanandroid.data.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

import lombok.Data;


@Data
@Entity(tableName = "user")
public class User {
    @NonNull
    @PrimaryKey
    String id = UUID.randomUUID().toString();

    String username;
    String password;
    String nickname;

    String avatarUrl;
    String email;
    String bio;

    String createdAt;
}
