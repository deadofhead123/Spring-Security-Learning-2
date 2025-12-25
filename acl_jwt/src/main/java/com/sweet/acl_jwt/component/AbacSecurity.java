package com.sweet.acl_jwt.component;

import com.sweet.acl_jwt.entity.ResourceEntity;
import com.sweet.acl_jwt.entity.UserEntity;
import com.sweet.acl_jwt.service.AuthorizationService;
import com.sweet.acl_jwt.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("abac")
@RequiredArgsConstructor
public class AbacSecurity {

    private final AuthorizationService authorizationService;
    private final ResourceService resourceService;

    public boolean check(String resourceType,
                         String action,
                         Long entityId,
                         Authentication authentication) {

        UserEntity cud = (UserEntity) authentication.getPrincipal();

        ResourceEntity resource = resourceService.load(resourceType, entityId);

        return authorizationService.authorize(
            cud,
            resource,
            action
        );
    }
}
