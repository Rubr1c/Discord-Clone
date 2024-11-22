package dev.rubric.discord.handlers;

import dev.rubric.discord.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", getStatus(ex.getErrorCode()).value());
        body.put("error", ex.getErrorCode());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, getStatus(ex.getErrorCode()));
    }

    private HttpStatus getStatus(String errorCode) {
        return switch (errorCode) {
            case "USER_NOT_FOUND" -> HttpStatus.NOT_FOUND;
            case "INVALID_EMAIL", "INVALID_PASSWORD", "INVALID_NAME" -> HttpStatus.BAD_REQUEST;
            case "USER_ALREADY_EXISTS", "FRIEND_ALREADY_EXISTS" -> HttpStatus.CONFLICT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
