package dev.rubric.discord.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "reactions")
public class Reaction {
    @Id
    private Long reactionId;
    private Long messageId;
    private Long emojiId;
    private Set<Long> userIds = new HashSet<>();
    private Instant createdAt;

    public Long getReactionId() {
        return reactionId;
    }

    public void setReactionId(Long reactionId) {
        this.reactionId = reactionId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getEmojiId() {
        return emojiId;
    }

    public void setEmojiId(Long emojiId) {
        this.emojiId = emojiId;
    }

    public Set<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Long> userIds) {
        this.userIds = userIds;
    }

    public void addUserId(Long userId) {
        userIds.add(userId);
    }

    public void removeUserId(Long userId) {
        userIds.remove(userId);
    }


    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
