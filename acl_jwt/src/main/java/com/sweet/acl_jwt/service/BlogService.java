package com.sweet.acl_jwt.service;

import com.sweet.acl_jwt.dto.BlogDto;
import com.sweet.acl_jwt.dto.request.blog.BlogRequest;
import com.sweet.acl_jwt.entity.BlogEntity;

import java.util.List;

public interface BlogService {
    BlogDto createBlog(BlogRequest blogRequest);
    BlogDto readBlog(Long id);
    BlogEntity findById(Long id);
    BlogEntity findByResourceId(Long resourceId);
    List<BlogEntity> findAllById(List<Long> ids);
    BlogDto updateBlog(Long blogId, BlogRequest blogRequest);
    void deleteByIds(List<Long> ids);
}
