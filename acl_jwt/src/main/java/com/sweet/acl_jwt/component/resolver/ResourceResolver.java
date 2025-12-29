package com.sweet.acl_jwt.component.resolver;

import com.sweet.acl_jwt.dto.ResourceAttributeDto;
import com.sweet.acl_jwt.enumeration.PolicyAction;
import com.sweet.acl_jwt.enumeration.ResourceTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResourceResolver {
    private final BlogAttributeResolver blogAttributeResolver;

    public ResourceAttributeDto load(ResourceTypeEnum type, PolicyAction action, List<Long> ids) {
        return switch (type) {
            case BLOG -> blogAttributeResolver.load(type.toString(), action, ids);

            case COMMENT -> null;

            default -> throw new IllegalArgumentException(
                    "Unsupported resource type: " + type
            );
        };
    }

}
