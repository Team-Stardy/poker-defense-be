package com.stardy.poker_defense.unit.exception;

import com.stardy.poker_defense.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UnitErrorCode implements ErrorCode {

    NO_UNITS_PROVIDED(HttpStatus.BAD_REQUEST, "조합할 유닛이 제공되지 않았습니다."),
    INVALID_UNIT_COUNT(HttpStatus.BAD_REQUEST, "유효하지 않은 유닛 개수입니다. 2~5개의 유닛이 필요합니다."),
    GAME_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게임 유저를 찾을 수 없습니다."),
    UNIT_NOT_FOUND_OR_NOT_OWNED(HttpStatus.BAD_REQUEST, "하나 이상의 유닛을 찾을 수 없거나 해당 유저의 유닛이 아닙니다."),
    NO_VALID_POKER_HAND(HttpStatus.BAD_REQUEST, "유효한 포커 핸드 조합이 감지되지 않았습니다."),
    // ShopErrorCode와 중복되는 부분은 합쳐서 관리할 수 있습니다.
    NOT_ENOUGH_GOLD(HttpStatus.BAD_REQUEST, "골드가 부족합니다."),
    INVALID_PURCHASE_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 구매 유형입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}