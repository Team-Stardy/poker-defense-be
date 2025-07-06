package com.stardy.poker_defense.room.service;

import com.stardy.poker_defense.room.dto.CreateRoomRequestDto;
import com.stardy.poker_defense.room.dto.CreateRoomResponseDto;
import com.stardy.poker_defense.room.dto.EnterRoomRequestDto;
import com.stardy.poker_defense.room.dto.EnterRoomResponseDto;

public interface RoomService {
    CreateRoomResponseDto createRoom(CreateRoomRequestDto params);

    EnterRoomResponseDto enterRoom(EnterRoomRequestDto params);
}
