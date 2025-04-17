package com.nhanph.doanandroid.data.roomdatabase;

import androidx.room.RoomDatabase;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import com.nhanph.doanandroid.data.dao.BoardDAO;
import com.nhanph.doanandroid.data.dao.BoardPinDAO;
import com.nhanph.doanandroid.data.dao.PinDAO;
import com.nhanph.doanandroid.data.dao.UserDAO;
import com.nhanph.doanandroid.data.entities.Board;
import com.nhanph.doanandroid.data.entities.BoardPin;
import com.nhanph.doanandroid.data.entities.Pin;
import com.nhanph.doanandroid.data.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Pin.class, User.class, Board.class, BoardPin.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract UserDAO getUserDAO();
    public abstract PinDAO getPinDAO();
    public abstract BoardDAO getBoardDAO();
    public abstract BoardPinDAO getBoardPinDAO();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "doan_pinterest")
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
