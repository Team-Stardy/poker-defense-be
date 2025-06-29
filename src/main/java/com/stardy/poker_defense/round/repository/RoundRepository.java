package com.stardy.poker_defense.round.repository;

import com.stardy.poker_defense.round.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoundRepository extends JpaRepository<Round, Long> {

    @Query("select r from Round r where r.game.id = :gameId")
    List<Round> findAllByGameId(@Param("gameId") Long gameId);
}
