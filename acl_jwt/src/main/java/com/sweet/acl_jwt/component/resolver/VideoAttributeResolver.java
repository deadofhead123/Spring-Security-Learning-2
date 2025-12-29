package com.sweet.acl_jwt.component.resolver;

import com.sweet.acl_jwt.dto.ResourceAttributeDto;
import com.sweet.acl_jwt.dto.record.video.VideoDeleteAttribute;
import com.sweet.acl_jwt.dto.record.video.VideoGrantAttribute;
import com.sweet.acl_jwt.dto.record.video.VideoReadAttribute;
import com.sweet.acl_jwt.dto.record.video.VideoUpdateAttribute;
import com.sweet.acl_jwt.entity.ResourceEntity;
import com.sweet.acl_jwt.entity.VideoEntity;
import com.sweet.acl_jwt.enumeration.PolicyAction;
import com.sweet.acl_jwt.exception.EmptyListException;
import com.sweet.acl_jwt.service.ResourceService;
import com.sweet.acl_jwt.service.VideoService;
import com.sweet.acl_jwt.util.PrincipalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VideoAttributeResolver {
    private final VideoService videoService;
    private final ResourceService resourceService;

    public ResourceAttributeDto load(String resourceType, PolicyAction action, List<Long> ids) {
        switch (action) {
            case READ -> {
                VideoEntity videoEntity = videoService.findById(ids.get(0));
                ResourceEntity resourceEntity = resourceService.findById(videoEntity.getResourceId());

                return new ResourceAttributeDto(
                        resourceEntity.getId(),
                        resourceType,
                        new VideoReadAttribute(
                                videoEntity.getOwnerId(),
                                videoEntity.getIsDeleted(),
                                resourceEntity.getVisibility()
                        )
                );
            }

            case UPDATE -> {
                VideoEntity videoEntity = videoService.findById(ids.get(0));
                ResourceEntity resourceEntity = resourceService.findById(videoEntity.getResourceId());

                return new ResourceAttributeDto(
                        resourceEntity.getId(),
                        resourceType,
                        new VideoUpdateAttribute(
                                videoEntity.getOwnerId()
                        )
                );
            }

            case GRANT -> {
                Long resourceId = ids.get(0);
                VideoEntity videoEntity = videoService.findByResourceId(resourceId);
                ResourceEntity resourceEntity = resourceService.findById(resourceId);

                return new ResourceAttributeDto(
                        resourceEntity.getId(),
                        resourceType,
                        new VideoGrantAttribute(
                                videoEntity.getOwnerId(),
                                videoEntity.getIsDeleted(),
                                resourceEntity.getVisibility()
                        )
                );
            }

            case DELETE -> {
                List<VideoEntity> videoEntities = videoService.findAllById(ids);

                if(videoEntities.isEmpty()){
                    throw new EmptyListException("Cannot find any element from provided id list");
                }

                Long userId = PrincipalUtil.getPrincipal().getId();
                Long otherUserVideos = videoEntities.stream().filter(x -> !x.getOwnerId().equals(userId)).count();

                return new ResourceAttributeDto(
                        null,
                        resourceType,
                        new VideoDeleteAttribute(
                                otherUserVideos
                        )
                );
            }

            default -> throw new IllegalArgumentException("Unsupported action: " + action);
        }
    }
}
