package com.nhanph.doanandroid.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(
        tableName = "comments",
        foreignKeys = {
                @ForeignKey(
                        entity = Pin.class,
                        parentColumns = "pin_id",
                        childColumns = "pin_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "user_id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE
                )

        },
        indices = {@Index("pin_id")}
)
public class Comment {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "comment_id")
    private int commentId;

    @ColumnInfo(name = "pin_id")
    private int pinId; // Khóa ngoại tham chiếu tới Pin

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "user_id")
    private int userId; // Khóa ngoại tham chiếu tới User

    @ColumnInfo(name = "created_at")
    private String createdAt; // Thời điểm tạo comment

    // Constructor
    public Comment(int pinId,int userId, String content) {
        this.pinId = pinId;
        this.userId = userId;
        this.content = content;
        Date k = new Date();
        this.createdAt = k.toString();
    }

    // Getters and Setters
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getPinId() {
        return pinId;
    }

    public void setPinId(int pinId) {
        this.pinId = pinId;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
