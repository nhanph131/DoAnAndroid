package com.nhanph.doanandroid.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

import lombok.Data;

@Data
@Entity(tableName = "pin")
public class Pin {
    @PrimaryKey
    @NonNull
    String id = UUID.randomUUID().toString();

    String title;
    String description;
    String imageUrl;
    boolean publish;
    boolean commentAllow;
    String createdAt;

    String userId;

}
