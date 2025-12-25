package com.sweet.acl_jwt.service;

import com.sweet.acl_jwt.dto.request.GrantRoleRequest;
import com.sweet.acl_jwt.dto.request.LoginRequest;
import com.sweet.acl_jwt.dto.request.RegisterRequest;
import com.sweet.acl_jwt.dto.response.RegisterResponse;
import com.sweet.acl_jwt.entity.UserEntity;

public interface UserService {
    UserEntity findUserByUsername(String username);
    String login(LoginRequest loginRequest) throws Exception;
    RegisterResponse register(RegisterRequest registerRequest);
    void grantRole(GrantRoleRequest grantRoleRequest);
}
