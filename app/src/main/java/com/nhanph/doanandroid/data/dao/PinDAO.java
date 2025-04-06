package com.nhanph.doanandroid.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nhanph.doanandroid.data.entities.Pin;
import java.util.List;

@Dao
public interface PinDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPin(Pin pin);

    @Delete
    void deletePin(Pin pin);

    @Query("DELETE FROM pin WHERE id = :id")
    void deletePinById(int id);

    @Query("SELECT * FROM pin WHERE id = :id")
    Pin getPinById(int id);

    @Query("SELECT * FROM pin")
    List<Pin> getAllPins();

    @Query("SELECT * FROM pin WHERE userId = :userId")
    List<Pin> getPinsByUserId(int userId);
}
