package com.sweet.acl_jwt.service.impl;

import com.sweet.acl_jwt.constant.ErrorMessage;
import com.sweet.acl_jwt.dto.VideoDto;
import com.sweet.acl_jwt.dto.request.VideoRequest;
import com.sweet.acl_jwt.entity.ResourceEntity;
import com.sweet.acl_jwt.entity.VideoEntity;
import com.sweet.acl_jwt.enumeration.ResourceTypeEnum;
import com.sweet.acl_jwt.exception.DataNotFoundException;
import com.sweet.acl_jwt.repo.VideoRepository;
import com.sweet.acl_jwt.service.ResourceService;
import com.sweet.acl_jwt.service.VideoService;
import com.sweet.acl_jwt.util.PrincipalUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final ModelMapper modelMapper;
    private final VideoRepository videoRepository;
    private final ResourceService resourceService;

    @Override
    @Transactional
    public VideoDto createVideo(VideoRequest videoRequest) {
        VideoEntity videoEntity = new VideoEntity();

        videoEntity.setTitle(videoRequest.getTitle());
        videoEntity.setDescription(videoRequest.getDescription());
        videoEntity.setLink(videoRequest.getLink());
        videoEntity.setOwnerId(PrincipalUtil.getPrincipal().getId());
        videoEntity.setType(videoRequest.getVideoTypeEnum().toString());

        ResourceEntity resourceEntity = resourceService.createResource(ResourceTypeEnum.VIDEO.toString(), videoRequest.getResourceVisibility());
        videoEntity.setResourceId(resourceEntity.getId());

        return modelMapper.map(videoRepository.save(videoEntity), VideoDto.class);
    }

    @Override
    public VideoDto watchVideo(Long id) {
        return modelMapper.map(videoRepository.findById(id).orElseThrow(() -> new DataNotFoundException(ErrorMessage.Video.VIDEO_NOT_FOUND)),  VideoDto.class);
    }

    @Override
    public VideoDto updateVideo(Long id, VideoRequest videoRequest) {
        VideoEntity videoEntity = videoRepository.findById(id).orElseThrow(() -> new DataNotFoundException(ErrorMessage.Video.VIDEO_NOT_FOUND));

        videoEntity.setTitle(videoRequest.getTitle());
        videoEntity.setDescription(videoRequest.getDescription());
        videoEntity.setLink(videoRequest.getLink());
        videoEntity.setOwnerId(PrincipalUtil.getPrincipal().getId());
        videoEntity.setType(videoRequest.getVideoTypeEnum().toString());

        ResourceEntity resourceEntity = resourceService.updateResource(videoEntity.getResourceId(), videoRequest.getResourceVisibility());

        return modelMapper.map(videoRepository.save(videoEntity), VideoDto.class);
    }

    @Override
    public VideoEntity findById(Long id) {
        return videoRepository.findById(id).orElseThrow(() -> new DataNotFoundException(ErrorMessage.Video.VIDEO_NOT_FOUND+ ", id = " + id) );
    }

    @Override
    public VideoEntity findByResourceId(Long resourceId) {
        VideoEntity videoEntity = videoRepository.findByResourceId(resourceId);
        if(videoEntity == null){
            throw new DataNotFoundException(ErrorMessage.Video.VIDEO_NOT_FOUND + ", resourceId = " + resourceId);
        }
        return videoEntity;
    }

    @Override
    public List<VideoEntity> findAllById(List<Long> ids) {
        return videoRepository.findAllByIdAndIsDeleted(ids, false);
    }

    @Override
    public void deleteVideos(List<Long> ids) {
        List<VideoEntity> videoEntities = videoRepository.findAllByIdAndIsDeleted(ids, false);
        videoEntities.forEach(item -> {
            item.setIsDeleted(true);
        });
        videoRepository.saveAll(videoEntities);
    }
}
