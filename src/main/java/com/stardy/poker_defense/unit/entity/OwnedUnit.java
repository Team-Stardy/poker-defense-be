package com.stardy.poker_defense.unit.entity;

import com.stardy.poker_defense.game.entity.GameUser;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "owned_unit")
public class OwnedUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owned_unit_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_user_id")
    private GameUser gameUser;

    private String name;

    private Integer hp;

    private Integer attackPower;

    private Integer attackRange;

    private CardSuit suit;

    private String number;

    private Double xPos;

    private Double yPos;

    private String zone; // boss or player

    private UnitType type;

    private Integer price;
}
