package com.stardy.poker_defense.room.repository;

import com.stardy.poker_defense.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
