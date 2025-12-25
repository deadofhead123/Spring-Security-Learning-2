package com.sweet.acl_jwt.repo;

import com.sweet.acl_jwt.entity.PolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolicyRepository extends JpaRepository<PolicyEntity, Long> {
    List<PolicyEntity> findByResourceTypeAndAction(String resourceType, String action);
}
