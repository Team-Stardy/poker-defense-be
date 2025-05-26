package com.stardy.poker_defense.round.service;

import com.stardy.poker_defense.round.dto.request.CreateSystemRoundRequestDto;
import com.stardy.poker_defense.round.dto.response.GetSystemRoundListResponseDto;

import java.util.List;

public interface RoundService {
    List<GetSystemRoundListResponseDto> getSystemRoundList();

    void createSystemRound(CreateSystemRoundRequestDto params);
}
