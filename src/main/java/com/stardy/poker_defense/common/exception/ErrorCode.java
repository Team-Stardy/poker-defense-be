package com.stardy.poker_defense.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getHttpStatus();
    String getMessage();
}
