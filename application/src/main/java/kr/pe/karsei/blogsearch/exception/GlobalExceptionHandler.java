package kr.pe.karsei.blogsearch.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.MessageFormat;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex,
                                                                          final HttpHeaders headers,
                                                                          final HttpStatusCode status,
                                                                          final WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .error(HttpStatus.valueOf(status.value()).name())
                .status(status.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(status.value())
                .body(response);
    }

    @ExceptionHandler(value = { ConstraintViolationException.class })
    protected ResponseEntity<ErrorResponse> handleConstraintViolationException(final ConstraintViolationException e,
                                                                               final WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse response = ErrorResponse.builder()
                .error(httpStatus.name())
                .status(httpStatus.value())
                .message(e.getConstraintViolations()
                        .stream()
                        .findFirst()
                        .map(o -> MessageFormat.format("{0} - {1}", o.getPropertyPath(), o.getMessage()))
                        .orElse("Validation Error")
                )
                .build();
        return ResponseEntity.status(httpStatus.value())
                .body(response);
    }
}