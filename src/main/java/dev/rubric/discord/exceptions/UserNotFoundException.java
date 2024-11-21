package dev.rubric.discord.exceptions;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException(String username) {
        super(String.format("User with username '%s' does not exist", username), "USER_NOT_FOUND");
    }

    public UserNotFoundException(Integer userId) {
        super(String.format("User with ID '%d' does not exist", userId), "USER_NOT_FOUND");
    }
}

