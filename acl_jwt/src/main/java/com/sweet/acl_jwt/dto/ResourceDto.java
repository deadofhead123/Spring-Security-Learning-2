package com.sweet.acl_jwt.dto;

import lombok.Data;

@Data
public class ResourceDto {
    private String type;
    private Long ownerId;
    private String visibility;
}
