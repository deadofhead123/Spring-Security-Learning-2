package com.sweet.acl_jwt.service;

import com.sweet.acl_jwt.entity.ResourceEntity;
import com.sweet.acl_jwt.entity.UserEntity;

public interface AuthorizationService {
    boolean authorize(UserEntity user, ResourceEntity resource, String action);
}
