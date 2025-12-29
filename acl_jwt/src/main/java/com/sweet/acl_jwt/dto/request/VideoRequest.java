package com.sweet.acl_jwt.dto.request;

import com.sweet.acl_jwt.enumeration.VideoTypeEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VideoRequest {
     String title;
     String description;
     String link;
     VideoTypeEnum videoTypeEnum;
     String resourceVisibility;
}
