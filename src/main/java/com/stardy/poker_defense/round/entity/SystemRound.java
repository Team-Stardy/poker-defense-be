package com.stardy.poker_defense.round.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "system_round")
public class SystemRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "system_round_id")
    private Long id;

    private Integer roundNumber;

    private Long systemUnitId;

    private Integer unitCount;

    private Boolean bossRoundYn;
}
