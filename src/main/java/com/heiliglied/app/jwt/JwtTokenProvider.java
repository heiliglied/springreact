package com.heiliglied.app.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.jboss.jandex.ClassInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.heiliglied.app.dataSource.entity.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKeyString;

    @Value("${jwt.refresh}")
    private String refreshKeyString;

    private SecretKey secretKey;
    private SecretKey refreshKey;

    private final long accessTokenValidTime = 60 * 60 * 1000L; // 1시간
    private final long refreshTokenValidTime = 60 * 60 * 24 * 14 * 1000L; // 14일

    // 의존성 주입이 완료된 후 실행되는 초기화 메서드
    @PostConstruct
    public void init() {
        // Base64 디코딩 및 SecretKey 생성은 @Value 값이 주입된 후에 수행
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKeyString));
        this.refreshKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(refreshKeyString));
    }

    // Access Token 생성
    public String createAccessToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return createToken(userDetails, "accessToken");
    }

    // Refresh Token 생성
    public String createRefreshToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return createToken(userDetails, "refreshToken");
    }

    private String createToken(CustomUserDetails userDetails, String type) {
        Date date = new Date();

        //Claims claims = Jwts.claims().build();
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", userDetails.getUsername());
        claims.put("name", userDetails.getName());
        claims.put("email", userDetails.getEmail());
        claims.put("role", userDetails.getRole());
        claims.put("password_change_date", userDetails.getPasswordChangeDate());
        claims.put("created_at", userDetails.getCreatedAt());

        JwtBuilder jwt = Jwts.builder()
                            .subject("user infomation")
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

    //검증 방식이 payload를 가져오는 방식으로 변경됨.
    public Claims decodeToken(String type, String token) {
        SecretKey key = (type.equals("accessToken")) ? secretKey : refreshKey;

        Claims claims = Jwts.parser()
                            .verifyWith(key)
                            .build()
                            .parseSignedClaims(token)
                            .getPayload();
        return claims;
    }
}