package com.heiliglied.app.dataSource.seeder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.heiliglied.app.dataSource.entity.User;
import com.heiliglied.app.dataSource.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class UserSeeder implements ApplicationRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        seedRole();
    }

    private void seedRole() {
        List<User> userList = new ArrayList<>();

        User user = User.builder()
                    .userId("heiliglied")
                    .password(passwordEncoder.encode("abcd12#$"))
                    .email("heiliglied@gmail.com")
                    .name("관리자")
                    .roll(0)
                    .build();

        userList.add(user);

        createUser(userList);
    }

    private void createUser(List<User> user) {
        for(User i: user) {
            userRepository.save(i);
        }
    }
}