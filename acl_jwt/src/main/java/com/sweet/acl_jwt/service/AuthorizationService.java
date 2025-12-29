package com.sweet.acl_jwt.service;

import com.sweet.acl_jwt.dto.ResourceAttributeDto;
import com.sweet.acl_jwt.entity.UserEntity;

public interface AuthorizationService {
    boolean authorize(UserEntity user, ResourceAttributeDto resource, String action);
}
