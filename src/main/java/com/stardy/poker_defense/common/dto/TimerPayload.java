package com.stardy.poker_defense.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TimerPayload {
    private Integer elapsedTime;
}
