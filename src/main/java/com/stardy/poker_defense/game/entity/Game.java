package com.stardy.poker_defense.game.entity;

import com.stardy.poker_defense.room.entity.Room;
import com.stardy.poker_defense.round.entity.Round;
import com.stardy.poker_defense.unit.entity.BossUnit;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;

    private GameDifficultyLevel difficultyLevel;

    private Integer totalRound;

    private Integer currentRound;

    private LocalDateTime gameDuration;

    @Builder.Default
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GameUser> gameUserList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Round> roundList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BossUnit> bossUnitList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public void addRoundList(List<Round> newRoundList) {
        this.roundList = newRoundList;
    }
}
