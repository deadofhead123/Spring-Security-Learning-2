package com.sweet.acl_jwt.repo;

import com.sweet.acl_jwt.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findRoleByCode(String code);
}
