package com.sweet.acl_jwt.service.impl;

import com.sweet.acl_jwt.dto.BlogDto;
import com.sweet.acl_jwt.dto.request.BlogRequest;
import com.sweet.acl_jwt.entity.BlogEntity;
import com.sweet.acl_jwt.repo.BlogRepository;
import com.sweet.acl_jwt.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final ModelMapper modelMapper;

    @Override
    public BlogDto createBlog(BlogRequest blogRequest) {
        BlogEntity blogEntity = modelMapper.map(blogRequest, BlogEntity.class);
        blogEntity.setStatus(blogRequest.getStatus().toString());
        blogEntity.setType(blogRequest.getType().toString());
        return modelMapper.map(blogRepository.save(blogEntity), BlogDto.class);
    }
}
