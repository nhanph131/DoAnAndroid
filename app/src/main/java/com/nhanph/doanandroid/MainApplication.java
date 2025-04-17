package com.nhanph.doanandroid;

import android.app.Application;
import android.content.SharedPreferences;

import com.nhanph.doanandroid.data.roomdatabase.AppDatabase;

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
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        database = AppDatabase.getDatabase(this);
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
