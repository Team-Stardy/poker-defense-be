package com.stardy.poker_defense.room.entity;

import com.stardy.poker_defense.chatting.entity.ChattingMessage;
import com.stardy.poker_defense.game.entity.Game;
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
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    private String title;

    private String password;

    private Boolean passwordYn;

    private Boolean gameStartYn;

    private Integer maxPlayerCount;

    private Long ownerId;

    private RoomStatus status;

    @OneToOne(mappedBy = "room", fetch = FetchType.LAZY)
    private Game game;

    @Builder.Default
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<ChattingMessage> chattingMessageList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<User> userList = new ArrayList<>();

    public void startGame() {
        this.status = RoomStatus.STARTED;
    }
}
