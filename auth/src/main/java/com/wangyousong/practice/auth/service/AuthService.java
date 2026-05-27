package com.wangyousong.practice.auth.service;

import cn.dev33.satoken.stp.StpUtil;
import com.wangyousong.practice.auth.dto.AuthResponse;
import com.wangyousong.practice.auth.dto.LoginRequest;
import com.wangyousong.practice.auth.dto.RegisterRequest;
import com.wangyousong.practice.auth.entity.User;
import com.wangyousong.practice.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        userRepository.save(user);

        StpUtil.login(user.getId());
        String token = StpUtil.getTokenValue();

        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        StpUtil.login(user.getId());
        String token = StpUtil.getTokenValue();

        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }

    public void logout() {
        StpUtil.logout();
    }

    public boolean isLogin() {
        return StpUtil.isLogin();
    }

    public Long getCurrentUserId() {
        return StpUtil.getLoginIdAsLong();
    }
}
