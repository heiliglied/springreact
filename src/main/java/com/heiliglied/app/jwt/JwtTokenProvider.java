package com.heiliglied.app.jwt;

import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.heiliglied.app.dataSource.entity.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private String secretKeyString = "1P3StbeTCYpuFXhgmmTvErjWXkJU4XiRG2kGbMi4AHGeZrpV5riFQzHAh02crDKBvqKyjSVvGk7fbinCJi99RxzXGW";
    private String refreshKeyString = "tV3PVxJ3Yvizyk8nJpZtUu96A1vpzuCHkvZgY7DzarXQJ9AMJE6xpVLVdYURqHenFuec2cCy5BASuWq7YFZjc1QzeN";
    private final long accessTokenValidTime = 60 * 60 * 1000L; // 1시간
    private final long refreshTokenValidTime = 60 * 60 * 24 * 14 * 1000L; // 14일

    private final SecretKey secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKeyString));
    private final SecretKey refreshKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(refreshKeyString));

    // Access Token 생성
    public String createAccessToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return createToken(userDetails, "accessToken");
    }

    // Refresh Token 생성
    public String createRefreshToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return createToken(userDetails, "refresh-token");
    }

    private String createToken(CustomUserDetails userDetails, String type) {
        Date date = new Date();

        Claims claims = Jwts.claims().build();
        claims.put("user_id", userDetails.getUsername());
        claims.put("name", userDetails.getName());
        claims.put("email", userDetails.getEmail());
        claims.put("role", userDetails.getRole());
        claims.put("password_change_date", userDetails.getPasswordChangeDate());
        claims.put("created_at", userDetails.getCreatedAt());

        JwtBuilder jwt = Jwts.builder()
                            .claims(claims)
                            .subject(userDetails.getUsername()) // 최신 방식 적용
                            .issuedAt(date);

        if(type.equals("accessToken")) {
            jwt = jwt.signWith(secretKey)
                    .expiration(new Date(date.getTime() + this.accessTokenValidTime));
        } else {
            jwt = jwt.signWith(refreshKey)
                    .expiration(new Date(date.getTime() + this.refreshTokenValidTime));

        }

        return jwt.compact();
    }
}