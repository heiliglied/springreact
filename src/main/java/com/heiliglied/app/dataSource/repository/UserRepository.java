package com.heiliglied.app.dataSource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.heiliglied.app.dataSource.entity.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findFirstByUserId(String userId);
}
