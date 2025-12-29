package com.sweet.acl_jwt.service;

import com.sweet.acl_jwt.entity.ResourceEntity;

public interface ResourceService {
    ResourceEntity createResource(String resourceType, String resourceVisibility);
    ResourceEntity updateResource(Long resourceId, String resourceVisibility);
    ResourceEntity findById(Long id);
}

