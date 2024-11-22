package dev.rubric.discord.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Document(collection = "servers")
public class Server {
    @Id
    private Long serverId;
    private String name;
    private Long ownerId;
    private String iconUrl;
    private Instant createdAt;
    private Set<Long> memberIds = new HashSet<>();
    private Set<Long> channelIds = new HashSet<>();
    private Set<Long> roleIds = new HashSet<>();
    private Map<Long, Long> userRoles = new HashMap<>();
    private Set<Integer> emojiIds = new HashSet<>();
    private String description;
    private Boolean isPublic = false;
    private Long settingsId;

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Long> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(Set<Long> memberIds) {
        this.memberIds = memberIds;
    }

    public void addMember(Long memberId) {
        memberIds.add(memberId);
    }

    public void removeMember(Long memberId) {
        memberIds.remove(memberId);
    }

    public Set<Long> getChannelIds() {
        return channelIds;
    }

    public void setChannelIds(Set<Long> channelIds) {
        this.channelIds = channelIds;
    }

    public void addChannel(Long channelId) {
        channelIds.add(channelId);
    }

    public void removeChannel(Long channelId) {
        channelIds.remove(channelId);
    }

    public Set<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public void addRole(Long roleId) {
        roleIds.add(roleId);
    }

    public void removeRole(Long roleId) {
        roleIds.remove(roleId);
    }

    public Map<Long, Long> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Map<Long, Long> userRoles) {
        this.userRoles = userRoles;
    }

    public void addUserRole(Long userId, Long roleId) {
        userRoles.put(userId, roleId);
    }

    public void removeUserRole(Long userId) {
        userRoles.remove(userId);
    }

    public Set<Integer> getEmojiIds() {
        return emojiIds;
    }

    public void setEmojiIds(Set<Integer> emojiIds) {
        this.emojiIds = emojiIds;
    }

    public void addEmoji(Integer emojiId) {
        emojiIds.add(emojiId);
    }

    public void removeEmoji(Integer emojiId) {
        emojiIds.remove(emojiId);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Long getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(Long settingsId) {
        this.settingsId = settingsId;
    }
}
