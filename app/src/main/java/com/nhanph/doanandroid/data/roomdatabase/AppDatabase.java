package com.nhanph.doanandroid.data.roomdatabase;

import androidx.room.RoomDatabase;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.nhanph.doanandroid.data.dao.BoardDAO;
import com.nhanph.doanandroid.data.dao.BoardPinDAO;
import com.nhanph.doanandroid.data.dao.PinDAO;
import com.nhanph.doanandroid.data.dao.SearchHistoryDAO;
import com.nhanph.doanandroid.data.dao.UserDAO;
import com.nhanph.doanandroid.data.entities.Board;
import com.nhanph.doanandroid.data.entities.BoardPin;
import com.nhanph.doanandroid.data.entities.Pin;
import com.nhanph.doanandroid.data.entities.SearchHistory;
import com.nhanph.doanandroid.data.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Pin.class, User.class, Board.class, BoardPin.class,SearchHistory.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract UserDAO getUserDAO();
    public abstract PinDAO getPinDAO();
    public abstract BoardDAO getBoardDAO();

    public abstract SearchHistoryDAO getSearchHistoryDAO();

    public abstract BoardPinDAO getBoardPinDAO();



    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // 📌 Ví dụ: Thêm cột mới vào bảng `search_history`
            database.execSQL("ALTER TABLE search_history ADD COLUMN new_column TEXT");
        }
    };



    //Nếu bạn muốn giữ dữ liệu cũ khi cập nhật schema, hãy dùng Migration thay vì xóa database.
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "doan_pinterest")
                            .addMigrations(MIGRATION_1_2)
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
