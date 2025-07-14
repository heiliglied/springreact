package com.heiliglied.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heiliglied.services.auth.AuthService;

import lombok.RequiredArgsConstructor;

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
    public Map<String, String> signIn(@RequestBody Map<String, Object> data) {
        Map<String, String> response = new HashMap<>();
        authService.validateUser(data);

        response = authService.authenticate(data);

        return response;
    }

    @PostMapping("/refreshToken")
    public Map<String, Object> refreshToken(@RequestBody Map<String, Object> data) {
        return authService.refreshToken(data);
    }
}
