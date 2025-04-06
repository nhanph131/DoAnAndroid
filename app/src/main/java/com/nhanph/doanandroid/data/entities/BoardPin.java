package com.nhanph.doanandroid.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity(tableName = "board_pin", primaryKeys = {"boardId", "pinId"})
public class BoardPin {
    String boardId;
    String pinId;

}
