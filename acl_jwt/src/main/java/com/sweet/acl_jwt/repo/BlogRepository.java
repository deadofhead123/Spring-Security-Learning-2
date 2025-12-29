package com.sweet.acl_jwt.repo;

import com.sweet.acl_jwt.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
    BlogEntity findByResourceId(Long resourceId);
    Optional<BlogEntity> findByIdAndIsDeleted(Long id, Boolean isDeleted);
    Optional<BlogEntity> findByResourceIdAndIsDeleted(Long resourceId, Boolean isDeleted);

    @Query("SELECT b FROM BlogEntity b " +
            "WHERE b.id IN :ids " +
            "AND b.isDeleted = :isDeleted ")
    List<BlogEntity> findAllByIdAndIsDeleted(
            @Param("ids") List<Long> ids,
            @Param("isDeleted") Boolean isDeleted);
}
