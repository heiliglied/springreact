package com.heiliglied.app.dataSource.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "role", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Integer role;
    
    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "password_change_date", columnDefinition = "DATE")
    private LocalDate passwordChangeDate;

    @Column(name = "remember_token")
    private String rememberToken;

    @Column(name = "created_at", columnDefinition = "DATE")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "DATE")
    private LocalDateTime updatedAt;
}
