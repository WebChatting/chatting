package com.sxrekord.chatting.exception;

import com.sxrekord.chatting.model.vo.ResponseJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.security.auth.login.AccountExpiredException;
import java.security.SignatureException;
import java.util.stream.Collectors;

/**
 * 描述: 全局错误统一处理控制中心
 * @author Rekord
 * @date 2023/4/1 11:39
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final ResponseJson ERROR;

    static {
        ERROR = new ResponseJson(HttpStatus.INTERNAL_SERVER_ERROR).setMsg("系统出错,请稍候再试");
    }

    /**
     * 描述：默认异常
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseJson defaultExceptionHandler(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ERROR;
    }

    /**
     * 用户鉴权异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccountExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseJson authenticationExceptionHandler(AccountExpiredException e) {
        log.error(e.getMessage());
        return new ResponseJson(402).setMsg("authentication failure");
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseJson signatureExceptionHandler(SignatureException e) {
        log.error(e.getMessage());
        return new ResponseJson(-1).setMsg("signature invalid");
    }

    /**
     * 描述：请求方法不支持异常提示
     * @param exception
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseJson httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException exception) {
        String supportedMethods = exception.getSupportedHttpMethods().stream()
                .map(method -> method.toString())
                .collect(Collectors.joining("/"));
        
        String msg = "请求方法不合法,请使用方法" + supportedMethods;
        return new ResponseJson(HttpStatus.METHOD_NOT_ALLOWED).setMsg(msg);
    }

    /**
     * 参数不合法
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseJson illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return new ResponseJson().setMsg(e.getMessage());
    }

    /**
     * 方法参数不合法
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseJson methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return new ResponseJson().setMsg(e.getMessage());
    }

    /**
     * 请求参数缺失
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseJson missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        return new ResponseJson().setMsg(e.getMessage());
    }

    /**
     * 描述：数据绑定失败异常提示
     * @param exception
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseJson bindExceptionHandler(BindException exception) {
        String errors = exception.getFieldErrors().stream()
                .map(error -> error.getField() + error.getDefaultMessage())
                .collect(Collectors.joining(","));
        return new ResponseJson(HttpStatus.BAD_REQUEST).setMsg(errors);
    }
}
