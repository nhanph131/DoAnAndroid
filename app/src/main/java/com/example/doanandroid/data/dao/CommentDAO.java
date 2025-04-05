package com.example.doanandroid.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.doanandroid.data.entity.Comment;

import java.util.List;
@Dao
public interface CommentDAO {
    @Insert
    void insertComment(Comment comment);

    @Update
    void updateComment(Comment comment);

    @Delete
    void deleteComment(Comment comment);

    @Query("SELECT * FROM comments WHERE comment_id = :commentId")
    Comment getCommentById(int commentId);

    @Query("SELECT * FROM comments WHERE pin_id = :pinId ORDER BY created_at DESC")
    List<Comment> getCommentsByPin(int pinId);
}
