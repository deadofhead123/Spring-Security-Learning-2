package com.sweet.acl_jwt.repo;

import com.sweet.acl_jwt.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
    BlogEntity findByResourceId(Long resourceId);
}
