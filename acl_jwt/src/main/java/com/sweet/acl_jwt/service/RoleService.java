package com.sweet.acl_jwt.service;

import com.sweet.acl_jwt.entity.RoleEntity;

public interface RoleService {
    RoleEntity findRoleByCode(String code);
}
