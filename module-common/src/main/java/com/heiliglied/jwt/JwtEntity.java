package com.heiliglied.jwt;

import lombok.Data;

@Data
public class JwtEntity {
    private String grantType;
    private String accessToken;
    private String refreshToken;

    public JwtEntity(String grantType, String accessToken, String refreshToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
