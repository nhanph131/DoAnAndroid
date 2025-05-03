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

    @NonNull
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(@NonNull String id) {
        this.id = id;
    }

    String description;
    String imageUrl;
    boolean publish;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    boolean commentAllow;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    String createdAt;

    String userId;

    public boolean isPublish() {
        return publish;
    }

    public boolean isCommentAllow() {
        return commentAllow;
    }

    public void setCommentAllow(boolean commentAllow) {
        this.commentAllow = commentAllow;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }
}
