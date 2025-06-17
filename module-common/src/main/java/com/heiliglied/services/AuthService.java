package com.heiliglied.services;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heiliglied.dataSource.entity.User;
import com.heiliglied.dataSource.repository.UserRepository;
import com.heiliglied.extra.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> signUp(Map<String, Object> data) {
        String password_regex = "^(?=.*[a-zA-Z])(?=.*[-_!@#$%^])[A-Za-z0-9-_!@#$%^]{8,10}$|^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9-_!@#$%^]{8,10}$|^(?=.*[0-9])(?=.*[-_!@#$%^])[A-Za-z0-9-_!@#$%^]{8,10}$";
        Pattern pattern = Pattern.compile(password_regex);
        Map<String, Object> response = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();

        try {
            if(data.get("user_id").equals("")) {
                throw new CustomException("아이디를 입력해 주세요."); 
            }

            if(data.get("password").equals("")) {
                throw new CustomException("비밀번호를 입력 해 주세요.");
            }

            Matcher matcher = pattern.matcher((String)data.get("password"));

            if(!matcher.matches()) {
                throw new CustomException("비밀번호는 영문 대소문자 숫자 중 두종류 이상의 8~10자 형식입니다.");
            }

            if(!data.get("password").equals(data.get("re_password"))) {
                throw new CustomException("비밀번호가 일치하지 않습니다.");
            }

            User user = User.builder()
                    .userId((String) data.get("user_id"))
                    .password((String) passwordEncoder.encode((String) data.get("password")))
                    .email((String) data.get("email"))
                    .name((String) data.get("name"))
                    .role(2)
                    .passwordChangeDate(now.toLocalDate())
                    .createdAt(now)
                    .updatedAt(now)
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
            if (e.getCause() instanceof SQLException) {
                SQLException sqlEx = (SQLException) e.getCause();
                if (sqlEx.getErrorCode() == 1062) {
                    response.put("msg", "이미 가입된 계정입니다.");
                } else {
                    response.put("msg", "이미 가입된 계정입니다.");
                }
            } else {
                response.put("msg", "가입에 실패하였습니다.");
            }

            response.put("status", "error");
            return response;
        }
    }
}
