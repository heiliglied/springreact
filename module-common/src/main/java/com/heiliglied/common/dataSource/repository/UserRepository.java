package com.heiliglied.common.dataSource.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.heiliglied.common.dataSource.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findFirstByUserId(String userId);
}
