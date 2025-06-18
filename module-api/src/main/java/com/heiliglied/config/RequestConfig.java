package com.heiliglied.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class RequestConfig {
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
}
