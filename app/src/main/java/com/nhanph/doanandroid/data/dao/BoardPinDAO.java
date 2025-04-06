package com.nhanph.doanandroid.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nhanph.doanandroid.data.entities.BoardPin;

import java.util.List;

@Dao
public interface BoardPinDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBoardPin(BoardPin boardPin);

    @Delete
    void deleteBoardPin(BoardPin boardPin);

    @Query("SELECT * FROM board_pin WHERE boardId = :boardId")
    List<BoardPin> getAllBoardPinByBoardId(int boardId);

    @Query("SELECT * FROM board_pin WHERE pinId = :pinId")
    List<BoardPin> getAllBoardPinByPinId(int pinId);
}
