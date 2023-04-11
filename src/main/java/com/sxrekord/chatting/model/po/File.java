package com.sxrekord.chatting.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Rekord
 * @date 2023/4/10 21:48
 */
@Data
@NoArgsConstructor
@TableName("file")
public class File {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("size")
    private String size;
    @TableField("path")
    private String path;
    @TableField("expire_policy")
    private Integer expirePolicy;
    @TableField("expire_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;

    public File(String size, String path) {
        this.size = size;
        this.path = path;
    }
}
