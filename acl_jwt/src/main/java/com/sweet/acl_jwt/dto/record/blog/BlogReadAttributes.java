package com.sweet.acl_jwt.dto.record.blog;

public record BlogReadAttributes(
        Long ownerId,
        String status,
        String type,
        Boolean isDeleted,
        String resourceVisibility
) { }
