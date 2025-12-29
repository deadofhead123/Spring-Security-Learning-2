package com.sweet.acl_jwt.component;

import com.sweet.acl_jwt.component.resolver.ResourceResolver;
import com.sweet.acl_jwt.dto.ResourceAttributeDto;
import com.sweet.acl_jwt.entity.UserEntity;
import com.sweet.acl_jwt.enumeration.PolicyAction;
import com.sweet.acl_jwt.enumeration.ResourceTypeEnum;
import com.sweet.acl_jwt.service.AuthorizationService;
import com.sweet.acl_jwt.util.PrincipalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("abac")
@RequiredArgsConstructor
public class AbacSecurity {
    private final AuthorizationService authorizationService;
    private final ResourceResolver resourceResolver;

    public boolean check(ResourceTypeEnum resourceTypeEnum, PolicyAction action, List<Long> entityId) {
        UserEntity cud = PrincipalUtil.getPrincipal();

        ResourceAttributeDto resource = resourceResolver.load(resourceTypeEnum, action, entityId);

        return authorizationService.authorize(cud, resource, action.toString());
    }
}
