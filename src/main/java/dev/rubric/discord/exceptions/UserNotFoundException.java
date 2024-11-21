package dev.rubric.discord.exceptions;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException(String username) {
        super(String.format("User with username '%s' does not exist", username), "USER_NOT_FOUND");
    }

    public UserNotFoundException(Long userId) {
        super(String.format("User with userId '%d' does not exist", userId), "USER_NOT_FOUND");
    }
}

