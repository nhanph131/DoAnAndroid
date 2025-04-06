package com.nhanph.doanandroid.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.nhanph.doanandroid.data.entities.Follow;
@Dao
public interface FollowDAO {
    @Insert
    void insertFollow(Follow follow);

    @Delete
    void deleteFollow(Follow follow);

    @Query("DELETE FROM follows WHERE follower_id = :followerId AND followed_id = :followedId")
    void unfollow(int followerId, int followedId);

    @Query("SELECT COUNT(*) FROM follows WHERE follower_id = :userId")
    int getFollowingCount(int userId);

    @Query("SELECT COUNT(*) FROM follows WHERE followed_id = :userId")
    int getFollowersCount(int userId);

    @Query("SELECT EXISTS(SELECT 1 FROM follows WHERE follower_id = :followerId AND followed_id = :followedId LIMIT 1)")
    boolean isFollowing(int followerId, int followedId);
}
