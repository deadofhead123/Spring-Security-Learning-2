package com.sweet.acl_jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResourceAttributeDto {
    Long resourceId;
    String resourceType;
    Object data;
}
