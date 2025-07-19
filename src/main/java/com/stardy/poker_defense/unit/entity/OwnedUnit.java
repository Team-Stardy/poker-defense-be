package com.stardy.poker_defense.unit.entity;

import com.stardy.poker_defense.game.entity.GameUser;
import com.stardy.poker_defense.unit.vo.AttackResult;
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

    @Setter
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

    public static OwnedUnit from(SystemUnit systemUnit) {
        return OwnedUnit.builder()
                .id(systemUnit.getId())
                .name(systemUnit.getName())
                .hp(systemUnit.getHp())
                .attackPower(systemUnit.getAttackPower())
                .attackRange(systemUnit.getAttackRange())
                .suit(systemUnit.getSuit())
                .number(systemUnit.getNumber())
                .zone("boss")
                .type(systemUnit.getType())
                .build();
    }

    public AttackResult attackNormal(EnemyUnit target) {
        return target.attacked(attackPower);
    }

    public AttackResult attackBoss(BossUnit target) {
        return target.attacked(attackPower);
    }
}
