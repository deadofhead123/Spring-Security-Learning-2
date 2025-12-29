package com.sweet.acl_jwt.dto.record.video;

public record VideoReadAttribute(
    Long ownerId,
    Boolean isDeleted,
    String resourceVisibility
) { }
