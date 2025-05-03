package com.nhanph.doanandroid.view.home.CreateNew;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.data.entities.Board;

public class BoardViewModel extends AndroidViewModel {

    private final MutableLiveData<Board> createdBoard = new MutableLiveData<>();

    public BoardViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<Board> getCreatedBoard() {
        return createdBoard;
    }

    public void insertBoard(Board board) {
        MainApplication.getDatabase().databaseWriteExecutor.execute(() -> {
            MainApplication.getDatabase().getBoardDAO().insertBoard(board);
            // Sau khi insert thành công, cập nhật LiveData
            createdBoard.postValue(board);
        });
    }
}