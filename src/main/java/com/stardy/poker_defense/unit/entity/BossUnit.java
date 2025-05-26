package com.stardy.poker_defense.unit.entity;

import com.stardy.poker_defense.game.entity.Game;
import com.stardy.poker_defense.round.Round;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "boss_unit")
public class BossUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boss_unit_id")
    private Long id;

    private Long systemUnitId;

    private Boolean killedYn;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id")
    private Round round;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;
}
