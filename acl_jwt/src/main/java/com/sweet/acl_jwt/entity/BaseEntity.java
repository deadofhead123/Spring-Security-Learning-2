package com.sweet.acl_jwt.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class) // cho phép ghi các thuộc tính của lớp này nhưng không tạo bảng
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CreatedDate
    @Column(name = "created_time")
    Date createdTime;

    @Column(name = "created_user")
    String createdUser;

    @LastModifiedDate
    @Column(name = "updated_time")
    Date updatedTime;

    @Column(name = "updated_user")
    String updatedUser;

    @PrePersist
    public void prePersist() {
        this.updatedTime = null;
        this.updatedUser = null;
    }
}
