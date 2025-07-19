package com.stardy.poker_defense.unit.dto;

import com.stardy.poker_defense.unit.entity.OwnedUnit;
import com.stardy.poker_defense.unit.entity.UnitType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UnitRewardDto {
    private Boolean isHero;
    private Long ownedUnitId;
//    private Long systemUnitId;
//    private String type;
    private String suit;
    private String number;
    private Integer attack;
    private Integer hp;
//    private Integer defense;
    private Integer range;

    public static UnitRewardDto from(OwnedUnit ownedUnit) {
        return new UnitRewardDto(ownedUnit.getType() == UnitType.HERO, // 참고 : https://johnmarc.tistory.com/152
                ownedUnit.getId(),
                ownedUnit.getSuit().name(),
                ownedUnit.getNumber(),
                ownedUnit.getAttackPower(),
                ownedUnit.getHp(),
                ownedUnit.getAttackRange()
                );
    }
}
