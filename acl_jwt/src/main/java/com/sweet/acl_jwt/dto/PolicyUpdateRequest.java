package com.sweet.acl_jwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PolicyUpdateRequest {
    private Long id;
    private String resourceType;
    private String action;
    private String conditionExpression;
}
