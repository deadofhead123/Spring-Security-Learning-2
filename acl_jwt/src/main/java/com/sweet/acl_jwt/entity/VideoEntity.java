package com.sweet.acl_jwt.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "videos")
@AttributeOverride(name = "id", column = @Column(nullable = false))
public class VideoEntity extends BaseEntity {
    @Column(name = "description")
    private String description;

    @Column(name = "title")
    private String title;

    @Column(name = "link")
    private String link;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "type", length = 100)
    private String type;

    @Column(name = "resource_id")
    private Long resourceId;

    @Column(name = "is_deleted")
    private Boolean isDeleted  = false;
}