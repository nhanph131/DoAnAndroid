package com.nhanph.doanandroid.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

import lombok.Data;

@Data
@Entity(tableName = "search_history")
public class SearchHistory {
    @PrimaryKey(autoGenerate = true)
    int id;
    String keyword;
    LocalDateTime createdAt;
}
