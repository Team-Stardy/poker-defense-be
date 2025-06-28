package com.stardy.poker_defense.game.service.impl;

import com.stardy.poker_defense.common.service.WebsocketService;
import com.stardy.poker_defense.game.dto.StartGameRequestDto;
import com.stardy.poker_defense.game.dto.StartGameResponseDto;
import com.stardy.poker_defense.game.entity.Game;
import com.stardy.poker_defense.game.entity.GameDifficultyLevel;
import com.stardy.poker_defense.game.service.GameService;
import com.stardy.poker_defense.room.entity.Room;
import com.stardy.poker_defense.room.repository.RoomRepository;
import com.stardy.poker_defense.round.entity.Round;
import com.stardy.poker_defense.round.entity.SystemRound;
import com.stardy.poker_defense.round.repository.SystemRoundRepository;
import com.stardy.poker_defense.unit.entity.BossUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class GameServiceImpl implements GameService {

    @Value("${game.round-info.normal-round}")
    private int NORMAL_ROUND;

    @Value("${game.round-info.hard-round}")
    private int HARD_ROUND;

    @Value("${game.round-info.hell-round}")
    private int HELL_ROUND;

    private final RoomRepository roomRepository;
    private final SystemRoundRepository systemRoundRepository;

    private final WebsocketService websocketService;

    @Override
    public StartGameResponseDto startGame(StartGameRequestDto params) {

        Room room = roomRepository.findById(params.getRoomId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 방입니다."));

//        if(params.getGameDifficultyLevel() == GameDifficultyLevel.NORMAL) {
//
//            List<SystemRound> systemRoundList = systemRoundRepository.findAllByRoundNumber(NORMAL_ROUND);
//            for(SystemRound systemRound : systemRoundList) {
//
//                BossUnit bossUnit = systemRound.getBossRoundYn() == true ?
//
//                Round newRound = Round.builder()
//                        .systemRoundId(systemRound.getId())
//                        .game(room.getGame())
//                        .
//            }
//        }

        websocketService.startGame(room.getGame());

        return StartGameResponseDto.from(room.getGame());
    }
}
