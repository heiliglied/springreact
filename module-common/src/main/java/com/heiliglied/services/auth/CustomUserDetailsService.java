package com.heiliglied.services.auth;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.heiliglied.dataSource.entity.CustomUserDetails;
import com.heiliglied.dataSource.entity.User;
import com.heiliglied.dataSource.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = userRepository.findFirstByUserId(username);
        return userDetail.map(CustomUserDetails::new)
                        .orElseThrow(
                            () -> new UsernameNotFoundException("해당하는 사용자가 없습니다.")
                        );
    }
    
}
