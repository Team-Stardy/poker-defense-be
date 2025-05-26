package com.stardy.poker_defense.game.entity;

import com.stardy.poker_defense.chatting.entity.ChattingMessage;
import com.stardy.poker_defense.unit.entity.EnemyUnit;
import com.stardy.poker_defense.unit.entity.OwnedUnit;
import com.stardy.poker_defense.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "game_user")
public class GameUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_user_id")
    private Long id;

    private Integer life;

    private Integer gold;

    private Integer killCount;

    private Boolean surviveYn;

    @Builder.Default
    @OneToMany(mappedBy = "gameUser", fetch = FetchType.LAZY)
    private List<ChattingMessage> chattingMessageList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "gameUser", fetch = FetchType.LAZY)
    private List<OwnedUnit> ownedUnitList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "gameUser", fetch = FetchType.LAZY)
    private List<EnemyUnit> enemyUnitList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
