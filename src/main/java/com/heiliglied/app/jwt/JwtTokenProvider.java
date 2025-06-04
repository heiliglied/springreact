package com.heiliglied.app.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.heiliglied.app.dataSource.entity.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
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

    public Map<String, Object> decodeToken(String type, String token) {
        Map<String, Object> result = new HashMap<>();

        String[] tokenParts = token.split("\\.");
        
        if (tokenParts.length != 3) {
            result.put("status", "error");
            result.put("msg", "invalid token");
            return result;
        }

        // 헤더, 페이로드, 서명 부분으로 나누기
        String header = tokenParts[0];
        String payload = tokenParts[1];
        String signature = tokenParts[2];

        // Base64 디코딩
        String decodedHeader = new String(Base64.getUrlDecoder().decode(header));
        String decodedPayload = new String(Base64.getUrlDecoder().decode(payload));

        SecretKey key = (type.equals("accessToken")) ? secretKey : refreshKey;

        String computedSignature = Jwts.builder()
            .setHeader(Jwts.parser().parseClaimsJws(token).getHeader())
            .setClaims(Jwts.parser().parseClaimsJws(token).getBody())
            .signWith(key, SignatureAlgorithm.HS256) // 사용 중인 알고리즘에 맞게 설정
            .compact()
            .split("\\.")[2]; // 서명 부분만 가져오기

        if (!signature.equals(computedSignature)) {
            result.put("status", "error");
            result.put("msg", "invalid signature");
            return result;
        }
        
        return result;
    }
}