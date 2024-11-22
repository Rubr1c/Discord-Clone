package dev.rubric.discord.collections;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Document(collection = "roles")
public class Role {
    @Id
    private Long roleId;
    private Long serverId;
    private String name;
    private String color;
    private Integer position;
    private Set<Long> memberIds = new HashSet<>();
    private Map<String, Boolean> permissions;
    private Boolean isMentionable = false;
    private Instant createdAt;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Set<Long> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(Set<Long> memberIds) {
        this.memberIds = memberIds;
    }

    public void addMemberId(Long memberId) {
        this.memberIds.add(memberId);
    }

    public void removeMemberId(Long memberId) {
        this.memberIds.remove(memberId);
    }

    public Map<String, Boolean> getPermissions() {
        return permissions;
    }

    public void setPermissions(Map<String, Boolean> permissions) {
        this.permissions = permissions;
    }

    public void addPermission(String permission, Boolean value) {
        this.permissions.put(permission, value);
    }

    public void removePermission(String permission) {
        this.permissions.remove(permission);
    }

    public Boolean getMentionable() {
        return isMentionable;
    }

    public void setMentionable(Boolean mentionable) {
        isMentionable = mentionable;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
