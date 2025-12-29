package com.sweet.acl_jwt.repo;

import com.sweet.acl_jwt.entity.GrantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GrantRepository extends JpaRepository<GrantEntity, Long> {
    @Query("SELECT gr FROM GrantEntity gr " +
            "WHERE gr.userGrantedId = :userId " +
            "AND gr.resourceId = :resourceId " +
            "AND gr.action = :action ")
    GrantEntity existsValidGrantEntity(
            @Param("userId") Long userId,
            @Param("resourceId") Long resourceId,
            @Param("action") String action);
}
