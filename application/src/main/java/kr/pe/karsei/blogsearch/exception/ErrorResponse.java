package kr.pe.karsei.blogsearch.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String message;

    @Builder
    public ErrorResponse(final int status,
                         final String error,
                         final String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
