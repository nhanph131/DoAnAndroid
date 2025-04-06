package com.example.doanandroid.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.doanandroid.data.entity.Pin;

import java.util.List;
@Dao
public interface PinDAO {
    @Insert
    void insertPin(Pin pin);

    @Update
    void updatePin(Pin pin);

    @Delete
    void deletePin(Pin pin);

    @Query("SELECT * FROM pins WHERE pin_id = :pinId")
    Pin getPinById(int pinId);

    @Query("SELECT * FROM pins WHERE user_id = :userId")
    List<Pin> getPinsByUser(int userId);
}