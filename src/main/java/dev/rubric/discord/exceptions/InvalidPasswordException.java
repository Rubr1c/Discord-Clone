package dev.rubric.discord.exceptions;

public class InvalidPasswordException extends ApplicationException {
    public InvalidPasswordException(String reason) {
        super(String.format("Invalid password: %s", reason), "INVALID_PASSWORD");
    }
}

