package com.heiliglied.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.heiliglied.exceptions.CustomException;
import com.heiliglied.jwt.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticateFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        final String authorization = request.getHeader("Authorization");

        try {
            if (authorization != null && authorization.startsWith("Bearer ")) {
                String jwt = authorization.substring(7);

                Map<String, Object> token = jwtTokenProvider.decodeToken("accessToken", jwt);

                if(!token.get("status").equals("success")) {
                    throw new CustomException((String)token.get("status"), "권한이 없습니다.", HttpStatus.UNAUTHORIZED);    
                }
            } else {
                throw new CustomException("expired", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
            }
        } catch(Exception e) {
            throw new CustomException("expired", "권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        filterChain.doFilter(request, response);
    }
    
}
