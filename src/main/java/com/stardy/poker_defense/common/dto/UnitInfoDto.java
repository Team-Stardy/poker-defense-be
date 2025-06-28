package com.stardy.poker_defense.common.dto;

import com.stardy.poker_defense.unit.entity.BossUnit;
import com.stardy.poker_defense.unit.entity.CardSuit;
import com.stardy.poker_defense.unit.entity.EnemyUnit;
import com.stardy.poker_defense.unit.entity.UnitType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UnitInfoDto {

    private Long id;
    private Boolean killed;
    private Integer hp;
    private Integer defense;
    private CardSuit suit;
    private String number;
    private Double xPos, yPos;
    private UnitType type;

    public static UnitInfoDto fromEnemyUnit(EnemyUnit eu) {
        return UnitInfoDto.builder()
                .id(eu.getId())
                .killed(Boolean.TRUE.equals(eu.getKilledYn()))
                .hp(eu.getHp())
                .defense(eu.getDefense())
                .suit(eu.getSuit())
                .number(eu.getNumber())
                .xPos(eu.getXPos())
                .yPos(eu.getYPos())
                .type(eu.getType())
                .build();
    }

    public static UnitInfoDto fromBossUnit(BossUnit bu) {
        if (bu == null) return null;
        return UnitInfoDto.builder()
                .id(bu.getId())
                .killed(Boolean.TRUE.equals(bu.getKilledYn()))
                .hp(bu.getHp())
                .defense(bu.getDefense())
                .suit(bu.getSuit())
                .number(bu.getNumber())
                .xPos(bu.getXPos())
                .yPos(bu.getYPos())
                .type(bu.getType())
                .build();
    }
}