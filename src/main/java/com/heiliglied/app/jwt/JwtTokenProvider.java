package com.heiliglied.app.jwt;

import java.util.Base64;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private String secretKey = "${jwt.secret}";
    private long tokenValidTime = 60 * 60 * 1000L; // 1시간.
    private long expireTime = 60 * 60 * 24 * 14 * 1000L; // 14일 2주.

    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(Authentication authentication) {
        String accessToken = "";
        
        

        return accessToken;
    }
}
