package com.stardy.poker_defense.user.controller;

import com.stardy.poker_defense.common.response.ApiResponse;
import com.stardy.poker_defense.user.dto.LoginRequestDto;
import com.stardy.poker_defense.user.dto.LoginResponseDto;
import com.stardy.poker_defense.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    /*
    * 로그인 api
    * */
    @PostMapping("/users/login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody LoginRequestDto params) {

        LoginResponseDto result = userService.login(params);

        return ApiResponse.builder()
                .data(result)
                .status(HttpStatus.OK)
                .message("접속되었습니다.")
                .success();
    }
}
