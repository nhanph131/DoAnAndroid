package com.nhanph.doanandroid.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.nhanph.doanandroid.data.entities.Like;
@Dao
public interface LikeDAO {
    @Insert
    void insertLike(Like like);

    @Delete
    void deleteLike(Like like);

    @Query("DELETE FROM likes WHERE user_id = :userId AND pin_id = :pinId")
    void deleteLike(int userId, int pinId);

    @Query("SELECT COUNT(*) FROM likes WHERE pin_id = :pinId")
    int getLikeCount(int pinId);

    @Query("SELECT EXISTS(SELECT 1 FROM likes WHERE user_id = :userId AND pin_id = :pinId LIMIT 1)")
    boolean isLiked(int userId, int pinId);
}
