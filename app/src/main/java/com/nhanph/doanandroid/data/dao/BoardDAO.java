package com.nhanph.doanandroid.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.nhanph.doanandroid.data.entities.Board;

import java.util.List;

@Dao
public interface BoardDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBoard(Board board);

    @Delete
    void deleteBoard(Board board);

    @Update
    void updateBoard(Board board);

    @Query("SELECT * FROM board")
    LiveData<List<Board>> getAllBoards();  // Thêm LiveData<>

    @Query("SELECT * FROM board WHERE id = :id")
    Board getBoardById(int id);

    @Query("SELECT * FROM board WHERE userId = :userId")
    LiveData<List<Board>> getBoardsByUserId(String userId);
    @Query("SELECT * FROM board WHERE userId = :userId")
    List<Board> getBoardsByUserIdSync(String userId); // Cho background thread

}
