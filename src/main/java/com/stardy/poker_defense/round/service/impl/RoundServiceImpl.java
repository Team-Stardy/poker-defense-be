package com.stardy.poker_defense.round.service.impl;

import com.stardy.poker_defense.round.dto.request.CreateSystemRoundRequestDto;
import com.stardy.poker_defense.round.dto.response.GetSystemRoundListResponseDto;
import com.stardy.poker_defense.round.entity.SystemRound;
import com.stardy.poker_defense.round.exception.RoundErrorCode;
import com.stardy.poker_defense.round.exception.RoundException;
import com.stardy.poker_defense.round.repository.RoundRepository;
import com.stardy.poker_defense.round.repository.SystemRoundRepository;
import com.stardy.poker_defense.round.service.RoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoundServiceImpl implements RoundService {

    private final RoundRepository roundRepository;
    private final SystemRoundRepository systemRoundRepository;

    @Override
    public List<GetSystemRoundListResponseDto> getSystemRoundList() {

        List<GetSystemRoundListResponseDto> resultList = systemRoundRepository.findAll().stream()
                .map(sr -> GetSystemRoundListResponseDto.builder()
                        .roundNumber(sr.getRoundNumber())
                        .systemUnitId(sr.getSystemUnitId())
                        .unitCount(sr.getUnitCount())
                        .bossRoundYn(sr.getBossRoundYn())
                        .build())
                .toList();

        return resultList;
    }

    @Override
    public void createSystemRound(CreateSystemRoundRequestDto params) {

        if(systemRoundRepository.findByRoundNumber(params.getRoundNumber()).isPresent()) {

            throw new RoundException(RoundErrorCode.ALREADY_EXIST_SYSTEM_ROUND);
        }

        SystemRound newSystemRound = SystemRound.builder()
                .roundNumber(params.getRoundNumber())
                .unitCount(params.getUnitCount())
                .bossRoundYn(params.getBossRoundYn())
                .systemUnitId(params.getSystemUnitId())
                .build();

        systemRoundRepository.save(newSystemRound);
    }
}
