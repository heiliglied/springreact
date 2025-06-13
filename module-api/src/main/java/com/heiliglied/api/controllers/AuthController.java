package com.heiliglied.api.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/signUp")
    public Map<String, Object> signUp(@RequestBody Map<String, Object> data) {
        //return authService.signUp(data);

        return data;
    }
}
