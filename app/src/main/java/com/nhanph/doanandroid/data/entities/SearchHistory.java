package com.nhanph.doanandroid.data.entities;

import android.annotation.SuppressLint;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "search_history")
public class SearchHistory {
    @PrimaryKey(autoGenerate = true)
    int id;
    String keyword;
    String createdAt;

    @SuppressLint("NewApi")
    public SearchHistory(String keyword) {
        this.keyword = keyword;
        this.createdAt = LocalDateTime.now().toString();
    }
}
