package com.stardy.poker_defense.unit.service.impl;

import com.stardy.poker_defense.game.entity.GameUser;
import com.stardy.poker_defense.game.repository.GameUserRepository;
import com.stardy.poker_defense.unit.dto.AttackResponseDto;
import com.stardy.poker_defense.unit.entity.BossUnit;
import com.stardy.poker_defense.unit.entity.EnemyUnit;
import com.stardy.poker_defense.unit.entity.OwnedUnit;
import com.stardy.poker_defense.unit.entity.SystemUnit;
import com.stardy.poker_defense.unit.repository.BossUnitRepository;
import com.stardy.poker_defense.unit.repository.EnemyUnitRepository;
import com.stardy.poker_defense.unit.repository.OwnedUnitRepository;
import com.stardy.poker_defense.unit.repository.SystemUnitRepository;
import com.stardy.poker_defense.unit.service.OwnedUnitService;
import com.stardy.poker_defense.unit.vo.AttackResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
@Transactional
public class OwnedUnitServiceImpl implements OwnedUnitService {

    private final OwnedUnitRepository ownedUnitRepository;
    private final EnemyUnitRepository enemyUnitRepository;
    private final BossUnitRepository bossUnitRepository;
    private final GameUserRepository gameUserRepository;
    private final SystemUnitRepository systemUnitRepository;

    private final int systemUnitCount;

    public OwnedUnitServiceImpl(OwnedUnitRepository ownedUnitRepository, EnemyUnitRepository enemyUnitRepository,
                                BossUnitRepository bossUnitRepository, GameUserRepository gameUserRepository,
                                @Value("${unit.system-unit-count}") int  systemUnitCount, SystemUnitRepository systemUnitRepository) {
        this.ownedUnitRepository = ownedUnitRepository;
        this.enemyUnitRepository = enemyUnitRepository;
        this.bossUnitRepository = bossUnitRepository;
        this.gameUserRepository = gameUserRepository;
        this.systemUnitCount = systemUnitCount;
        this.systemUnitRepository = systemUnitRepository;
    }

    public AttackResponseDto attackNormal(long ownedUnitId, long targetId, long gameUserId) {
        OwnedUnit ownedUnit = ownedUnitRepository.findById(ownedUnitId).orElseThrow(NoSuchElementException::new);
        EnemyUnit enemyUnit = enemyUnitRepository.findById(targetId).orElseThrow(NoSuchElementException::new);
        AttackResult attackResult = ownedUnit.attackNormal(enemyUnit);

        if (attackResult.getKilled()) {
            GameUser gameUser = gameUserRepository.findById(gameUserId).orElseThrow(NoSuchElementException::new);
            gameUser.addKill(attackResult.getReward().getGold());
            return AttackResponseDto.killNormal(attackResult.getDamageDealt(), attackResult.getTargetHp(),
                    gameUser.getKillCount(), gameUser.getGold());
        }
        return AttackResponseDto.notKilled(attackResult.getDamageDealt(), attackResult.getTargetHp());
    }

    public AttackResponseDto attackBoss(long ownedUnitId, long targetId, long gameUserId) {
        OwnedUnit ownedUnit = ownedUnitRepository.findById(ownedUnitId).orElseThrow(NoSuchElementException::new);
        BossUnit bossUnit = bossUnitRepository.findById(targetId).orElseThrow(NoSuchElementException::new);
        AttackResult attackResult = ownedUnit.attackBoss(bossUnit);

        if (attackResult.getKilled()) {
            GameUser gameUser = gameUserRepository.findById(gameUserId).orElseThrow(NoSuchElementException::new);
            gameUser.addKill();
            //랜덤유닛 보상 생성
            OwnedUnit rewardUnit = OwnedUnit.from(
                    systemUnitRepository.findById(bossUnit.getReward().getSystemUnitId()).orElseThrow(NoSuchElementException::new)
            );

            return AttackResponseDto.killBoss(attackResult.getDamageDealt(), attackResult.getTargetHp(),
                    gameUser.getKillCount(), gameUser.getGold(), rewardUnit);
        }
        return AttackResponseDto.notKilled(attackResult.getDamageDealt(), attackResult.getTargetHp());
    }

    public List<OwnedUnit> createRandom(int randomUnitCount, GameUser gameUser) {
        List<Long> ids = new ArrayList<>(randomUnitCount);
        Random random = new Random();
        for (int i = 0; i < randomUnitCount; i++) {
            ids.add(random.nextLong(systemUnitCount) + 1);
        }

        List<SystemUnit> systemUnits = systemUnitRepository.findAllById(ids);
        // 불변 리스트
        return systemUnits.stream()
                .map(systemUnit -> {
                    OwnedUnit ownedUnit = OwnedUnit.from(systemUnit);
                    gameUser.addOwnedUnit(ownedUnit);
                    return ownedUnit;
                }).toList();
    }
}
