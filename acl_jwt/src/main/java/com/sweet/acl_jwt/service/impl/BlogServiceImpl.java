package com.sweet.acl_jwt.service.impl;

import com.sweet.acl_jwt.constant.ResourceVisibilityEnum;
import com.sweet.acl_jwt.constant.SystemConst;
import com.sweet.acl_jwt.dto.BlogDto;
import com.sweet.acl_jwt.dto.ResourceDto;
import com.sweet.acl_jwt.dto.request.BlogRequest;
import com.sweet.acl_jwt.entity.BlogEntity;
import com.sweet.acl_jwt.entity.ResourceEntity;
import com.sweet.acl_jwt.repo.BlogRepository;
import com.sweet.acl_jwt.service.BlogService;
import com.sweet.acl_jwt.service.ResourceService;
import com.sweet.acl_jwt.util.PrincipalUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final ResourceService resourceService;
    private final ModelMapper modelMapper;

    @Override
    public BlogDto createBlog(BlogRequest blogRequest) {
        BlogEntity blogEntity = modelMapper.map(blogRequest, BlogEntity.class);
        blogEntity.setStatus(blogRequest.getStatus().toString());
        blogEntity.setType(blogRequest.getType().toString());
        blogEntity.setOwnerId(PrincipalUtil.getPrincipal().getId());
        BlogEntity blogEntitySaved = blogRepository.save(blogEntity);

        blogEntitySaved.setResourceId(createResourceEntityFromBlog().getId());
        blogRepository.save(blogEntitySaved);

        return modelMapper.map(blogEntitySaved, BlogDto.class);
    }

    private ResourceEntity createResourceEntityFromBlog(){
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setOwnerId(PrincipalUtil.getPrincipal().getId());
        resourceDto.setType(SystemConst.ResourceType.BLOG);
        resourceDto.setVisibility(ResourceVisibilityEnum.PUBLIC.toString());
        return resourceService.createResource(resourceDto);
    }
}
