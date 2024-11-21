package dev.rubric.discord.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "users")
public class User {
    @Id
    private Long userId;

    @Indexed(unique = true)
    private String username;

    private String email;
    private String displayName;
    private String password;
    private Boolean isActive = true;
    private Set<Long> friends = new HashSet<>();
    private Set<Long> sentFriendRequests = new HashSet<>();
    private Set<Long> receivedFriendRequests = new HashSet<>();

    public User(Long userId,
                String email,
                String username,
                String displayName,
                String password) {

        this.email = email;
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.password = password;
    }

    public User() {}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Set<Long> getFriends() {
        return friends;
    }

    public void setFriends(HashSet<Long> friends) {
        this.friends = friends;
    }

    public boolean addFriend(Long friendId) {
        return friends.add(friendId);
    }

    public boolean removeFriend(Long friendId) {
        return friends.remove(friendId);
    }

    public Set<Long> getSentFriendRequests() {
        return sentFriendRequests;
    }

    public boolean addSentFriendRequest(Long friendId) {
        return sentFriendRequests.add(friendId);
    }

    public boolean removeSentFriendRequest(Long friendId) {
        return sentFriendRequests.remove(friendId);
    }

    public Set<Long> getReceivedFriendRequests() {
        return receivedFriendRequests;
    }

    public boolean addReceivedFriendRequest(Long friendId) {
        return receivedFriendRequests.add(friendId);
    }

    public boolean removeReceivedFriendRequest(Long friendId) {
        return receivedFriendRequests.remove(friendId);
    }
}
