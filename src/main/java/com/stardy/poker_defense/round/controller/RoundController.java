package com.stardy.poker_defense.round.controller;

import com.stardy.poker_defense.common.response.ApiResponse;
import com.stardy.poker_defense.round.dto.request.CreateSystemRoundRequestDto;
import com.stardy.poker_defense.round.dto.response.GetSystemRoundListResponseDto;
import com.stardy.poker_defense.round.service.RoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class RoundController {

    private final RoundService roundService;

    @GetMapping("/system-rounds")
    public ResponseEntity<ApiResponse<Object>> getSystemRoundList() {

        List<GetSystemRoundListResponseDto> resultList = roundService.getSystemRoundList();

        return ApiResponse.builder()
                .data(resultList)
                .status(HttpStatus.OK)
                .message("시스템 라운드 정보를 조회하였습니다.")
                .success();
    }

    @PostMapping("/system-rounds")
    public ResponseEntity<ApiResponse<Object>> createSystemRound(@RequestBody CreateSystemRoundRequestDto params) {

        roundService.createSystemRound(params);

        return ApiResponse.builder()
                .data(null)
                .status(HttpStatus.CREATED)
                .message("시스템 라운드 정보를 생성하였습니다.")
                .success();
    }
}
