package com.stardy.poker_defense.round.repository;

import com.stardy.poker_defense.round.entity.SystemRound;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface SystemRoundRepository extends JpaRepository<SystemRound, Long> {

    Optional<SystemRound> findByRoundNumber(Integer roundNumber);
}
