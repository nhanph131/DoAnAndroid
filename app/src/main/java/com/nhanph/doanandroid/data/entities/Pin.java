package com.nhanph.doanandroid.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity(tableName = "pin")
public class Pin {
    @PrimaryKey(autoGenerate = true)
    int pinId;

    String title;
    String description;
    String imageUrl;

    boolean privacy;
    boolean commentAllow;

    String createdAt;

    String userId;

}
