package com.stardy.poker_defense.room.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateRoomRequestDto {

    private String roomName;
    private Integer maxPlayerCount;
    private Long userId;
    private String password;
    private Boolean passwordYn;
}
