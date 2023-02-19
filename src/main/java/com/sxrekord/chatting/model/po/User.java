package com.sxrekord.chatting.model.po;

import lombok.Data;

import java.util.List;

/**
 * @author Rekord
 * @date 2022/9/12 12:14
 */
@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String avatarPath;
    private List<User> friendList;
    private List<Group> groupList;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String avatarPath) {
        this(username, password);
        this.avatarPath = avatarPath;
    }

    public User(Long id, String username, String password, String avatarPath) {
        this(username, password, avatarPath);
        this.id = id;
    }
}
