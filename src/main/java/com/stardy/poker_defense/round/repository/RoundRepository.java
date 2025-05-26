package com.stardy.poker_defense.round.repository;

import com.stardy.poker_defense.round.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundRepository extends JpaRepository<Round, Long> {
}
