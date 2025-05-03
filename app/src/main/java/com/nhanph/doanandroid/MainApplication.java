package com.nhanph.doanandroid;

import android.app.Application;
import android.content.SharedPreferences;

import com.cloudinary.android.MediaManager;
import com.nhanph.doanandroid.data.roomdatabase.AppDatabase;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MainApplication extends Application {
    private static SharedPreferences sharedPreferences;
    public static final String PREF_NAME = "AppPrefs";

    @Getter
    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();



        // Cấu hình Cloudinary
        Map config = new HashMap();
        config.put("cloud_name", "dtrl2znqh");
        config.put("api_key", "251365653991289");
        config.put("api_secret", "e_7LQWKUoFSwOpfSo7bNrf_eDc0");
        MediaManager.init(this, config);



        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        database = AppDatabase.getDatabase(this);
    }
    public static AppDatabase getDatabase() {
        return database;
    }

    public static void saveUid(String uid) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("uid", uid);
        editor.apply();
    }

    public static String getUid() {

        return sharedPreferences.getString("uid", null);
    }

    public static void clearUid() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("uid");
        editor.apply();
    }

}
