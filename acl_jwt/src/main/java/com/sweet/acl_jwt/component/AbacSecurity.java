package com.sweet.acl_jwt.component;

import com.sweet.acl_jwt.entity.ResourceEntity;
import com.sweet.acl_jwt.entity.UserEntity;
import com.sweet.acl_jwt.service.AuthorizationService;
import com.sweet.acl_jwt.service.ResourceService;
import com.sweet.acl_jwt.util.PrincipalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("abac")
@RequiredArgsConstructor
public class AbacSecurity {

    private final AuthorizationService authorizationService;
    private final ResourceService resourceService;

    public boolean check(String resourceType, String action, Long entityId) {
        UserEntity cud = PrincipalUtil.getPrincipal();

        ResourceEntity resource = resourceService.load(resourceType, entityId);

        return authorizationService.authorize(
            cud,
            resource,
            action
        );
    }
}
