package com.sxrekord.chatting.interceptor;

import com.sxrekord.chatting.util.HeaderUtils;
import com.sxrekord.chatting.util.JwtTokenUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.login.AccountExpiredException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SignatureException;

/**
 * @author Rekord
 * @date 2022/9/10 21:22
 */
@Component
public class UserAuthInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String authorizationHeader = HeaderUtils.getAuthorizationHeader(request);
        if (authorizationHeader != null && authorizationHeader.startsWith(authorizationHeader)) {
            String accessToken = authorizationHeader.substring(HeaderUtils.authorizationPrefix.length());
            if (JwtTokenUtils.isSignatureInvalid(accessToken)) {
                throw new SignatureException("signature invalid");
            }
            if (JwtTokenUtils.isTokenExpired(accessToken)) {
                throw new AccountExpiredException("accessToken expired");
            }
            HeaderUtils.setAuthorizationHeader(response, JwtTokenUtils.refreshAccessToken(accessToken));
            return true;
        }
        throw new SignatureException("accessToken invalid");
    }

    /**
     * called after processing the request but before generating the view.
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        // when the Request.credentials is “include” mode browsers will expose the response
        // to front-end JavaScript code if the Access-Control-Allow-Credentials is set true.
        response.setHeader("Access-Control-Allow-Credentials","true");
    }

    /**
     * called after the complete request is finished and the view is generated.
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}
