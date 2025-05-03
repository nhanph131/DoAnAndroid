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
    @PrimaryKey
    private String id = UUID.randomUUID().toString();  // 📌 ID duy nhất

    private String name;           // 📌 Tên Board
    private boolean publish;       // 📌 Trạng thái công khai
    private String thumbnailUrl;   // 📌 Ảnh thumbnail của Board
    private String createdAt;      // 📌 Ngày tạo Board
    private String userId;         // 📌 ID người dùng tạo Board

    // Setter & Getter cho các thuộc tính
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public boolean isPublish() {
        return publish;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUserId() {
        return userId;
    }
}
