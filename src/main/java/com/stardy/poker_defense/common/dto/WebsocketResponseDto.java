package com.stardy.poker_defense.common.dto;

import lombok.Builder;
import lombok.Getter;

import java.awt.*;
import java.time.LocalDateTime;

@Getter
@Builder
public class WebsocketResponseDto<T>{
    private MessageType type;
    private T payload;
    private LocalDateTime timestamp;
    private String roomId;
}
