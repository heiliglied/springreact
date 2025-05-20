package com.heiliglied.app.services;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.heiliglied.app.dataSource.entity.User;
import com.heiliglied.app.dataSource.repository.UserRepository;
import com.heiliglied.app.extra.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Map<String, Object> signUp(Map<String, Object> data) {
        String password_regex = "^(?=.*[a-zA-Z])(?=.*[-_!@#$%^])[A-Za-z0-9-_!@#$%^]{8,10}$|^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9-_!@#$%^]{8,10}$|^(?=.*[0-9])(?=.*[-_!@#$%^])[A-Za-z0-9-_!@#$%^]{8,10}$";
        Pattern pattern = Pattern.compile(password_regex);
        Map<String, Object> response = new HashMap<>();

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

    public Map<String, Object> signIn(Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> optionalUser = userRepository.findFirstByUserId((String) data.get("user_id"));
        
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(!passwordEncoder.matches((String) data.get("password"), user.getPassword())) {
                response.put("status", "error");
                response.put("msg", "ID, 또는 비밀번호를 확인 해 주세요.");
            } else {
                response.put("status", "success");
                response.put("msg", "로그인 되었습니다.");
                response.put("user", convertUserMap(user));
            }
        } else {
            response.put("status", "error");
            response.put("msg", "ID, 또는 비밀번호를 확인 해 주세요.");
        }

        return response;
    }
    
    private Map<String, Object> convertUserMap(User user) {
        Map<String, Object> userMap = new HashMap<>();

        userMap.put("user_id", user.getUserId());
        userMap.put("role", user.getRole());
        userMap.put("name", user.getName());
        userMap.put("email", user.getEmail());
        userMap.put("password_change_date", user.getPasswordChangeDate());
        userMap.put("created_at", user.getCreatedAt());
        userMap.put("updated_at", user.getUpdatedAt());

        return userMap;
    }
}
