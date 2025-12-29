package com.sweet.acl_jwt.service;

import com.sweet.acl_jwt.dto.BlogDto;
import com.sweet.acl_jwt.dto.request.blog.BlogRequest;

import java.util.List;

public interface BlogService {
    BlogDto createBlog(BlogRequest blogRequest);
    BlogDto readBlog(Long id);
    BlogDto updateBlog(Long blogId, BlogRequest blogRequest);
    void deleteByIds(List<Long> ids);
}
