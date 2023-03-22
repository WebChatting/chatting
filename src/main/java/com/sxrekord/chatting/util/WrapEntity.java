package com.sxrekord.chatting.util;

import com.sxrekord.chatting.model.po.Group;
import com.sxrekord.chatting.model.po.User;
import com.sxrekord.chatting.model.vo.ResponseJson;

/**
 * @author Rekord
 * @date 2023/3/15 20:26
 */
public class WrapEntity {
    public static void wrapUser(ResponseJson responseJson, User user, Long acceptId, int type, int status) {
        wrapResult(responseJson, user.getId(), acceptId, type, user.getUsername(), user.getAvatarPath(), status, 0);
    }
    public static void wrapUser(ResponseJson responseJson, User user, int type, int status) {
        wrapUser(responseJson, user, 0L, type, status);
    }

    public static void wrapGroup(ResponseJson responseJson, Group group, int type, int status) {
        wrapResult(responseJson, group.getId(), 0L, type, group.getName(), group.getAvatarPath(), status, 0);
    }

    private static void wrapResult(ResponseJson responseJson, Long id, Long acceptId, Integer type,
                            String name, String avatarPath, Integer status, Integer online) {
        responseJson.setData("id", id)
                .setData("acceptId", acceptId)
                .setData("type", type)
                .setData("name", name)
                .setData("avatarPath", avatarPath)
                .setData("status", status)
                .setData("online", online);

        responseJson.addToCollection("relations");
    }

    public static void wrapSearchResultForUser(ResponseJson responseJson, User user, Integer status) {
        wrapSearchResult(responseJson, user.getId(), user.getAvatarPath(), user.getUsername(), status);
    }

    public static void wrapSearchResultForGroup(ResponseJson responseJson, Group group, Integer status) {
        wrapSearchResult(responseJson, group.getId(), group.getAvatarPath(), group.getName(), status);
    }

    private static void wrapSearchResult(ResponseJson responseJson, Long id, String avatarPath, String name, Integer status) {
        responseJson.setData("id", id)
                .setData("avatarPath", avatarPath)
                .setData("name", name)
                .setData("status", status)
                .addToCollection("results");
    }
}
