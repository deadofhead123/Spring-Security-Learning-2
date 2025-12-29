package com.sweet.acl_jwt.dto.record.blog;

public record BlogUpdateAttributes (
    Long ownerId,
    String status,
    String type,
    String resourceVisibility
){}
