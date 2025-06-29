package com.stardy.poker_defense.unit.repository;

import com.stardy.poker_defense.unit.entity.SystemUnit;
import com.stardy.poker_defense.unit.entity.UnitType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemUnitRepository extends JpaRepository<SystemUnit, Long> {
    List<SystemUnit> findAllByType(UnitType type);
}
