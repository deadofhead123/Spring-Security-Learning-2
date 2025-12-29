package com.sweet.acl_jwt.service;

import com.sweet.acl_jwt.dto.GrantDto;
import com.sweet.acl_jwt.dto.request.grant.GrantRequest;

public interface GrantService {
    GrantDto createGrant(GrantRequest grantRequest);
}
