package dev.rubric.discord.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "voice-channels")
public class VoiceChannel {
    @Id
    private Long voiceChannelId;
    private Long channelId;
    private Integer maxParticipants;
    private Instant createdAt;

    public Long getVoiceChannelId() {
        return voiceChannelId;
    }

    public void setVoiceChannelId(Long voiceChannelId) {
        this.voiceChannelId = voiceChannelId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }
    

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
