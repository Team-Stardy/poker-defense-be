package com.stardy.poker_defense.unit.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AttackResponseDto {
    private int damageDealt;
    private int targetHp;
    private Integer killCount;
    private Integer gold;

    public static AttackResponseDto notKilled(int damageDealt, int targetHp) {
        return new AttackResponseDto(damageDealt, targetHp, null, null);
    }

    public static AttackResponseDto killNormal(int damageDealt, int targetHp, Integer killCount, Integer gold) {
        return new AttackResponseDto(damageDealt, targetHp, killCount, gold);
    }
}
