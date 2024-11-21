package dev.rubric.discord.exceptions;

public class InvalidEmailException extends ApplicationException {
    public InvalidEmailException(String email) {
        super(String.format("Invalid email: %s", email), "INVALID_EMAIL");
    }
}
