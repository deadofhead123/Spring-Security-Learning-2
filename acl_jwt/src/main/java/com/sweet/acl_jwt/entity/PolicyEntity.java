package com.sweet.acl_jwt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "policies")
@AttributeOverride(name = "id", column = @Column(nullable = false))
public class PolicyEntity extends BaseEntity {
    @Column(name = "resource_type", length = 100)
    private String resourceType;

    @Column(name = "action", length = 20)
    private String action;

    @Lob
    @Column(name = "condition_expression")
    private String conditionExpression;
}