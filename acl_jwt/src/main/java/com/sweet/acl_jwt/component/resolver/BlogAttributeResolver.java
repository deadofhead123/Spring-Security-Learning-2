package com.sweet.acl_jwt.component.resolver;

import com.sweet.acl_jwt.dto.ResourceAttributeDto;
import com.sweet.acl_jwt.dto.record.blog.BlogDeleteAttributes;
import com.sweet.acl_jwt.dto.record.blog.BlogGrantAttributes;
import com.sweet.acl_jwt.dto.record.blog.BlogReadAttributes;
import com.sweet.acl_jwt.dto.record.blog.BlogUpdateAttributes;
import com.sweet.acl_jwt.entity.BlogEntity;
import com.sweet.acl_jwt.entity.ResourceEntity;
import com.sweet.acl_jwt.enumeration.PolicyAction;
import com.sweet.acl_jwt.service.BlogService;
import com.sweet.acl_jwt.service.ResourceService;
import com.sweet.acl_jwt.util.PrincipalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BlogAttributeResolver {
    private final BlogService blogService;
    private final ResourceService resourceService;

    public ResourceAttributeDto load(String resourceType, PolicyAction action, List<Long> ids) {
        return switch (action) {
            case READ -> {
                // ids lúc này là List blog id
                BlogEntity blog = blogService.findById(ids.get(0));
                ResourceEntity resourceEntity = resourceService.findById(blog.getResourceId());

                yield new ResourceAttributeDto(
                        blog.getResourceId(),
                        resourceType,
                        new BlogReadAttributes(
                                blog.getOwnerId(),
                                blog.getStatus(),
                                blog.getType(),
                                blog.getIsDeleted(),
                                resourceEntity.getVisibility()
                        )
                );
            }

            case UPDATE -> {
                // ids lúc này là List blog id
                BlogEntity blog = blogService.findById(ids.get(0));
                ResourceEntity resourceEntity = resourceService.findById(blog.getResourceId());

                yield new ResourceAttributeDto(
                        blog.getResourceId(),
                        resourceType,
                        new BlogUpdateAttributes(
                                blog.getOwnerId(),
                                blog.getStatus(),
                                blog.getType(),
                                resourceEntity.getVisibility()
                        )
                );
            }

            case DELETE -> {
                // ids lúc này là List blog id
                List<BlogEntity> blogEntities = blogService.findAllById(ids);
                Long userId = PrincipalUtil.getPrincipal().getId();
                Long otherUserBlogs = blogEntities.stream().filter(x -> !x.getOwnerId().equals(userId)).count();

                yield new ResourceAttributeDto(
                        null,
                        resourceType,
                        new BlogDeleteAttributes(
                            otherUserBlogs
                        )
                );
            }

            case GRANT -> {
                // ids lúc này là List resource id
                Long resourceId = ids.get(0);
                BlogEntity blog = blogService.findByResourceId(resourceId);
                ResourceEntity resourceEntity = resourceService.findById(resourceId);

                yield new ResourceAttributeDto(
                        blog.getResourceId(),
                        resourceType,
                        new BlogGrantAttributes(
                                blog.getOwnerId(),
                                blog.getStatus(),
                                blog.getType(),
                                resourceEntity.getVisibility()
                        )
                );
            }

            default -> throw new IllegalArgumentException(
                    "Unsupported action: " + action
            );
        };
    }
}
