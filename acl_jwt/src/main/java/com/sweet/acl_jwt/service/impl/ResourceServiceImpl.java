package com.sweet.acl_jwt.service.impl;

import com.sweet.acl_jwt.constant.ErrorMessage;
import com.sweet.acl_jwt.dto.ResourceDto;
import com.sweet.acl_jwt.entity.ResourceEntity;
import com.sweet.acl_jwt.exception.ResourceNotFoundException;
import com.sweet.acl_jwt.repo.BlogRepository;
import com.sweet.acl_jwt.repo.ResourceRepository;
import com.sweet.acl_jwt.service.ResourceService;
import jakarta.transaction.Transactional;
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
    @Transactional
    public ResourceEntity createResource(ResourceDto resourceDto) {
        ResourceEntity resource = modelMapper.map(resourceDto, ResourceEntity.class);
        resource.setId(null);
        return resourceRepository.save(resource);
    }

    @Override
    @Transactional
    public ResourceEntity updateResource(Long resourceId, String resourceVisibility) {
        ResourceEntity existingResource = resourceRepository.findById(resourceId)
                                          .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.Resource.RESOURCE_NOT_FOUND));
        existingResource.setVisibility(resourceVisibility);
        return resourceRepository.save(existingResource);
    }
}
