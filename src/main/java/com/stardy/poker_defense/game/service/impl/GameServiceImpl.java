package com.stardy.poker_defense.game.service.impl;

import com.stardy.poker_defense.common.service.WebsocketService;
import com.stardy.poker_defense.game.dto.StartGameRequestDto;
import com.stardy.poker_defense.game.dto.StartGameResponseDto;
import com.stardy.poker_defense.game.entity.Game;
import com.stardy.poker_defense.game.entity.GameDifficultyLevel;
import com.stardy.poker_defense.game.entity.GameUser;
import com.stardy.poker_defense.game.repository.GameRepository;
import com.stardy.poker_defense.game.repository.GameUserRepository;
import com.stardy.poker_defense.game.service.GameService;
import com.stardy.poker_defense.room.entity.Room;
import com.stardy.poker_defense.room.repository.RoomRepository;
import com.stardy.poker_defense.round.entity.Round;
import com.stardy.poker_defense.round.entity.SystemRound;
import com.stardy.poker_defense.round.repository.RoundRepository;
import com.stardy.poker_defense.round.repository.SystemRoundRepository;
import com.stardy.poker_defense.unit.entity.BossUnit;
import com.stardy.poker_defense.unit.entity.EnemyUnit;
import com.stardy.poker_defense.unit.entity.SystemUnit;
import com.stardy.poker_defense.unit.entity.UnitType;
import com.stardy.poker_defense.unit.repository.BossUnitRepository;
import com.stardy.poker_defense.unit.repository.EnemyUnitRepository;
import com.stardy.poker_defense.unit.repository.SystemUnitRepository;
import com.stardy.poker_defense.user.entity.User;
import com.stardy.poker_defense.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class GameServiceImpl implements GameService {

    private final RoundRepository roundRepository;
    @Value("${game.round-info.normal-round}")
    private int NORMAL_ROUND;

    @Value("${game.round-info.hard-round}")
    private int HARD_ROUND;

    @Value("${game.round-info.hell-round}")
    private int HELL_ROUND;

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final SystemRoundRepository systemRoundRepository;
    private final SystemUnitRepository systemUnitRepository;
    private final GameUserRepository gameUserRepository;
    private final GameRepository gameRepository;
    private final BossUnitRepository bossUnitRepository;
    private final EnemyUnitRepository enemyUnitRepository;

    private final WebsocketService websocketService;

    @Override
    public StartGameResponseDto startGame(StartGameRequestDto params) {

        Room findRoom = roomRepository.findById(params.getRoomId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 방입니다."));

        // 난이도에 따른 라운드 수 설정
        int roundCount = 0;
        if(params.getGameDifficultyLevel() == GameDifficultyLevel.NORMAL) {
            roundCount = NORMAL_ROUND;
        }
        else if(params.getGameDifficultyLevel() == GameDifficultyLevel.HARD) {
            roundCount = HARD_ROUND;
        }
        else if(params.getGameDifficultyLevel() == GameDifficultyLevel.HELL) {
            roundCount = HELL_ROUND;
        }

        // 게임 생성
        Game newGame = Game.builder()
                .room(findRoom)
                .difficultyLevel(params.getGameDifficultyLevel())
                .totalRound(roundCount)
                .build();

        gameRepository.save(newGame);
        findRoom.addGame(newGame);

        Game findGame = gameRepository.findById(findRoom.getGame().getId())
                .orElseThrow(() -> new RuntimeException("해당 게임은 존재하지 않습니다."));

        // 게임 유저 데이터 생성
        List<User> findUserList = userRepository.findAllByRoomId(findRoom.getId());
        List<GameUser> newGameUserList = new ArrayList<>();
        for(User user : findUserList) {

            GameUser newGameUser = GameUser.builder()
                    .game(findGame)
                    .user(user)
                    .life(30)
                    .gold(10)
                    .killCount(0)
                    .surviveYn(true)
                    .build();

            newGameUserList.add(newGameUser);
        }

        gameUserRepository.saveAll(newGameUserList);

        // 난이도에 따른 라운드 생성
        List<SystemRound> systemRoundList = systemRoundRepository.findAllByRoundNumber(HELL_ROUND);

        List<Round> newRoundList = new ArrayList<>();
        for(int i=0; i<roundCount; i++) {
            SystemRound currentSystemRound = systemRoundList.get(i);
            Round newRound = Round.builder()
                    .game(findGame)
                    .roundNumber(currentSystemRound.getRoundNumber())
                    .unitCount(currentSystemRound.getUnitCount())
                    .bossRoundYn(currentSystemRound.getBossRoundYn())
                    .build();

            newRoundList.add(newRound);
        }
        roundRepository.saveAll(newRoundList);
        findGame.addRoundList(newRoundList);

        // 라운드 별 보스 유닛, 게임 유저 별 적 유닛 생성
        List<SystemUnit> systemNormalUnitList = systemUnitRepository.findAllByType(UnitType.NORMAL);
        List<SystemUnit> systemBossUnitList = systemUnitRepository.findAllByType(UnitType.HERO);
        List<Round> findRoundList = roundRepository.findAllByGameId(findGame.getId());

        List<BossUnit> newBossUnitList = new ArrayList<>();
        List<EnemyUnit> newEnemyUnitList = new ArrayList<>();
        List<GameUser> findGameUserList = gameUserRepository.findAllByGameId(findGame.getId());
        int bossIdx = 0;
        for(Round round : findRoundList) {

            if(round.getBossRoundYn()) {
                SystemUnit systemBossUnit = systemBossUnitList.get(bossIdx);
                BossUnit newBossUnit = BossUnit.builder()
                        .game(findGame)
                        .round(round)
                        .hp(systemBossUnit.getHp())
                        .suit(systemBossUnit.getSuit())
                        .number(systemBossUnit.getNumber())
                        .defense(systemBossUnit.getDefense())
                        .type(systemBossUnit.getType())
                        .killedYn(false)
                        .appearanceYn(false)
                        .build();

                bossIdx++;
                newBossUnitList.add(newBossUnit);
            }

//            // 보스 유닛 생성
//            for(SystemUnit systemBossUnit : systemBossUnitList) {
//
//
//                if(round.getBossRoundYn()) {
//                    BossUnit newBossUnit = BossUnit.builder()
//                            .game(findGame)
//                            .round(round)
//                            .hp(systemBossUnit.getHp())
//                            .suit(systemBossUnit.getSuit())
//                            .number(systemBossUnit.getNumber())
//                            .defense(systemBossUnit.getDefense())
//                            .type(systemBossUnit.getType())
//                            .killedYn(false)
//                            .appearanceYn(false)
//                            .build();
//
//                    newBossUnitList.add(newBossUnit);
//                }
//            }

            // 게임 유저 별 적 유닛 생성
            for(GameUser gameUser : findGameUserList) {

                for(SystemUnit systemNormalUnit : systemNormalUnitList) {

                    EnemyUnit newEnemyUnit = EnemyUnit.builder()
                            .gameUser(gameUser)
                            .round(round)
                            .hp(systemNormalUnit.getHp())
                            .defense(systemNormalUnit.getDefense())
                            .suit(systemNormalUnit.getSuit())
                            .number(systemNormalUnit.getNumber())
                            .type(systemNormalUnit.getType())
                            .killedYn(false)
                            .appearanceYn(false)
                            .build();

                    newEnemyUnitList.add(newEnemyUnit);
                }
            }
        }

        bossUnitRepository.saveAll(newBossUnitList);
        enemyUnitRepository.saveAll(newEnemyUnitList);

        // 게임 시작 및 스케쥴러 작동
        findRoom.startGame();

        websocketService.startGame(findGame);

        return StartGameResponseDto.from(findGame);
    }
}
