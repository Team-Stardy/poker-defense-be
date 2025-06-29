package com.stardy.poker_defense.game.controller;

import com.stardy.poker_defense.common.response.ApiResponse;
import com.stardy.poker_defense.game.dto.StartGameRequestDto;
import com.stardy.poker_defense.game.dto.StartGameResponseDto;
import com.stardy.poker_defense.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GameController {

    private final GameService gameService;

    @PostMapping("/games")
    public ResponseEntity<ApiResponse<Object>> startGame(@RequestBody StartGameRequestDto params) {

        StartGameResponseDto result = gameService.startGame(params);

        return ApiResponse.builder()
                .data(result)
                .status(HttpStatus.OK)
                .message("게임이 시작되었습니다.")
                .success();
    }
}
