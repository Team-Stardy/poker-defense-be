package com.stardy.poker_defense.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoundEndPayload {
    private Long userId;
    private Integer lostLife;
    private Integer remainEnemy;
    private Integer lifeAfter;
}
