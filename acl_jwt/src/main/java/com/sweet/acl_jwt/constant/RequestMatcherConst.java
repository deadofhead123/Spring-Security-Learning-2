package com.sweet.acl_jwt.constant;

public class RequestMatcherConst {
    public static class API {
        public static final String AUTH = "/auth/**";

    }

    public static class Method {
        public static final String POST = "POST";
    }

    public static class BypassAPI {
        public static final String LOGIN = "/auth/login";
        public static final String REGISTER = "/auth/register";
    }
}
