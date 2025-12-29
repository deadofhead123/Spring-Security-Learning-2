package com.sweet.acl_jwt.service.impl;

import com.sweet.acl_jwt.constant.ErrorMessage;
import com.sweet.acl_jwt.dto.BlogDto;
import com.sweet.acl_jwt.dto.ResourceDto;
import com.sweet.acl_jwt.dto.request.blog.BlogRequest;
import com.sweet.acl_jwt.entity.BlogEntity;
import com.sweet.acl_jwt.entity.ResourceEntity;
import com.sweet.acl_jwt.enumeration.ResourceTypeEnum;
import com.sweet.acl_jwt.exception.BlogNotFoundException;
import com.sweet.acl_jwt.repo.BlogRepository;
import com.sweet.acl_jwt.service.BlogService;
import com.sweet.acl_jwt.service.ResourceService;
import com.sweet.acl_jwt.util.PrincipalUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final ResourceService resourceService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public BlogDto createBlog(BlogRequest blogRequest) {
        BlogEntity blogEntity = modelMapper.map(blogRequest, BlogEntity.class);
        blogEntity.setStatus(blogRequest.getStatus());
        blogEntity.setType(blogRequest.getType());
        blogEntity.setOwnerId(PrincipalUtil.getPrincipal().getId());
        BlogEntity blogEntitySaved = blogRepository.save(blogEntity);

        blogEntitySaved.setResourceId(createResourceEntityFromBlog(blogRequest.getResourceVisibility().toString()).getId());
        blogRepository.save(blogEntitySaved);

        return modelMapper.map(blogEntitySaved, BlogDto.class);
    }

    @Override
    @Transactional
    public BlogDto updateBlog(Long blogId, BlogRequest blogRequest) {
        BlogEntity existingBlog = blogRepository.findById(blogId).orElseThrow(() -> new BlogNotFoundException(ErrorMessage.Blog.BLOG_NOT_FOUND));
        existingBlog.setTitle(blogRequest.getTitle());
        existingBlog.setContent(blogRequest.getContent());
        existingBlog.setStatus(blogRequest.getStatus());
        existingBlog.setType(blogRequest.getType());

        resourceService.updateResource(existingBlog.getResourceId(), blogRequest.getResourceVisibility().toString());

        return modelMapper.map(existingBlog, BlogDto.class);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        List<BlogEntity> blogEntities = blogRepository.findAllById(ids);
        blogEntities.forEach(blogEntity -> {
            blogEntity.setIsDeleted(true);
        });
        blogRepository.saveAll(blogEntities);
    }

    @Override
    public BlogDto readBlog(Long id) {
        BlogEntity blogEntity = blogRepository.findById(id).orElseThrow(() -> new BlogNotFoundException(ErrorMessage.Blog.BLOG_NOT_FOUND));
        return modelMapper.map(blogEntity, BlogDto.class);
    }

    private ResourceEntity createResourceEntityFromBlog(String resourceVisibility){
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setOwnerId(PrincipalUtil.getPrincipal().getId());
        resourceDto.setType(ResourceTypeEnum.BLOG.toString());
        resourceDto.setVisibility(resourceVisibility);
        return resourceService.createResource(resourceDto);
    }
}
