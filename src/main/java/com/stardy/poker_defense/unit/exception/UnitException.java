package com.stardy.poker_defense.unit.exception;

import com.stardy.poker_defense.common.exception.ApiException;
import com.stardy.poker_defense.common.exception.ErrorCode;

public class UnitException extends ApiException {

    public UnitException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UnitException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}