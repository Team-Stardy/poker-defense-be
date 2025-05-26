package com.stardy.poker_defense.user.entity;

import com.stardy.poker_defense.game.entity.GameUser;
import com.stardy.poker_defense.room.entity.Room;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private GameUser gameUser;
}
