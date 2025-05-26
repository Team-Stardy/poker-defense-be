package com.stardy.poker_defense.round.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateSystemRoundRequestDto {

    private Integer roundNumber;
    private Integer unitCount;
    private Boolean bossRoundYn;
    private Long systemUnitId;
}
