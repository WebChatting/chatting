package com.sxrekord.chatting.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rekord
 * @date 2023/4/10 15:02
 */
public class HeaderUtils {
    public static final String authorizationPrefix = "Bearer ";

    public static void setAuthorizationHeader(HttpServletResponse response, String accessToken) {
        response.setHeader("Authorization", authorizationPrefix + accessToken);
    }

    public static String getAuthorizationHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public static Long getUserId(HttpServletRequest request) {
        return JwtTokenUtils.parseUserId(getAuthorizationHeader(request).substring(authorizationPrefix.length()));
    }
}
