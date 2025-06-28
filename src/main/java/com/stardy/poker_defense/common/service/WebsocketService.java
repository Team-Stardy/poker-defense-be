package com.stardy.poker_defense.common.service;

import com.stardy.poker_defense.common.dto.*;
import com.stardy.poker_defense.game.entity.Game;
import com.stardy.poker_defense.game.entity.GameUser;
import com.stardy.poker_defense.room.entity.Room;
import com.stardy.poker_defense.room.repository.RoomRepository;
import com.stardy.poker_defense.round.entity.Round;
import com.stardy.poker_defense.unit.entity.BossUnit;
import com.stardy.poker_defense.unit.entity.EnemyUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

@RequiredArgsConstructor
@Service
public class WebsocketService {

    @Value("${scheduler.threadpool-size}")
    private int schedulerPoolSize;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(schedulerPoolSize);

    private final SimpMessagingTemplate messagingTemplate;

    private final RoomRepository roomRepository;

    public void startGame(Game game) {

        List<Round> roundList = game.getRoundList();
        if (roundList.isEmpty()) throw new RuntimeException("라운드 데이터가 없습니다.");

        scheduler.execute(() -> {
            try {
                playRounds(game, roundList, game.getRoom().getId());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void playRounds(Game game, List<Round> roundList, Long roomId) throws InterruptedException {
        for (Round round : roundList) {
            // 1. 라운드 시작 알림 (적 유닛, 보스 정보 동시 전송)
            sendRoundStart(round, roomId);

            // 2. 라운드 타임루프 (예: 20초 or EnemyUnit, BossUnit 모두 killedYn==true 일 때까지 반복)
            for (int sec = 1; sec <= 20; sec++) {
                sendTimer(roomId, sec);

                sleep(1000);

                if (isRoundCleared(round)) break;
            }

            // 3. 라운드 종료: 유저별 미처치 적 수로 생명 차감하고 메시지 전송
            settleRound(round, roomId);

            sleep(2000);
        }
        // (전체 라운드 종료 후 게임 종료 메시지, 결과 집계 등을 추가하실 수 있습니다)
    }

    private void sendRoundStart(Round round, Long roomId) {
        List<EnemyUnit> enemyUnits = round.getEnemyUnitList();
        BossUnit bossUnit = round.getBossUnit();

        List<UnitInfoDto> enemyDtoList = enemyUnits.stream()
                .map(UnitInfoDto::fromEnemyUnit)
                .collect(Collectors.toList());

        UnitInfoDto bossDto = bossUnit != null ? UnitInfoDto.fromBossUnit(bossUnit) : null;

        RoundStartPayload payload = RoundStartPayload.builder()
                .roundId(round.getId())
                .enemyUnits(enemyDtoList)
                .bossUnit(bossDto)
                .build();

        WebsocketResponseDto<RoundStartPayload> wsMessage = WebsocketResponseDto.<RoundStartPayload>builder()
                .type(MessageType.ROUND_START)
                .payload(payload)
                .timestamp(LocalDateTime.now())
                .roomId(String.valueOf(roomId))
                .build();

        messagingTemplate.convertAndSend("/topic/game/" + roomId, wsMessage);

        // 라운드 시작 시각도 기록
        round.changeStartedAt(LocalDateTime.now());
    }

    private void sendTimer(Long roomId, int elapsedTime) {
        TimerPayload timerPayload = TimerPayload.builder().elapsedTime(elapsedTime).build();

        WebsocketResponseDto<TimerPayload> wsMessage = WebsocketResponseDto.<TimerPayload>builder()
                .type(MessageType.TIMER)
                .payload(timerPayload)
                .timestamp(LocalDateTime.now())
                .roomId(String.valueOf(roomId))
                .build();

        messagingTemplate.convertAndSend("/topic/game/" + roomId, wsMessage);
    }

    private boolean isRoundCleared(Round round) {
        boolean enemiesCleared = round.getEnemyUnitList().stream().allMatch(e -> Boolean.TRUE.equals(e.getKilledYn()));
        BossUnit boss = round.getBossUnit();
        boolean bossCleared = (boss == null) || Boolean.TRUE.equals(boss.getKilledYn());
        return enemiesCleared && bossCleared;
    }

    @Transactional
    public void settleRound(Round round, Long roomId) {
        Game game = round.getGame();
        for (GameUser gameUser : game.getGameUserList()) {
            long remain = round.getEnemyUnitList().stream()
                    .filter(eu -> eu.getGameUser().equals(gameUser))
                    .filter(eu -> !Boolean.TRUE.equals(eu.getKilledYn()))
                    .count();
            int oldLife = gameUser.getLife();
            int lost = (int) remain;
            int newLife = Math.max(oldLife - lost, 0);
            gameUser.changeLife(newLife);

            // 메시지 전송
            RoundEndPayload payload = RoundEndPayload.builder()
                    .userId(gameUser.getUser().getId())
                    .lostLife(lost)
                    .remainEnemy((int) remain)
                    .lifeAfter(newLife)
                    .build();
            WebsocketResponseDto<RoundEndPayload> wsMessage = WebsocketResponseDto.<RoundEndPayload>builder()
                    .type(MessageType.ROUND_END)
                    .payload(payload)
                    .timestamp(LocalDateTime.now())
                    .roomId(String.valueOf(roomId))
                    .build();
            messagingTemplate.convertAndSend("/topic/room/" + roomId, wsMessage);
        }

        // 끝난 시각 기록
        round.changeEndedAt(LocalDateTime.now());
    }

}
