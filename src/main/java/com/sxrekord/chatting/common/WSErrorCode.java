package com.sxrekord.chatting.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Rekord
 * @date 2023/4/24 14:44
 */
@AllArgsConstructor
@Getter
public enum WSErrorCode {
    /**
     * Invalid data type
     */
    INVALID_DATA_TYPE(1003, "Invalid data type"),
    /**
     * Invalid JSON format
     */
    INVALID_JSON_FORMAT(1007, "Invalid JSON format"),
    /**
     * Message content is empty
     */
    MESSAGE_CONTENT_EMPTY(1003, "Message content is empty"),
    /**
     * Required field is missing in the request
     */
    MISSING_REQUIRED_FIELD(1003, "Required field is missing");

    private final int code;
    private final String message;
}
