package com.example.doanandroid.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import java.util.Date;

@Entity(
        tableName = "follows",
        primaryKeys = {"follower_id", "followed_id"}, // Khóa chính composite
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "user_id",
                        childColumns = "follower_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "user_id",
                        childColumns = "followed_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index("follower_id"),
                @Index("followed_id")
        }
)
public class Follow {
    @ColumnInfo(name = "follower_id")
    private int followerId; // Người theo dõi (tham chiếu tới User)

    @ColumnInfo(name = "followed_id")
    private int followedId; // Người được theo dõi (tham chiếu tới User)

    @ColumnInfo(name = "followedAt")
    private Date followedAt; // Thời điểm theo dõi

    // Constructor
    public Follow(int followerId, int followedId) {
        this.followerId = followerId;
        this.followedId = followedId;
        this.followedAt = new Date(); // Tự động gán thời gian hiện tại
    }

    // Getters and Setters
    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public int getFollowedId() {
        return followedId;
    }

    public void setFollowedId(int followedId) {
        this.followedId = followedId;
    }

    public Date getFollowedAt() {
        return followedAt;
    }

    public void setFollowedAt(Date followedAt) {
        this.followedAt = followedAt;
    }
}