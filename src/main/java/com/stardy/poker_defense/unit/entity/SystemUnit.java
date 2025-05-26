package com.stardy.poker_defense.unit.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "system_unit")
public class SystemUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "system_unit_id")
    private Long id;

    private String name;

    private Integer hp;

    private Integer AttackPower;

    private Integer AttackRange;

    private Integer defense;

    private CardSuit suit;

    private String number;

    private Double xPos;

    private Double yPos;

    private UnitType type;
}
