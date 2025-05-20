package com.heiliglied.app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heiliglied.app.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/signUp")
    public Map<String, Object> join(@RequestBody Map<String, Object> data) {
        return userService.signUp(data);
    }

    @PostMapping("/signIn")
    public Map<String, Object> login(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> result = userService.signIn(data);

        response.put("status", result.get("status"));
        response.put("msg", result.get("msg"));

        if(result.get("status").equals("success")) {
            Map userMap = (Map) response.get("user");
            //쿠키 전달.(로그인 유지를 위한 쿠키 설정)

            /*
            HttpSession session = request.getSession();
            session.setAttribute("user_id", userMap.get("user_id"));
            session.setAttribute("roll", userMap.get("role"));
            session.setAttribute("name", userMap.get("name"));
            session.setAttribute("email", userMap.get("email"));
            session.setAttribute("password_change_date", userMap.get("password_change_date"));
            session.setAttribute("created_at", userMap.get("created_at"));
            session.setAttribute("updated_at", userMap.get("updated_at"));
             */
            //response.put("JSESSIONID", "");
        } else {
            response.put("JSESSIONID", "");
        }

        return response;
    }

}
