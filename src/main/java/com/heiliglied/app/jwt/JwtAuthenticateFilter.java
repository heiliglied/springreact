package com.heiliglied.app.jwt;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

        Boolean result = false;
        String status = "";

        if (authorization != null && authorization.startsWith("Bearer ")) {
            String jwt = authorization.substring(7);
            Map<String, Object> token = jwtTokenProvider.decodeToken("accessToken", jwt);

            if(token.get("status").equals("expire")) {
                //refreshToken Check
                Map<String, Object> refresh = jwtTokenProvider.decodeToken("refreshToken", request.getHeader("refreshToken"));

                if(refresh.get("status").equals("success")) {
                    result = false;
                    status = "refresh";
                }

            } else if(token.get("status").equals("error")) {
                result = false;
                status = "error";
            }
        } else {
            result = false;
            status = "error";
        }

        if(!result) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 에러.
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"status\":\"" + status + "\",\"msg\":\"로그인 해 주세요.\"}");
            return;
        } else {
            filterChain.doFilter(request, response);
        }
    }
}