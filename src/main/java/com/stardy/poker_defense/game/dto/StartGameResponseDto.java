package com.stardy.poker_defense.game.dto;

import com.stardy.poker_defense.game.entity.Game;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StartGameResponseDto {

    private Long gameId;

    public static StartGameResponseDto from(Game game) {

        return StartGameResponseDto.builder()
                .gameId(game.getId())
                .build();
    }
}
