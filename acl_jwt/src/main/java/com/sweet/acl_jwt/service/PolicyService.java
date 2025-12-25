package com.sweet.acl_jwt.service;

import com.sweet.acl_jwt.dto.PolicyDto;
import com.sweet.acl_jwt.dto.request.PolicyRequest;

public interface PolicyService {
    PolicyDto createPolicy(PolicyRequest policyRequest);
}
