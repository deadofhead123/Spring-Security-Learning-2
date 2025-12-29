package com.sweet.acl_jwt.repo;

import com.sweet.acl_jwt.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {
    VideoEntity findByResourceId(Long resourceId);

    @Query("SELECT v FROM VideoEntity v " +
            "WHERE v.id IN :ids " +
            "AND v.isDeleted = :isDeleted ")
    List<VideoEntity> findAllByIdAndIsDeleted(
            @Param("ids") List<Long> ids,
            @Param("isDeleted") Boolean isDeleted);
}
