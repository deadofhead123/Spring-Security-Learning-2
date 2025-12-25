package com.sweet.acl_jwt.service.impl;

import com.sweet.acl_jwt.constant.ErrorMessage;
import com.sweet.acl_jwt.entity.RoleEntity;
import com.sweet.acl_jwt.exception.RoleNotFoundException;
import com.sweet.acl_jwt.repo.RoleRepository;
import com.sweet.acl_jwt.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public RoleEntity findRoleByCode(String code) {
        RoleEntity role = roleRepository.findRoleByCode(code);
        if(role == null) {
            throw new RoleNotFoundException(ErrorMessage.Role.ROLE_NOT_FOUND);
        }
        return role;
    }
}
