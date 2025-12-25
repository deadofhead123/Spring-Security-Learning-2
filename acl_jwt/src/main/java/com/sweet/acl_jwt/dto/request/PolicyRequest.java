package com.sweet.acl_jwt.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PolicyRequest {
    private String resourceType;
    private String action;
    private String conditionExpression;
}
