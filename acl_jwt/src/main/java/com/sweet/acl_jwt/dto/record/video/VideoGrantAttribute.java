package com.sweet.acl_jwt.dto.record.video;

public record VideoGrantAttribute(
    Long ownerId,
    Boolean isDeleted,
    String resourceVisibility
) { }
