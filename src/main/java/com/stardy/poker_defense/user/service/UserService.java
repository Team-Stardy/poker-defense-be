package com.stardy.poker_defense.user.service;

import com.stardy.poker_defense.user.dto.LoginRequestDto;
import com.stardy.poker_defense.user.dto.LoginResponseDto;

public interface UserService {
    LoginResponseDto login(LoginRequestDto params);
}
