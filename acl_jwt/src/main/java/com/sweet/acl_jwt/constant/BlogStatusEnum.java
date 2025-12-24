package com.sweet.acl_jwt.constant;

public enum BlogStatusEnum {
    DRAFT("Bản nháp"),
    PUBLISHED("Đã đăng");

    private String label;

    BlogStatusEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
