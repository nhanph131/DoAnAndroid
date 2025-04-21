package com.nhanph.doanandroid;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cloudinary.android.MediaManager;
import com.nhanph.doanandroid.data.roomdatabase.AppDatabase;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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


    // Add this to MainApplication.java
    private static MainApplication instance;



    public static MainApplication getAppContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;


        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        database = AppDatabase.getDatabase(this);


        // Cấu hình Cloudinary
        Map config = new HashMap();
        config.put("cloud_name", "dtrl2znqh");
        config.put("api_key", "251365653991289");
        config.put("api_secret", "e_7LQWKUoFSwOpfSo7bNrf_eDc0");
        config.put("secure", true);
        try {
            MediaManager.init(this, config);
        } catch (Exception e) {
            Log.e("Cloudinary", "Init failed: ", e);
        }



        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        database = AppDatabase.getDatabase(this);
    }
     public static AppDatabase getDatabase() {
        return database;
    }


    public static MainApplication getInstance() {
        return instance;
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
    public static void saveUsername(String username){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();
    }
    public static String getUsername(){
        return sharedPreferences.getString("username", null);
    }
    public static void clearUsername(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.apply();
    }
    public static void saveAvatarUrl(String avatarUrl){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("avatarUrl", avatarUrl);
        editor.apply();
    }
    public static String getAvatarUrl(){
        return sharedPreferences.getString("avatarUrl", null);
    }

    public static void clearAvatarUrl(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("avatarUrl");
        editor.apply();
    }

    public static void saveAvatarToInternalStorage(Context context, Bitmap bitmap) {
        try {
            FileOutputStream fos = context.openFileOutput(getAvatarFileName(), Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (IOException e) {
            Log.d("saveAvatarToInternalStorage", "Error saving avatar: " + e.getMessage());
        }
    }

    public static Bitmap loadAvatarFromInternalStorage(Context context) {
        try {
            FileInputStream fis = context.openFileInput(getAvatarFileName());
            return BitmapFactory.decodeStream(fis);
        } catch (IOException e) {
            Log.d("loadAvatarFromInternalStorage", "Error loading avatar: " + e.getMessage());
            return null;
        }
    }

    public static void saveAvatarFileName(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("avatarFileName", "avatar_" + getUid());
        editor.apply();
    }

    public static String getAvatarFileName(){
        return sharedPreferences.getString("avatarFileName", null);
    }

    public static void clearAvatarFileName(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("avatarFileName");
        editor.apply();
    }
    public static void loadAvatarIntoView(Context context, ImageView imageView){
        Bitmap avatarBitmap = loadAvatarFromInternalStorage(context);

        if (avatarBitmap != null) {
            imageView.setImageBitmap(avatarBitmap);
        } else {
            Glide.with(context)
                    .asBitmap()
                    .load(getAvatarUrl())
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            imageView.setImageBitmap(resource);
                            saveAvatarToInternalStorage(context, resource);
                        }
                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) { }
                    });
        }

    }

}
