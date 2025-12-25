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
@Table(name = "resources")
@AttributeOverride(name = "id", column = @Column(nullable = false))
public class ResourceEntity extends BaseEntity {
    @Column(name = "type", length = 100)
    private String type;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "visibility", length = 100)
    private String visibility;
}