package com.stardy.poker_defense.user.dto;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequestDto {

    private String nickname;
}
