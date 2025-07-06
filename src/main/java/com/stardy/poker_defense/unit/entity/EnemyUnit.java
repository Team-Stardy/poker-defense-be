package com.stardy.poker_defense.unit.entity;

import com.stardy.poker_defense.game.entity.GameUser;
import com.stardy.poker_defense.round.entity.Round;
import com.stardy.poker_defense.unit.vo.AttackResult;
import com.stardy.poker_defense.unit.vo.Reward;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "enemy_unit")
public class EnemyUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enemy_unit_id")
    private Long id;

    private Boolean killedYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_user_id")
    private GameUser gameUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id")
    private Round round;

    private Integer hp;

    private Integer defense;

    private CardSuit suit;

    private String number;

    private Double xPos;

    private Double yPos;

    private UnitType type;

    private Boolean appearanceYn;

    private Reward reward;

    public AttackResult attacked(int power) {
        Reward reward = null;
        int damage = Math.max(0, power - this.defense);
        hp -= damage;
        if (hp <= 0) {
            killedYn = true;
            reward = this.reward;
        }
        return new AttackResult(damage, killedYn, hp, reward);
    }
}
