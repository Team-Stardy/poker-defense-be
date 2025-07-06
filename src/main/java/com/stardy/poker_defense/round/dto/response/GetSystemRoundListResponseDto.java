package com.stardy.poker_defense.round.dto.response;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GetSystemRoundListResponseDto {

    private Integer roundNumber;
    private Long systemNormalUnitId;
    private Long systemBossUnitId;
    private Integer unitCount;
    private Boolean bossRoundYn;
}
