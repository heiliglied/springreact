package com.heiliglied.app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heiliglied.app.services.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public Map<String, Object> signUp(@RequestBody Map<String, Object> data) {
        return authService.signUp(data);
    }

    @PostMapping("/signIn")
    public Map<String, Object> signIn(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        return authService.signIn(data, request);
    }

    @PostMapping("/refreshToken")
    public Map<String, Object> refreshToken(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        return authService.refreshToken(data, request);
    }
    
}
