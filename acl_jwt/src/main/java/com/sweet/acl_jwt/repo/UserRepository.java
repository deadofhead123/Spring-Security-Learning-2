package com.sweet.acl_jwt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sweet.acl_jwt.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
