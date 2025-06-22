package com.heiliglied.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.heiliglied.services.auth.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests.requestMatchers(
                            "/", "/auth/signIn", "/auth/signUp", 
                                        "/error", 
                                        "/auth/refreshToken"
                                    )
                                    .permitAll()
                                    .anyRequest()
                                    .permitAll()
                                    //.authenticated() //securitycontext 등록해야 사용 가능함. JWT기 때문에 여기선 필요 없음.
        ).csrf(csrf -> csrf.disable()
        );//.addFilter(jwtAuthenticateFilter);
        /* JWT 토큰 사용할 거라 의미 없음. 세션 사용시에 확인.
        .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                                .addLogoutHandler(customLogoutHandler)
                                //.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)) // react 호출이기 때문에 핸들러에서 일괄처리.
                                //.invalidateHttpSession(true)
                                //.deleteCookies("JSESSIONID", "remember-me")
        );
        */

        return http.build();
    }

    private String idForEncode = "bcrypt";

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        // 다른 인코더 추가 가능
        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
