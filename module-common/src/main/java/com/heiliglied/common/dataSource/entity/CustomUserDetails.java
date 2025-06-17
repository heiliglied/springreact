package com.heiliglied.common.dataSource.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
    
    private Long id;
    private String userId;
    private String password;
    private int role;
    private String name;
    private String email;
    private LocalDate passwordChangeDate;
    private String rememberToken;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.name = user.getName();
        this.email = user.getEmail();
        this.passwordChangeDate =  user.getPasswordChangeDate();
        this.rememberToken =  user.getRememberToken();
        this.createdAt =  user.getCreatedAt();
        this.updatedAt =  user.getUpdatedAt();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collections = new ArrayList<>();
        collections.add(new SimpleGrantedAuthority("ROLE_" + this.role));
        return collections;
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public int getRole() {
        return this.role;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public LocalDate getPasswordChangeDate() {
        return this.passwordChangeDate;
    }

    public String getRememberToken() {
        return this.rememberToken;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }
/*
    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부
    }
*/
    
}