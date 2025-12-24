package com.sweet.acl_jwt.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    String username;
    String password;
    Long roleId;
}
