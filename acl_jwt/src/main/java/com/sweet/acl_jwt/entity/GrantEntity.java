package com.sweet.acl_jwt.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "grants")
@AttributeOverride(name = "id", column = @Column(nullable = false))
public class GrantEntity extends BaseEntity {
    @Column(name = "resource_id")
    private Long resourceId;

    @Column(name = "user_granted_id")
    private Long userGrantedId;

    @Column(name = "action", length = 20)
    private String action;

    @Column(name = "expires_at")
    private Date expiresAt;
}