package com.stardy.poker_defense.user.service.impl;

import com.stardy.poker_defense.user.dto.LoginRequestDto;
import com.stardy.poker_defense.user.dto.LoginResponseDto;
import com.stardy.poker_defense.user.entity.User;
import com.stardy.poker_defense.user.repository.UserRepository;
import com.stardy.poker_defense.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public LoginResponseDto login(LoginRequestDto params) {

        User newUser = User.builder()
                .nickname(params.getNickname())
                .build();

        User savedUser = userRepository.save(newUser);

        return LoginResponseDto.from(savedUser);
    }
}
