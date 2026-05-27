package com.wangyousong.practice.auth.controller;

import com.wangyousong.practice.auth.dto.AuthResponse;
import com.wangyousong.practice.auth.dto.LoginRequest;
import com.wangyousong.practice.auth.dto.RegisterRequest;
import com.wangyousong.practice.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        authService.logout();
        Map<String, String> result = new HashMap<>();
        result.put("message", "退出成功");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkLogin() {
        Map<String, Object> result = new HashMap<>();
        boolean isLogin = authService.isLogin();
        result.put("isLogin", isLogin);
        if (isLogin) {
            result.put("userId", authService.getCurrentUserId());
        }
        return ResponseEntity.ok(result);
    }
}
