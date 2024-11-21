package dev.rubric.discord.service;

import dev.rubric.discord.collections.User;
import dev.rubric.discord.exceptions.*;
import dev.rubric.discord.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final SecureRandom RANDOM = new SecureRandom();

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Long getUserIdByUsername(String username) {
        logger.info("Fetching user ID for username: {}", username);

        return userRepository.getUserByUsername(username)
                .map(User::getUserId)
                .orElseThrow(() -> {
                    logger.error("User not found with username: {}", username);
                    return new UserNotFoundException(username);
                });
    }

    public String getUsernameByUserId(Long userId) {
        logger.info("Fetching username for user ID: {}", userId);

        return userRepository.getUserByUserId(userId)
                .map(User::getUsername)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", userId);
                    return new UserNotFoundException(userId);
                });
    }

    public String getDisplayNameByUsername(String username) {
        logger.info("Fetching display name for username: {}", username);

        return userRepository.getUserByUsername(username)
                .map(User::getDisplayName)
                .orElseThrow(() -> {
                    logger.error("User not found with username: {}", username);
                    return new UserNotFoundException(username);
                });
    }

    public String getDisplayNameByUserId(Long userId) {
        logger.info("Fetching display name for user ID: {}", userId);

        return userRepository.getUserByUserId(userId)
                .map(User::getDisplayName)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", userId);
                    return new UserNotFoundException(userId);
                });
    }

    private Long generateRandomUserId() {
        logger.debug("Generating random user ID");
        long min = 100_000_000_000_000_000L;
        long max = 999_999_999_999_999_999L;
        return min + (long) (Math.random() * (max - min + 1));
    }

    public Long generateUniqueUserId() {
        logger.info("Generating unique user ID");
        Long newUserId;
        do {
            newUserId = generateRandomUserId();
        } while (userRepository.getUserByUserId(newUserId).isPresent());
        logger.debug("Generated unique user ID: {}", newUserId);
        return newUserId;
    }

    public Boolean isValidPassword(String password) {
        logger.debug("Validating password");
        return password.length() >= 8 &&
                password.chars().anyMatch(Character::isUpperCase) &&
                password.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));
    }

    public boolean isValidEmail(String email) {
        logger.debug("Validating email: {}", email);
        return email != null && !email.isEmpty() && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    public boolean isValidUsername(String username) {
        return username.length() <= 25 &&
                username.chars().noneMatch(Character::isWhitespace);
    }

    public Long createUser(String displayName, String email, String username, String password) {
        logger.info("Creating user with username: {}", username);

        userRepository.getUserByUsername(username).ifPresent(uname -> {
            logger.error("User with username {} already exists", username);
            throw new UserExistsException(username);
        });

        if (!isValidPassword(password)) {
            logger.warn("Password validation failed for username: {}", username);
            throw new InvalidPasswordException("Password does not meet security requirements");
        }

        if (!isValidEmail(email)) {
            logger.warn("Email validation failed for email: {}", email);
            throw new InvalidEmailException(email);
        }

        if (!isValidUsername(username)) {
            logger.warn("Display Name validation failed for username: {}", username);
            throw new InvalidDisplayNameException(displayName);
        }

        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(generateUniqueUserId(), email, username, displayName, hashedPassword);
        userRepository.save(user);

        logger.info("User created successfully with ID: {}", user.getUserId());
        return user.getUserId();
    }

    public Long authenticateUser(String username, String password) {
        logger.info("Authenticating user with username: {}", username);

        return userRepository.getUserByUsername(username)
                .filter(user -> {
                    boolean isMatch = passwordEncoder.matches(password, user.getPassword());
                    if (!isMatch) {
                        logger.warn("Password mismatch for username: {}", username);
                    }
                    return isMatch;
                })
                .map(User::getUserId)
                .orElseThrow(() -> {
                    if (userRepository.getUserByUsername(username).isEmpty()) {
                        logger.error("User not found with username: {}", username);
                        return new UserNotFoundException(username);
                    } else {
                        logger.error("Incorrect password for username: {}", username);
                        return new InvalidPasswordException("Incorrect password");
                    }
                });
    }

    public void changePassword(Long userId, String oldPassword, String newPassword) {
        logger.info("Changing password for user ID: {}", userId);

        User user = userRepository.getUserByUserId(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", userId);
                    return new UserNotFoundException(userId);
                });

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            logger.warn("Old password does not match for user ID: {}", userId);
            throw new InvalidPasswordException("Old password does not match");
        }

        if (!isValidPassword(newPassword)) {
            logger.warn("New password validation failed for user ID: {}", userId);
            throw new InvalidPasswordException("New password does not meet security requirements");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        logger.info("Password successfully changed for user ID: {}", userId);
    }

    public void updateUser(Long userId, String email, String displayName) {
        logger.info("Updating user with ID: {}", userId);

        User user = userRepository.getUserByUserId(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", userId);
                    return new UserNotFoundException(userId);
                });

        if (email != null && !isValidEmail(email)) {
            logger.warn("Email validation failed for email: {}", email);
            throw new InvalidEmailException(email);
        }

        if (email != null) user.setEmail(email);
        if (displayName != null) user.setDisplayName(displayName);

        userRepository.save(user);
        logger.info("User updated successfully with ID: {}", userId);
    }

    public void deactivateUser(Long userId) {
        logger.info("Deactivating user with ID: {}", userId);

        User user = userRepository.getUserByUserId(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", userId);
                    return new UserNotFoundException(userId);
                });

        user.setActive(false);
        userRepository.save(user);

        logger.info("User deactivated successfully with ID: {}", userId);
    }

    public boolean userExists(Long userId) {
        logger.debug("Checking if user exists with ID: {}", userId);
        return userRepository.getUserByUserId(userId).isPresent();
    }

    public void deleteUser(Long userId) {
        logger.info("Deleting user with ID: {}", userId);

        User user = userRepository.getUserByUserId(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", userId);
                    return new UserNotFoundException(userId);
                });

        userRepository.delete(user);
        logger.info("User deleted successfully with ID: {}", userId);
    }

    public User getUserDetails(Long userId) {
        logger.info("Fetching details for user ID: {}", userId);

        return userRepository.getUserByUserId(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", userId);
                    return new UserNotFoundException(userId);
                });
    }

    public User getUserDetails(String username) {
        logger.info("Fetching details for username: {}", username);

        return userRepository.getUserByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found with username: {}", username);
                    return new UserNotFoundException(username);
                });
    }

    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    public void removeFriend(Long userId, Long friendId) {
        logger.info("Removing friend with ID: {} for user ID: {}", friendId, userId);

        User[] users = getUserAndFriend(userId, friendId);
        User user = users[0];
        User friend = users[1];

        if (!user.removeFriend(friendId)) {
            logger.warn("Friend with ID: {} not found for user ID: {}", friendId, userId);
            throw new UserNotFoundException(friendId);
        }
        if (!friend.removeFriend(userId)) {
            logger.warn("User ID: {} not found for friend with ID: {}", friendId, userId);
            throw new UserNotFoundException(friendId);
        }

        userRepository.save(user);
        logger.info("Friend with ID: {} removed successfully for user ID: {}", friendId, userId);
    }

    public List<Long> getMutualFriends(Long userId, Long friendId) {
        logger.info("Fetching mutual friends between user ID: {} and user ID: {}", userId, friendId);

        User[] users = getUserAndFriend(userId, friendId);
        User user = users[0];
        User friend = users[1];

        List<Long> mutualFriends = user.getFriends().stream()
                .filter(friend.getFriends()::contains)
                .collect(Collectors.toList());

        logger.info("Found {} mutual friends between user ID: {} and user ID: {}", mutualFriends.size(), userId, friendId);
        return mutualFriends;
    }

    public List<Long> getFriends(Long userId) {
        logger.info("Fetching friends for user ID: {}", userId);

        User user = userRepository.getUserByUserId(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", userId);
                    return new UserNotFoundException(userId);
                });

        List<Long> friends = List.copyOf(user.getFriends());
        logger.info("Found {} friends for user ID: {}", friends.size(), userId);
        return friends;
    }

    public User[] getUserAndFriend(Long userId, Long friendId) {
        logger.info("Fetching user ID: {} and friend ID: {}", userId, friendId);

        User user = userRepository.getUserByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        User friend = userRepository.getUserByUserId(friendId)
                .orElseThrow(() -> new UserNotFoundException(friendId));

        logger.info("Fetched user ID: {} and friend ID: {}", userId, friendId);
        return new User[]{user, friend};
    }

    public void sendFriendRequest(Long userId, Long friendId) {
        logger.info("User ID: {} is sending a friend request to user ID: {}", userId, friendId);

        User[] users = getUserAndFriend(userId, friendId);
        User user = users[0];
        User friend = users[1];

        if (user.getSentFriendRequests().contains(friendId) || friend.getReceivedFriendRequests().contains(userId)) {
            logger.warn("Friend request already exists between user ID: {} and user ID: {}", userId, friendId);
            throw new UserExistsException(userId, friendId);
        }

        user.addSentFriendRequest(friendId);
        friend.addReceivedFriendRequest(userId);

        userRepository.save(user);
        userRepository.save(friend);

        logger.info("Friend request successfully sent from user ID: {} to user ID: {}", userId, friendId);
    }

    public void acceptFriendRequest(Long userId, Long friendId) {
        logger.info("User ID: {} is accepting a friend request from user ID: {}", userId, friendId);

        User[] users = getUserAndFriend(userId, friendId);
        User user = users[0];
        User friend = users[1];

        if (!user.getReceivedFriendRequests().contains(friendId)) {
            logger.warn("No friend request from user ID: {} to user ID: {}", friendId, userId);
            throw new UserNotFoundException("No friend request from user " + friendId);
        }

        user.addFriend(friendId);
        friend.addFriend(userId);

        user.removeReceivedFriendRequest(friendId);
        friend.removeSentFriendRequest(userId);

        userRepository.save(user);
        userRepository.save(friend);

        logger.info("Friend request successfully accepted between user ID: {} and user ID: {}", userId, friendId);
    }

    public void rejectFriendRequest(Long userId, Long friendId) {
        logger.info("User ID: {} is rejecting a friend request from user ID: {}", userId, friendId);

        User[] users = getUserAndFriend(userId, friendId);
        User user = users[0];
        User friend = users[1];

        if (!user.getReceivedFriendRequests().contains(friendId)) {
            logger.warn("No friend request from user ID: {} to user ID: {}", friendId, userId);
            throw new UserNotFoundException("No friend request from user " + friendId);
        }

        user.removeReceivedFriendRequest(friendId);
        friend.removeSentFriendRequest(userId);

        userRepository.save(user);
        userRepository.save(friend);

        logger.info("Friend request successfully rejected between user ID: {} and user ID: {}", userId, friendId);
    }
}
