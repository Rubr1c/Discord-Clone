package dev.rubric.discord.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.*;

@Document(collection = "messages")
public class Message {
    @Id
    private Long messageId;

    private Long senderId;
    private Long receiverId;
    private Long channelId;
    private Long parentMessageId;
    private String content;
    private List<Long> attachmentIds = new ArrayList<>();
    private Instant timestamp;
    private Map<Long, Set<Long>> reactions = new HashMap<>();
    private Boolean isPinned = false;


    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }


    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Map<Long, Set<Long>> getReactions() {
        return reactions;
    }

    public void setReactions(Map<Long, Set<Long>> reactions) {
        this.reactions = reactions;
    }

    public void addReaction(Long emojiId, Long userId) {
        if (!reactions.containsKey(emojiId)) {
            reactions.put(emojiId, new HashSet<>());
        }
        reactions.get(emojiId).add(userId);
    }

    public void removeReaction(Long emojiId, Long userId) {
        if (reactions.containsKey(emojiId)) {
            reactions.get(emojiId).remove(userId);
        }
    }

    public List<Long> getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(List<Long> attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    public void addAttachmentId(Long attachmentId) {
        attachmentIds.add(attachmentId);
    }

    public void removeAttachmentId(Long attachmentId) {
        attachmentIds.remove(attachmentId);
    }

    public Long getParentMessageId() {
        return parentMessageId;
    }

    public void setParentMessageId(Long parentMessageId) {
        this.parentMessageId = parentMessageId;
    }

    public Boolean getPinned() {
        return isPinned;
    }

    public void setPinned(Boolean pinned) {
        isPinned = pinned;
    }
}
