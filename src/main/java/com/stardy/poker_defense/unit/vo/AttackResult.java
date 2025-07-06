package com.stardy.poker_defense.unit.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AttackResult {
    private int damageDealt;
    private boolean killed;
    private int targetHp;
    private Reward reward;

    public boolean getKilled() {
        return killed;
    }
}
