package com.heiliglied.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heiliglied.dataSource.entity.CustomUserDetails;
import com.heiliglied.exceptions.CustomException;
import com.heiliglied.jwt.JwtTokenProvider;
import com.heiliglied.services.auth.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signUp")
    public Map<String, Object> signUp(@RequestBody Map<String, Object> data) {
        return authService.signUp(data);
    }

    @PostMapping("/signIn")
    public Map<String, Object> signIn(@RequestBody Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();
        authService.validateUser(data);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(data.get("user_id"), data.get("password"));
        Authentication authentication = authenticationManager.authenticate(authToken);

        if(authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);

            response.put("status", "success");
            response.put("msg", "로그인 되었습니다.");
            response.put("accessToken", jwtTokenProvider.createAccessToken((CustomUserDetails)authentication.getPrincipal()));
            response.put("refreshToken", jwtTokenProvider.createRefreshToken((CustomUserDetails)authentication.getPrincipal()));

            return response;
        } else {
            throw new CustomException("error", "인증에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/refreshToken")
    public Map<String, Object> refreshToken(@RequestBody Map<String, Object> data) {
        return authService.refreshToken(data);
    }
}
