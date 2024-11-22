package dev.rubric.discord.exceptions;

public class InvalidDisplayNameException extends ApplicationException{
    public InvalidDisplayNameException(String displayName) {
        super(String.format("Invalid display name '%s'", displayName), "INVALID_NAME");
    }
}
