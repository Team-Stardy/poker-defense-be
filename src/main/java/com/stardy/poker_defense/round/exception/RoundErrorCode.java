package com.stardy.poker_defense.round.exception;

import com.stardy.poker_defense.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RoundErrorCode implements ErrorCode {

    ALREADY_EXIST_SYSTEM_ROUND(HttpStatus.BAD_REQUEST, "해당 라운드 수를 가진 시스템 방이 존재합니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
