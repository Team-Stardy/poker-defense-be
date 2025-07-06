package com.stardy.poker_defense.room.dto;

import com.stardy.poker_defense.room.entity.Room;
import com.stardy.poker_defense.room.entity.RoomStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EnterRoomResponseDto {

    private Long roomId;
    private String title;
    private String password;
    private Boolean passwordYn;
    private Boolean gameStartYn;
    private Integer maxPlayerCount;
    private Long ownerId;
    private RoomStatus status;

    public static EnterRoomResponseDto from(Room room) {

        return EnterRoomResponseDto.builder()
                .roomId(room.getId())
                .title(room.getTitle())
                .password(room.getPassword())
                .passwordYn(room.getPasswordYn())
                .gameStartYn(room.getGameStartYn())
                .maxPlayerCount(room.getMaxPlayerCount())
                .ownerId(room.getOwnerId())
                .status(room.getStatus())
                .build();
    }
}
