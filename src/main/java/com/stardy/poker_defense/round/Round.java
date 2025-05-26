package com.stardy.poker_defense.round;

import com.stardy.poker_defense.game.entity.Game;
import com.stardy.poker_defense.unit.entity.BossUnit;
import com.stardy.poker_defense.unit.entity.EnemyUnit;
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
@Table(name = "round")
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "round_id")
    private Long id;

    private Long systemRoundId;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Builder.Default
    @OneToMany(mappedBy = "round", fetch = FetchType.LAZY)
    private List<EnemyUnit> enemyUnitList = new ArrayList<>();

    @OneToOne(mappedBy = "round", fetch = FetchType.LAZY)
    private BossUnit bossUnit;
}
