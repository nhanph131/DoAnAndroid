package com.nhanph.doanandroid.data.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

import lombok.Data;

@Data
@Entity(tableName = "user")
public class User {
    @PrimaryKey
    String id;

    String username;
    String nickname;
    String avatarUrl;
    String email;
    String bio;
    LocalDate createdAt;



}
