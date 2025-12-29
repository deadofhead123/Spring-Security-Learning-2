package com.sweet.acl_jwt.dto.request;

import com.sweet.acl_jwt.enumeration.RoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrantRoleRequest {
    Long userId;
    RoleEnum role;
}
