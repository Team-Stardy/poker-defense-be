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

    private Long systemUnitId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_user_id")
    private GameUser gameUser;
}
