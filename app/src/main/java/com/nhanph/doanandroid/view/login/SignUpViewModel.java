package com.nhanph.doanandroid.view.login;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.data.entities.User;
import com.nhanph.doanandroid.data.roomdatabase.AppDatabase;
import com.nhanph.doanandroid.utility.validator.AppNotificationCode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Map;

public class SignUpViewModel extends ViewModel {

     private static final String TAG = "SignUpViewModel";
    private final MutableLiveData<AppNotificationCode> notification = new MutableLiveData<>();
    private int uploadCount = 0;
    private final int TOTAL_UPLOADS = 2; // Avatar + Upload folder
    private User pendingUser;
    private File tempImageFile; // Dùng chung cho cả 2 upload

    public void signUp(String nickname, String username, String password, String confirmPassword) {
        if (validateData(username, password, confirmPassword)) {
            AppDatabase.databaseWriteExecutor.execute(() -> {
                try {
                    boolean isExists = MainApplication.getDatabase().getUserDAO().isExistsByUsername(username);
                    if (isExists) {
                        notification.postValue(AppNotificationCode.USER_EXISTED);
                        return;
                    }

                    pendingUser = new User();
                    pendingUser.setNickname(nickname);
                    pendingUser.setUsername(username);
                    pendingUser.setPassword(password);
                    pendingUser.setEmail("");
                    pendingUser.setBio("This is a default bio.");
                    pendingUser.setCreatedAt(LocalDateTime.now().toString());

                    Context context = MainApplication.getInstance().getApplicationContext();
                    prepareDefaultImage(context);
                    createUserFoldersOnCloudinary(username);

                } catch (Exception e) {
                    Log.e(TAG, "SignUp failed", e);
                    cleanupTempFile();
                    notification.postValue(AppNotificationCode.REGISTER_FAILED);
                }
            });
        }
    }

    private void prepareDefaultImage(Context context) throws Exception {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("default_avatar.jpg");
        tempImageFile = File.createTempFile("default_avatar", ".jpg", context.getCacheDir());
        try (FileOutputStream out = new FileOutputStream(tempImageFile)) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } finally {
            inputStream.close();
        }
    }

    private void createUserFoldersOnCloudinary(String username) {
        // Upload to avatar folder
        uploadImageToFolder(username, "avatar", true);

        // Upload to upload folder
        uploadImageToFolder(username, "upload", false);
    }

    private void uploadImageToFolder(String username, String folderName, boolean isAvatar) {
        String publicId = String.format("users/%s/%s/default_image", username, folderName);

        MediaManager.get().upload(Uri.fromFile(tempImageFile))
                .option("public_id", publicId)
                .option("folder", "users/" + username + "/" + folderName) // Rõ ràng folder structure
                .option("use_filename", true)
                .option("unique_filename", false)
                .option("overwrite", true)
                .option("resource_type", "image")
                .option("context", "username=" + username) // Thêm metadata
                .callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                        Log.d(TAG, String.format("Uploading to %s/%s started", username, folderName));
                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {
                        Log.d(TAG, String.format("Upload progress %s/%s: %d/%d",
                                username, folderName, bytes, totalBytes));
                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        String publicId = (String) resultData.get("public_id");
                        String url = (String) resultData.get("secure_url");

                        Log.i(TAG, String.format("Upload success to %s | URL: %s", publicId, url));

                        if (isAvatar) {
                            pendingUser.setAvatarUrl(url);
                        }

                        if (++uploadCount >= TOTAL_UPLOADS) {
                            completeRegistration();
                        }
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        Log.e(TAG, String.format("Upload failed to %s/%s: %s",
                                username, folderName, error.getDescription()));
                        cleanupTempFile();
                        notification.postValue(AppNotificationCode.REGISTER_FAILED);
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {
                        Log.w(TAG, String.format("Upload rescheduled to %s/%s", username, folderName));
                    }
                })
                .dispatch();
    }

    private void completeRegistration() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
             try {
                MainApplication.getDatabase().getUserDAO().insertUser(pendingUser);
                Log.i(TAG, "User registered successfully: " + pendingUser.getUsername());
                notification.postValue(AppNotificationCode.REGISTER_SUCCESS);
            } catch (Exception e) {
                Log.e(TAG, "Database insert failed", e);
                notification.postValue(AppNotificationCode.REGISTER_FAILED);
            } finally {
                cleanupTempFile();
                resetState();
            }
        });
    }

    private void cleanupTempFile() {
        if (tempImageFile != null && tempImageFile.exists()) {
            if (!tempImageFile.delete()) {
                Log.w(TAG, "Failed to delete temp file: " + tempImageFile.getAbsolutePath());
            }
            tempImageFile = null;
        }
    }

    private void resetState() {
        uploadCount = 0;
        pendingUser = null;
    }

    public boolean validateData(String username, String password, String confirmPassword) {
        if (username.isEmpty()) {
            notification.setValue(AppNotificationCode.EMPTY_USERNAME);
            return false;
        }
        if (username.length() < 6) {
            notification.setValue(AppNotificationCode.INVALID_USERNAME);
            return false;
        }
        if (password.isEmpty()) {
            notification.setValue(AppNotificationCode.EMPTY_PASSWORD);
            return false;
        }
        if (password.length() < 6) {
            notification.setValue(AppNotificationCode.INVALID_PASSWORD);
            return false;
        }
        if (!password.equals(confirmPassword)) {
            notification.setValue(AppNotificationCode.PASSWORD_NOT_MATCH);
            return false;
        }
        return true;
    }

    public LiveData<AppNotificationCode> getNotification() {
        return notification;
    }
}