package dev.rubric.discord.exceptions;

public class InvalidUsernameException extends ApplicationException{
    public InvalidUsernameException(String username) {
        super(String.format("Invalid display name '%s'", username), "INVALID_NAME");
    }
}
