package com.nhanph.doanandroid.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

import lombok.Data;

@Data
@Entity(tableName = "board")
public class Board {
    @PrimaryKey
    String id;

    String name;
    boolean publish;
    String thumbnailUrl;
    LocalDate createdAt;

    String userId;
}
