package com.stardy.poker_defense.room.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class EnterRoomRequestDto {

    private Long roomId;
    private Long userId;
    private String password;
}
