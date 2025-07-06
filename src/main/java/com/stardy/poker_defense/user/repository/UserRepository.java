package com.stardy.poker_defense.user.repository;

import com.stardy.poker_defense.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.room.id = :roomId")
    List<User> findAllByRoomId(@Param("roomId") Long roomId);
}
