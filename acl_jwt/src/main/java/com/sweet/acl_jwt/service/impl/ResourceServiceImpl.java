package com.sweet.acl_jwt.service.impl;

import com.sweet.acl_jwt.constant.ErrorMessage;
import com.sweet.acl_jwt.entity.ResourceEntity;
import com.sweet.acl_jwt.exception.DataNotFoundException;
import com.sweet.acl_jwt.exception.ResourceNotFoundException;
import com.sweet.acl_jwt.repo.BlogRepository;
import com.sweet.acl_jwt.repo.ResourceRepository;
import com.sweet.acl_jwt.service.ResourceService;
import com.sweet.acl_jwt.util.PrincipalUtil;
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
    public ResourceEntity createResource(String resourceType, String resourceVisibility) {
        ResourceEntity resource = new ResourceEntity();
        resource.setId(null);
        resource.setType(resourceType);
        resource.setVisibility(resourceVisibility);
        resource.setOwnerId(PrincipalUtil.getPrincipal().getId());
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

    @Override
    public ResourceEntity findById(Long id) {
        return resourceRepository.findById(id).orElseThrow(() -> new DataNotFoundException(ErrorMessage.Resource.RESOURCE_NOT_FOUND));
    }
}
