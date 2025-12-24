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
@Table(name = "roles")
@AttributeOverride(name = "id", column = @Column(nullable = false))
public class RoleEntity extends BaseEntity {
    @Column(name = "code", nullable = false, length = 100)
    private String code;

    @Column(name = "name", nullable = false, length = 100)
    private String name;


}