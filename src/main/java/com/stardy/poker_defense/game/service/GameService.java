package com.stardy.poker_defense.game.service;

import com.stardy.poker_defense.game.dto.StartGameRequestDto;
import com.stardy.poker_defense.game.dto.StartGameResponseDto;

public interface GameService {
    StartGameResponseDto startGame(StartGameRequestDto params);
}
