package org.doif.projectv.common.security.constant;

public class SecurityConstant {

    public static final String AUTH_KEY = "Authorization";
    public static final String REFRESH_TOKEN = "refresh-token";

    // 60 * 60 * 24 * 7 = 일주일
    public static final int REFRESH_TOKEN_MAX_AGE = 60 * 60 * 24 * 7;
}
