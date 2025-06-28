package com.stardy.poker_defense.common.dto;

import com.stardy.poker_defense.common.service.WebsocketService;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RoundStartPayload {
    private Long roundId;
    private List<UnitInfoDto> enemyUnits;
    private UnitInfoDto bossUnit;
}