package com.sweet.acl_jwt.constant;

public class ErrorMessage {
    public static class User {
        public static final String USERNAME_NOT_FOUND = "Username not found";
        public static final String USER_NOT_FOUND = "User not found";
    }

    public static class Role {
        public static final String ROLE_NOT_FOUND = "Role not found";
        public static final String ROLE_EXISTED = "Role existed";
    }

    public static class Policy {
        public static final String POLICY_NOT_FOUND = "Policy not found";
    }

    public static class Blog {
        public static final String BLOG_NOT_FOUND = "Blog not found";
    }

    public static class Resource {
        public static final String RESOURCE_NOT_FOUND = "Resource not found";
    }

    public static class Grant {
        public static final String GRANT_EXISTED = "Grant existed";
    }
}
