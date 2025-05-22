package com.heiliglied.app.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticateFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        final String authorization = request.getHeader("Authorization");
/*
        if(authorization != null || authorization.startsWith("Bearer ")) {
            String jwt = authorization.substring(7);

            try {
                //String user = JwtTokenProvider.
            } catch(ExpiredJwtException e) {

            } catch(JwtException e) {

            }
        }
*/
        filterChain.doFilter(request, response);
    }
    
}
