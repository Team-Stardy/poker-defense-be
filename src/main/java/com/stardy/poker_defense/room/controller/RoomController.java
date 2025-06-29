package com.stardy.poker_defense.room.controller;

import com.stardy.poker_defense.common.response.ApiResponse;
import com.stardy.poker_defense.room.dto.CreateRoomRequestDto;
import com.stardy.poker_defense.room.dto.CreateRoomResponseDto;
import com.stardy.poker_defense.room.dto.EnterRoomRequestDto;
import com.stardy.poker_defense.room.dto.EnterRoomResponseDto;
import com.stardy.poker_defense.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RoomController {

    private final RoomService roomService;

    /*
    * 방 생성 api
    * */
    @PostMapping("/rooms")
    public ResponseEntity<ApiResponse<Object>> createRoom(@RequestBody CreateRoomRequestDto params) {

        CreateRoomResponseDto result = roomService.createRoom(params);

        return ApiResponse.builder()
                .data(result)
                .status(HttpStatus.OK)
                .message("게임 방을 생성하였습니다.")
                .success();
    }

    /*
    * 방 참가 api
    * */
    @PostMapping("/rooms/{roomId}")
    public ResponseEntity<ApiResponse<Object>> enterRoom(@PathVariable("roomId") Long roomId, @RequestBody EnterRoomRequestDto params) {

        params.setRoomId(roomId);

        EnterRoomResponseDto result = roomService.enterRoom(params);

        return ApiResponse.builder()
                .data(result)
                .status(HttpStatus.OK)
                .message("방에 참여하였습니다.")
                .success();
    }
}
