package com.nhanph.doanandroid.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

import lombok.Data;

@Data
@Entity(tableName = "board")
public class Board {
    @NonNull
    @PrimaryKey()
    String id = UUID.randomUUID().toString();

    String name;
    boolean publish;
    String thumbnailUrl;
    String createdAt;

    String userId;

}
