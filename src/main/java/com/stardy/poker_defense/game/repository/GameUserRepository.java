package com.stardy.poker_defense.game.repository;

import com.stardy.poker_defense.game.entity.GameUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameUserRepository extends JpaRepository<GameUser, Long> {

    @Query("select gu from GameUser gu where gu.game.id = :gameId")
    List<GameUser> findAllByGameId(@Param("gameId") Long gameId);
}
