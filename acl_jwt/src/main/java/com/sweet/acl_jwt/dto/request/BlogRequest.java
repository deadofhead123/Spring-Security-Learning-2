package com.sweet.acl_jwt.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogRequest {
    private String title;
    private String content;
    private Long ownerId;
    private String status;
    private String type;
}
