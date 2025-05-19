package com.heiliglied.app.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;

import jakarta.annotation.PostConstruct;

@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final DelegatingPasswordEncoder passwordEncoder;
    private String idForEncode = "bcrypt";
    private Map<String, PasswordEncoder> encoders = new HashMap<>();

    SecurityConfig(DelegatingPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        //encoders.put("pbkdf2", new Pbkdf2PasswordEncoder("secret", 10000, 256, SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512));
        //encoders.put("scrypt", new SCryptPasswordEncoder(16384, 8, 1, 32, 16));
        //encoders.put("sha256", new StandardPasswordEncoder());
    }

    @Bean
    public DelegatingPasswordEncoder passwordEncoder() {
        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }

    /*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll() // 기본적으로 모든 요청 허용
            );

        return http.build();
    }
    */
}

