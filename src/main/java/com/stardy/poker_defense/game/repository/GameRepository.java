package com.stardy.poker_defense.game.repository;

import com.stardy.poker_defense.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
