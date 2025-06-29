package com.stardy.poker_defense.room.dto;

import com.stardy.poker_defense.room.entity.Room;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateRoomResponseDto {

    private Long roomId;

    public static CreateRoomResponseDto from(Room room) {

        return CreateRoomResponseDto.builder()
                .roomId(room.getId())
                .build();
    }
}
