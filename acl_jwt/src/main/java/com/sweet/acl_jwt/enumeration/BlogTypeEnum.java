package com.sweet.acl_jwt.enumeration;

public enum BlogTypeEnum {
    NORMAL("Thường"),
    PREMIUM("Cao cấp");

    private String label;

    BlogTypeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
