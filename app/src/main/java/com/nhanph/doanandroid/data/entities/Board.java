package com.nhanph.doanandroid.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(
        tableName = "boards",
        foreignKeys = @ForeignKey(
                entity = User.class, // Tham chiếu tới bảng User
                parentColumns = "user_id", // Khóa chính của bảng User
                childColumns = "user_id", // Khóa ngoại trong bảng Board
                onDelete = ForeignKey.CASCADE // Xóa board khi user bị xóa
        ),
        indices = {@Index("user_id")} // Đánh index cho khóa ngoại để tối ưu query
)
public class Board {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "board_id")
    private int boardId;

    @ColumnInfo(name = "user_id")
    private int userId; // Khóa ngoại tham chiếu tới User

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "des")
    private String description;

    @ColumnInfo(name = "is_private")
    private boolean isPrivate;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    // Constructor
    public Board(int userId, String title, String description, boolean isPrivate) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.isPrivate = isPrivate;
        this.createdAt = new Date(); // Tự động gán thời gian tạo
    }

    // Getters and Setters
    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
