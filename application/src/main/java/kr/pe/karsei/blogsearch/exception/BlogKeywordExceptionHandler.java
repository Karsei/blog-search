package kr.pe.karsei.blogsearch.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BlogKeywordExceptionHandler {
    @ExceptionHandler(value = { BlogKeywordException.class })
    protected ResponseEntity<ErrorResponse> handleGlobalException(final BlogKeywordException e) {
        ErrorResponse response = ErrorResponse.builder()
                .error(e.getErrorCode().getHttpStatus().name())
                .status(e.getErrorCode().getHttpStatus().value())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(e.getErrorCode().getHttpStatus().value())
                .body(response);
    }
}
