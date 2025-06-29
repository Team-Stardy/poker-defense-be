package com.stardy.poker_defense.room.service.impl;

import com.stardy.poker_defense.room.dto.CreateRoomRequestDto;
import com.stardy.poker_defense.room.dto.CreateRoomResponseDto;
import com.stardy.poker_defense.room.dto.EnterRoomRequestDto;
import com.stardy.poker_defense.room.dto.EnterRoomResponseDto;
import com.stardy.poker_defense.room.entity.Room;
import com.stardy.poker_defense.room.entity.RoomStatus;
import com.stardy.poker_defense.room.repository.RoomRepository;
import com.stardy.poker_defense.room.service.RoomService;
import com.stardy.poker_defense.user.entity.User;
import com.stardy.poker_defense.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Transactional
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Override
    public CreateRoomResponseDto createRoom(CreateRoomRequestDto params) {

        User findUser = userRepository.findById(params.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        Room newRoom = Room.builder()
                .title(params.getRoomName())
                .password(params.getPassword())
                .passwordYn(params.getPasswordYn())
                .gameStartYn(false)
                .maxPlayerCount(params.getMaxPlayerCount())
                .ownerId(params.getUserId())
                .status(RoomStatus.WAITING)
                .build();

        Room createdRoom = roomRepository.save(newRoom);
        findUser.enterRoom(newRoom);

        return CreateRoomResponseDto.from(createdRoom);
    }

    @Override
    public EnterRoomResponseDto enterRoom(EnterRoomRequestDto params) {

        User findUser = userRepository.findById(params.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        Room findRoom = roomRepository.findById(params.getRoomId())
                .orElseThrow(() -> new RuntimeException("해당 방이 존재하지 않습니다."));

        if(findRoom.getPasswordYn() && !(Objects.equals(findRoom.getPassword(), params.getPassword()))) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        findUser.enterRoom(findRoom);

        return EnterRoomResponseDto.from(findRoom);
    }
}
