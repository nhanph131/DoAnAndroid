package com.nhanph.doanandroid.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.nhanph.doanandroid.data.entities.Board;

import java.util.List;
@Dao
public interface BoardDAO {
    @Insert
    void insertBoard(Board board);

    @Update
    void updateBoard(Board board);

    @Delete
    void deleteBoard(Board board);

    @Query("SELECT * FROM boards WHERE board_id = :boardId")
    Board getBoardById(int boardId);

    @Query("SELECT * FROM boards WHERE user_id = :userId")
    List<Board> getBoardsByUser(int userId);
}
