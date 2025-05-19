package com.heiliglied.app.dataSource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.heiliglied.app.dataSource.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
