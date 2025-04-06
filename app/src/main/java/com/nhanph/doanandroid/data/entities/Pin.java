package com.nhanph.doanandroid.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

import lombok.Data;

@Data
@Entity(tableName = "pin")
public class Pin {
    @PrimaryKey
    String id;

    String title;
    String description;
    String imageUrl;
    boolean publish;
    boolean commentAllow;
    LocalDate createdAt;

    String userId;

}
