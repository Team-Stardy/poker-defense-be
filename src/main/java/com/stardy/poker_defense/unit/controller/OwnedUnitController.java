package com.stardy.poker_defense.unit.controller;

import com.stardy.poker_defense.unit.service.OwnedUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OwnedUnitController {

    private final OwnedUnitService ownedUnitService;

    @MessageMapping("")
    @SendTo("")
    public ResponseEntity<?> attackNormal(long ownedUnitId, long targetId, long gameUserId) {
        return ResponseEntity.ok().build();
    }

    @MessageMapping("")
    @SendTo("")
    public ResponseEntity<?> attackBoss(long ownedUnitId, long targetId, long gameUserId) {
        return ResponseEntity.ok().build();
    }


}
