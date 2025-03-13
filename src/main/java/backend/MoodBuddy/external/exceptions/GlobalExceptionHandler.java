package backend.MoodBuddy.external.exceptions;

import backend.MoodBuddy.application.controller.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global Exception Handler to handle all exceptions and provide custom error responses
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final BaseController baseController = new BaseController();

    /**
     * Handle validation error responses (e.g., @Valid/@NotNull violations)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Extract the first validation error message (you could customize this to combine multiple errors if needed)
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", ")); // Concatenate messages if there are multiple

        // Response header content with the error message directly included in "desc"
        return baseController.getResponseEntity("400", Map.of(
                "responseHeader", Map.of(
                        "timestamp", LocalDateTime.now().toString(),
                        "code", "400", // HTTP BAD REQUEST code
                        "desc", errorMessage
                )
        ));
    }

    /**
     * Handle custom DomainException (application-specific errors)
     */
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> handleDomainException(DomainException ex) {
        Map<String, Object> responseHeader = Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "code", ex.getMessage(),
                "desc", ex.getCode()
        );

        return baseController.getResponseEntity("400", Map.of("responseHeader", responseHeader));
    }

    /**
     * General handler for all other exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        Map<String, Object> responseHeader = Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "code", "500",
                "desc", ex.getMessage()
        );

        return baseController.getResponseEntity("500", Map.of("responseHeader", responseHeader));
    }
}