package com.sxrekord.chatting.model.po;

import lombok.Data;

/**
 * @author Rekord
 * @date 2022/9/12 16:22
 */
@Data
public class FileContent {
    private Long id;
    private String name;
    private String size;
    private String path;

    public FileContent() {}

    public FileContent(String name, String size, String path) {
        this.name = name;
        this.size = size;
        this.path = path;
    }
}
