package dev.rubric.discord.exceptions;

public class UserExistsException extends ApplicationException {
    public UserExistsException(String username) {
        super(String.format("User with username '%s' already exists", username), "USER_ALREADY_EXISTS");
    }

    public UserExistsException(Integer userId) {
        super(String.format("User with ID '%d' already exists", userId), "USER_ALREADY_EXISTS");
    }

    public UserExistsException(Integer userId, Integer friendId) {
        super(String.format("User '%d' is already friends with '%d'", userId, friendId), "FRIEND_ALREADY_EXISTS");
    }
}
