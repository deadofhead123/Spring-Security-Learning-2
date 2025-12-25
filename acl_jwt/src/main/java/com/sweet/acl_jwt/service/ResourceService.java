package com.sweet.acl_jwt.service;

import com.sweet.acl_jwt.dto.ResourceDto;
import com.sweet.acl_jwt.entity.ResourceEntity;

public interface ResourceService {
    ResourceEntity load(String type, Long id);
    ResourceEntity createResource(ResourceDto resourceDto);
}
