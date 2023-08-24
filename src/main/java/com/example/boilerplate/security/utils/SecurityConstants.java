package com.example.boilerplate.security.utils;

public class SecurityConstants {
    public static final String JWT_PREFIX = "Bearer ";

    public static final String BASIC_PREFIX = "Basic ";
    public static final String HEADER_STRING = "Authorization";

    public static final String LOGIN_REQUEST_URI = "/login";

    public static final String REGISTRATION_REQUEST_URI = "/register";

    private SecurityConstants(){
        throw new UnsupportedOperationException();
    }

}
