package com.sxrekord.chatting.model.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Rekord
 * @date 2022/9/12 16:20
 */
@Data
public class Message {
    private Long id;
    private Long fromId;
    private Long toId;
    private Integer type;
    private Long contentId;
    private Integer contentType;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public Message() {}

    public Message(Long fromId, Long toId, Integer type, Integer contentType, Long contentId) {
        this(fromId, toId, type, contentType);
        this.contentId = contentId;
    }

    public Message(Long fromId, Long toId, Integer type, Integer contentType) {
        this.fromId = fromId;
        this.toId = toId;
        this.type = type;
        this.contentType = contentType;
    }
}
