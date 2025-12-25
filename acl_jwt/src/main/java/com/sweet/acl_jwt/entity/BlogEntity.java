package com.sweet.acl_jwt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "blogs")
@AttributeOverride(name = "id", column = @Column(nullable = false))
public class BlogEntity extends BaseEntity {
    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "status", length = 100)
    private String status;

    @Column(name = "type", length = 100)
    private String type;

    @Column(name = "resource_id", length = 100)
    private Long resourceId;
}