package com.stardy.poker_defense.user.dto;


import com.stardy.poker_defense.user.entity.User;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDto {

    private Long userId;

    public static LoginResponseDto from(User user) {

        return LoginResponseDto.builder()
                .userId(user.getId())
                .build();
    }
}
