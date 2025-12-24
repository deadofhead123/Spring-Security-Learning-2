package com.sweet.acl_jwt.service;

import com.sweet.acl_jwt.dto.BlogDto;
import com.sweet.acl_jwt.dto.request.BlogRequest;

public interface BlogService {
    BlogDto createBlog(BlogRequest blogRequest);
}
