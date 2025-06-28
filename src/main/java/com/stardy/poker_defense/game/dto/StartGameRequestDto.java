package com.stardy.poker_defense.game.dto;

import com.stardy.poker_defense.game.entity.GameDifficultyLevel;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StartGameRequestDto {

    private Long roomId;
    private GameDifficultyLevel gameDifficultyLevel;
}
