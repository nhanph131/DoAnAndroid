package com.example.doanandroid.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import java.util.Date;

@Entity(
        tableName = "likes",
        primaryKeys = {"user_id", "pin_id"}, // Khóa chính composite
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "user_id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Pin.class,
                        parentColumns = "pin_id",
                        childColumns = "pin_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index("user_id"),
                @Index("pin_id")
        }
)
public class Like {
    @ColumnInfo(name = "user_id")
    private int userId; // Người like (tham chiếu tới User)

    @ColumnInfo(name = "pin_id")
    private int pinId; // Pin được like (tham chiếu tới Pin)

    @ColumnInfo(name = "liked_at")
    private Date likedAt; // Thời điểm like

    // Constructor
    public Like(int userId, int pinId) {
        this.userId = userId;
        this.pinId = pinId;
        this.likedAt = new Date(); // Tự động gán thời gian hiện tại
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPinId() {
        return pinId;
    }

    public void setPinId(int pinId) {
        this.pinId = pinId;
    }

    public Date getLikedAt() {
        return likedAt;
    }

    public void setLikedAt(Date likedAt) {
        this.likedAt = likedAt;
    }
}
