package com.sxrekord.chatting.exception;

import lombok.Getter;

/**
 * @author Rekord
 * @date 2023/4/27 14:24
 */
@Getter
public class ChattingIllegalArgumentException extends IllegalArgumentException {
    private final int errorCode;

    public ChattingIllegalArgumentException(String message) {
        super(message);
        this.errorCode = 400;
    }
    public ChattingIllegalArgumentException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
