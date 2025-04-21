package com.nhanph.doanandroid.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import lombok.Data;

@Data
@Entity(tableName = "board_pin", primaryKeys = {"boardId", "pinId"})
public class BoardPin {
    @NonNull
    String boardId;

    @NonNull
    String pinId;
    public BoardPin(@NonNull String boardId, @NonNull String pinId) {
         this.boardId = boardId;
        this.pinId = pinId;
    }

    // Thêm getter (nếu cần)
    @NonNull
     public String getBoardId() {
        return boardId;
    }

    @NonNull
    public String getPinId() {
        return pinId;
    }

}
