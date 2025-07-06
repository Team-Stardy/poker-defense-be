package com.stardy.poker_defense.unit.service;

import com.stardy.poker_defense.game.entity.GameUser;
import com.stardy.poker_defense.unit.dto.AttackResponseDto;
import com.stardy.poker_defense.unit.entity.OwnedUnit;

import java.util.List;

public interface OwnedUnitService {
    AttackResponseDto attackNormal(long ownedUnitId, long targetId, long gameUserId);
    List<OwnedUnit> createRandom(int randomUnitCount, GameUser gameUser);
}
