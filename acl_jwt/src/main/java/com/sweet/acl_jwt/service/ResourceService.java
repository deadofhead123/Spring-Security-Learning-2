package com.sweet.acl_jwt.service;

import com.sweet.acl_jwt.dto.ResourceDto;
import com.sweet.acl_jwt.entity.ResourceEntity;

public interface ResourceService {
    ResourceEntity createResource(ResourceDto resourceDto);
    ResourceEntity updateResource(Long resourceId, String resourceVisibility);
}

