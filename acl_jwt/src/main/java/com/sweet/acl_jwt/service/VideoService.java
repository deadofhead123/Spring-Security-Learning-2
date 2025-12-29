package com.sweet.acl_jwt.service;

import com.sweet.acl_jwt.dto.VideoDto;
import com.sweet.acl_jwt.dto.request.VideoRequest;
import com.sweet.acl_jwt.entity.VideoEntity;

import java.util.List;

public interface VideoService {
    VideoDto createVideo(VideoRequest videoRequest);
    VideoDto watchVideo(Long id);
    VideoDto updateVideo(Long id, VideoRequest videoRequest);
    VideoEntity findById(Long id);
    VideoEntity findByResourceId(Long resourceId);
    List<VideoEntity> findAllById(List<Long> ids);
    void deleteVideos(List<Long> ids);
}
