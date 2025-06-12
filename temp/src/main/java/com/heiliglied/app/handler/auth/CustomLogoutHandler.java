package com.heiliglied.app.handler.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if(authentication == null) {
            try {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 200 OK
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write("{\"status\":\"warning\",\"msg\":\"인증된 계정이 없습니다.\"}");
            } catch (Exception e) {
                //로그 추가.
            }
        } else {
            //세션 및 쿠키 제거.
            request.getSession().invalidate(); //세션 제거.
            
            //세션쿠키 제거.
            Cookie jsCookie = new Cookie("JSESSIONID", null);
            jsCookie.setPath("/");
            jsCookie.setMaxAge(0);
            response.addCookie(jsCookie);

            //remember-me 쿠키 제거.
            Cookie remember = new Cookie("remember-me", null);
            remember.setPath("/");
            remember.setMaxAge(0);
            response.addCookie(remember);

            try {
                response.setStatus(HttpServletResponse.SC_OK); // 200 OK
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write("{\"status\":\"success\",\"msg\":\"로그아웃 되었습니다.\"}");
            } catch (Exception e) {
                //로그 추가.
            }
        }
    }
}
