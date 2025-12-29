package com.sweet.acl_jwt.dto.request.grant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GrantRequest {
    Long resourceId;
    List<Long> userGrantedIds;
    String action;
}
