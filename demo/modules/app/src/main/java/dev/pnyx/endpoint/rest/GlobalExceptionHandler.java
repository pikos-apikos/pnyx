package dev.pnyx.endpoint.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

/**
 * Converts common application exceptions into HTTP responses.
 *
 * @see ../docs/80_Runtime/API_SPEC.md
 */
@Slf4j
@ControllerAdvice(basePackages = "dev.pnyx.endpoint.rest")
public class GlobalExceptionHandler {

    /**
     * Handles illegal argument exceptions as HTTP 400 Bad Request.
     *
     * @param ex the exception
     * @return 400 error response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Bad request: {}", ex.getMessage());
        return error(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Handles runtime exceptions as HTTP 500 Internal Server Error.
     *
     * @param ex the exception
     * @return 500 error response
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
        log.error("Internal error: {}", ex.getMessage(), ex);
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong. Please try again later.");
    }

    /**
     * Fallback handler for any unhandled exception.
     *
     * @param ex the exception
     * @return 500 error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
        log.error("Unhandled error: {}", ex.getMessage(), ex);
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
    }

    private ResponseEntity<ErrorResponse> error(HttpStatus status, String message) {
        return ResponseEntity.status(status)
            .body(new ErrorResponse(status.value(), message, Instant.now().toString()));
    }

    record ErrorResponse(int status, String error, String timestamp) { }
}
