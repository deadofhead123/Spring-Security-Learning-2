package com.sweet.acl_jwt.repo;

import com.sweet.acl_jwt.entity.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {
    ResourceEntity findByIdAndType(Long resourceId, String type);
}
