package com.sweet.acl_jwt.service.impl;

import com.sweet.acl_jwt.constant.SystemConst;
import com.sweet.acl_jwt.dto.ResourceDto;
import com.sweet.acl_jwt.entity.BlogEntity;
import com.sweet.acl_jwt.entity.ResourceEntity;
import com.sweet.acl_jwt.exception.DataNotFoundException;
import com.sweet.acl_jwt.repo.BlogRepository;
import com.sweet.acl_jwt.repo.ResourceRepository;
import com.sweet.acl_jwt.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final BlogRepository blogRepository;
    private final ResourceRepository resourceRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResourceEntity load(String type, Long id) {

        return switch (type) {
            case SystemConst.ResourceType.BLOG -> {
                BlogEntity blog = blogRepository.findById(id)
                        .orElseThrow(() -> new DataNotFoundException("Resource not found"));

                ResourceEntity resource = resourceRepository.findByIdAndType(blog.getResourceId(), SystemConst.ResourceType.BLOG);

                if(resource != null){ yield resource;}
                yield null;
            }

            default -> throw new IllegalArgumentException(
                    "Unsupported resource type: " + type
            );
        };
    }

    @Override
    public ResourceEntity createResource(ResourceDto resourceDto) {
        ResourceEntity resource = modelMapper.map(resourceDto, ResourceEntity.class);
        resource.setId(null);
        return modelMapper.map(resourceRepository.save(resource), ResourceEntity.class);
    }
}
