package com.sweet.acl_jwt.component.resolver;

import com.sweet.acl_jwt.constant.ErrorMessage;
import com.sweet.acl_jwt.dto.ResourceAttributeDto;
import com.sweet.acl_jwt.dto.record.blog.BlogDeleteAttributes;
import com.sweet.acl_jwt.dto.record.blog.BlogGrantAttributes;
import com.sweet.acl_jwt.dto.record.blog.BlogReadAttributes;
import com.sweet.acl_jwt.dto.record.blog.BlogUpdateAttributes;
import com.sweet.acl_jwt.entity.BlogEntity;
import com.sweet.acl_jwt.entity.ResourceEntity;
import com.sweet.acl_jwt.enumeration.PolicyAction;
import com.sweet.acl_jwt.exception.BlogNotFoundException;
import com.sweet.acl_jwt.exception.DataNotFoundException;
import com.sweet.acl_jwt.repo.BlogRepository;
import com.sweet.acl_jwt.repo.ResourceRepository;
import com.sweet.acl_jwt.util.PrincipalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BlogAttributeResolver {
    private final BlogRepository blogRepository;
    private final ResourceRepository resourceRepository;

    public ResourceAttributeDto load(String resourceType, PolicyAction action, List<Long> ids) {
        return switch (action) {
            case READ -> {
                // ids lúc này là List blog id
                BlogEntity blog = blogRepository.findById(ids.get(0)).orElseThrow(() -> new DataNotFoundException(ErrorMessage.Blog.BLOG_NOT_FOUND));
                ResourceEntity resourceEntity = resourceRepository.findById(blog.getResourceId())
                                                .orElseThrow(() -> new DataNotFoundException(ErrorMessage.Resource.RESOURCE_NOT_FOUND));

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
                BlogEntity blog = blogRepository.findById(ids.get(0)).orElseThrow(() -> new DataNotFoundException(ErrorMessage.Blog.BLOG_NOT_FOUND));
                ResourceEntity resourceEntity = resourceRepository.findById(blog.getResourceId())
                                                .orElseThrow(() -> new DataNotFoundException(ErrorMessage.Resource.RESOURCE_NOT_FOUND));

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
                List<BlogEntity> blogEntities = blogRepository.findAllById(ids);
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
                BlogEntity blog = blogRepository.findByResourceId(resourceId);
                if(blog == null){
                    throw new BlogNotFoundException(ErrorMessage.Blog.BLOG_NOT_FOUND);
                }

                ResourceEntity resourceEntity = resourceRepository.findById(resourceId)
                                                .orElseThrow(() -> new DataNotFoundException(ErrorMessage.Resource.RESOURCE_NOT_FOUND));

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
