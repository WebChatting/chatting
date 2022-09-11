package com.sxrekord.chatting.model.po;

import java.util.List;

/**
 * @author Rekord
 * @date 2022/9/10 14:16
 */
public class GroupInfo {

    private String groupId;
    private String groupName;
    private String groupAvatarUrl;
    private List<UserInfo> members;
    
    public GroupInfo(String groupId, String groupName, String groupAvatarUrl, List<UserInfo> members) {
        super();
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupAvatarUrl = groupAvatarUrl;
        this.members = members;
    }

    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public List<UserInfo> getMembers() {
        return members;
    }
    public void setMembers(List<UserInfo> members) {
        this.members = members;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupAvatarUrl() {
        return groupAvatarUrl;
    }

    public void setGroupAvatarUrl(String groupAvatarUrl) {
        this.groupAvatarUrl = groupAvatarUrl;
    }
    
}
