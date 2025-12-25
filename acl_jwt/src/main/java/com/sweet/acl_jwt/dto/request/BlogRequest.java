package com.sweet.acl_jwt.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogRequest {
    private String title;
    private String content;
    private String status;
    private String type;
}
