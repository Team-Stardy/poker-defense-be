package com.stardy.poker_defense.unit.repository;

import com.stardy.poker_defense.unit.entity.OwnedUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnedUnitRepository extends JpaRepository<OwnedUnit, Long> {
}
