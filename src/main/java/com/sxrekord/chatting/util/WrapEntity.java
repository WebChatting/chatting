package com.sxrekord.chatting.util;

import com.sxrekord.chatting.model.po.Group;
import com.sxrekord.chatting.model.po.User;
import com.sxrekord.chatting.model.vo.ResponseJson;

/**
 * @author Rekord
 * @date 2023/3/15 20:26
 */
public class WrapEntity {
    public static void wrapUser(ResponseJson responseJson, User user, int type, int status) {
        wrapResult(responseJson, user.getId(), type, user.getUsername(), user.getAvatarPath(), status, 0);
    }

    public static void wrapGroup(ResponseJson responseJson, Group group, int type, int status) {
        wrapResult(responseJson, group.getId(), type, group.getName(), group.getAvatarPath(), status, 0);
    }

    private static void wrapResult(ResponseJson responseJson, Long id, Integer type,
                            String name, String avatarPath, Integer status, Integer online) {
        responseJson.setData("id", id)
                .setData("type", type)
                .setData("name", name)
                .setData("avatarPath", avatarPath)
                .setData("status", status)
                .setData("online", online);

        responseJson.addToCollection("relations");
    }
}
