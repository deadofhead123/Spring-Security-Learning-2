package com.sweet.acl_jwt.constant;

public class RequestMatcherConst {
    public static class API {
        public static final String AUTH = "/auth/**";
        public static final String BLOG = "/blogs";
        public static final String USER = "/users";
        public static final String POLICY = "/policies";
        public static final String VIDEO = "/videos";
    }

    public static class Method {
        public static final String POST = "POST";
    }

    public static class BypassAPI {
        public static final String LOGIN = "/users/login";
        public static final String REGISTER = "/users/register";
    }
}
