package com.heiliglied.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heiliglied.app.dataSource.entity.User;
import com.heiliglied.app.dataSource.repository.UserRepository;
import com.heiliglied.app.extra.CustomException;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public Map<String, Object> join(@RequestBody Map<String, Object> data) {
        String password_regex = "^(?=.*[a-zA-Z])(?=.*[-_!@#$%^])[A-Za-z0-9-_!@#$%^]{8,10}$|^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9-_!@#$%^]{8,10}$|^(?=.*[0-9])(?=.*[-_!@#$%^])[A-Za-z0-9-_!@#$%^]{8,10}$";
        Pattern pattern = Pattern.compile(password_regex);

        Map<String, Object> response = new HashMap<>();

        try {
            if(data.get("user_id") == "") {
                throw new CustomException("아이디를 입력해 주세요.");
            }

            if(data.get("password") == "") {
                throw new CustomException("비밀번호를 입력 해 주세요.");
            }

            Matcher matcher = pattern.matcher((String)data.get("password"));

            if(!matcher.matches()) {
                throw new CustomException("비밀번호는 영문 대소문자 숫자 중 두종류 이상의 8~10자 형식입니다.");
            }

            if(data.get("password") != data.get("re_password")) {
                throw new CustomException("비밀번호가 일치하지 않습니다.");
            }

            User user = User.builder()
                    .userId((String) data.get("user_id"))
                    .password(passwordEncoder.encode((String) data.get("password")))
                    .email((String) data.get("email"))
                    .name((String) data.get("name"))
                    .roll(2)
                    .build();

            userRepository.save(user);
  
            response.put("status", "success");
            response.put("msg", "가입 되었습니다.");
            return response;
        } catch (CustomException c) {
            response.put("status", "error");
            response.put("msg", c.getMessage());
            return response;
        } catch (Exception e) {
            response.put("status", "error");
            response.put("msg", "가입에 실패하였습니다.");
            return response;
        }
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();
        
        response.put("status", "error");
        response.put("msg", "가입에 실패하였습니다.");
        return response;
    }
}
