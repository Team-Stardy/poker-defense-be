package com.stardy.poker_defense.round.repository;

import com.stardy.poker_defense.round.entity.SystemRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface SystemRoundRepository extends JpaRepository<SystemRound, Long> {

    Optional<SystemRound> findByRoundNumber(Integer roundNumber);

    @Query("select sr from SystemRound sr where sr.roundNumber <= :normal_round")
    List<SystemRound> findAllByRoundNumber(@Param("normal_round") Integer normal_round);
}
