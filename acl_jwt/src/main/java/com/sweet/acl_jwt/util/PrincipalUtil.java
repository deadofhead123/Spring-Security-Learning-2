package com.sweet.acl_jwt.util;

import com.sweet.acl_jwt.entity.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class PrincipalUtil {
    public static UserEntity getPrincipal(){
        UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return currentUser;
    }
}
