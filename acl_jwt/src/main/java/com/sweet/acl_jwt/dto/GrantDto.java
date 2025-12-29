package com.sweet.acl_jwt.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GrantDto {
    private Long resourceId;
    private List<Long> userGrantedIds;
    private String action;
    private Date expiresAt;
}
