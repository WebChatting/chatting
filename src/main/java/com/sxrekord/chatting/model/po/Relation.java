package com.sxrekord.chatting.model.po;

import lombok.Data;

/**
 * @author Rekord
 * @date 2022/9/12 16:05
 */
@Data
public class Relation {
    private Long id;
    private Long requestId;
    private Long acceptId;
    private Integer type;
    private Integer status;

    public Relation() {}
}
