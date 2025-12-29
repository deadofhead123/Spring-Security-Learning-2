package com.sweet.acl_jwt.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GrantDto {
    private Long resourceId;
    private Long userGrantedId;
    private String action;
    private Date expiresAt;
}
