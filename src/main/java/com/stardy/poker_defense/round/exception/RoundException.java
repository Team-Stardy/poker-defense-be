package com.stardy.poker_defense.round.exception;

import com.stardy.poker_defense.common.exception.ApiException;
import com.stardy.poker_defense.common.exception.ErrorCode;

public class RoundException extends ApiException {

    public RoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public RoundException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
