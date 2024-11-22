package dev.rubric.discord.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "pinned-messages")
public class PinnedMessage {
    @Id
    private Long pinnedMessageId;
    private Long messageId;
    private Long channelId;
    private Long pinnedBy;
    private Instant pinnedAt;

    public Long getPinnedMessageId() {
        return pinnedMessageId;
    }

    public void setPinnedMessageId(Long pinnedMessageId) {
        this.pinnedMessageId = pinnedMessageId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getPinnedBy() {
        return pinnedBy;
    }

    public void setPinnedBy(Long pinnedBy) {
        this.pinnedBy = pinnedBy;
    }

    public Instant getPinnedAt() {
        return pinnedAt;
    }

    public void setPinnedAt(Instant pinnedAt) {
        this.pinnedAt = pinnedAt;
    }
}
