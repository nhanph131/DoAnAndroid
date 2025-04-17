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

}
