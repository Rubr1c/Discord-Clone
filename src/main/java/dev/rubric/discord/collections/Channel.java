package dev.rubric.discord.collections;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "channels")
public class Channel {
    @Id
    private Long channelId;
    private Long serverId;
    private String name;
    private String type;
    private Long parentId;
    private Integer position;
    private Set<Long> memberAccessIds = new HashSet<>();
    private Set<Long> roleAccessIds = new HashSet<>();
    private Boolean nsfw = false;
    private Instant createdAt;
    private Set<Long> pinnedMessageIds = new HashSet<>();
    private Long voiceChannelId;

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Set<Long> getMemberAccessIds() {
        return memberAccessIds;
    }

    public void setMemberAccessIds(Set<Long> memberAccessIds) {
        this.memberAccessIds = memberAccessIds;
    }

    public void addMemberAccessId(Long memberId) {
        this.memberAccessIds.add(memberId);
    }

    public void removeMemberAccessId(Long memberId) {
        this.memberAccessIds.remove(memberId);
    }

    public Set<Long> getRoleAccessIds() {
        return roleAccessIds;
    }

    public void setRoleAccessIds(Set<Long> roleAccessIds) {
        this.roleAccessIds = roleAccessIds;
    }

    public void addRoleAccessId(Long roleId) {
        this.roleAccessIds.add(roleId);
    }

    public void removeRoleAccessId(Long roleId) {
        this.roleAccessIds.remove(roleId);
    }

    public Boolean getNSFW() {
        return nsfw;
    }

    public void setNSFW(Boolean NSFW) {
        nsfw = NSFW;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Long> getPinnedMessageIds() {
        return pinnedMessageIds;
    }

    public void setPinnedMessageIds(Set<Long> pinnedMessageIds) {
        this.pinnedMessageIds = pinnedMessageIds;
    }

    public void addPinnedMessageId(Long messageId) {
        pinnedMessageIds.add(messageId);
    }

    public void removePinnedMessageId(Long messageId) {
        pinnedMessageIds.remove(messageId);
    }

    public Long getVoiceChannelId() {
        return voiceChannelId;
    }

    public void setVoiceChannelId(Long voiceChannelId) {
        this.voiceChannelId = voiceChannelId;
    }
}
