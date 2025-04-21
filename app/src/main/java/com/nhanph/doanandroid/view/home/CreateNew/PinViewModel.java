package com.nhanph.doanandroid.view.home.CreateNew;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nhanph.doanandroid.MainApplication;
import com.nhanph.doanandroid.data.entities.Board;
import com.nhanph.doanandroid.data.entities.BoardPin;
import com.nhanph.doanandroid.data.entities.Pin;

import java.util.List;

public class PinViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Board>> userBoards = new MutableLiveData<>();

    public PinViewModel(Application application) {
        super(application);
    }

    public LiveData<List<Board>> getUserBoards(String userId) {
        MainApplication.getDatabase().databaseWriteExecutor.execute(() -> {
            List<Board> boards = MainApplication.getDatabase().getBoardDAO().getBoardsByUserIdSync(userId);
            userBoards.postValue(boards);
        });
         return userBoards;
    }

    public void insertPin(Pin pin, String boardId) {
        MainApplication.getDatabase().databaseWriteExecutor.execute(() -> {
            // Lưu pin vào database
             MainApplication.getDatabase().getPinDAO().insertPin(pin);

            // Nếu có chọn board, thêm vào bảng BoardPin
            if (boardId != null) {
                BoardPin boardPin = new BoardPin(boardId, pin.getId());
                MainApplication.getDatabase().getBoardPinDAO().insertBoardPin(boardPin);
            }
        });
    }
}