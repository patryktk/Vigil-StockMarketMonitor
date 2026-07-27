package pl.tkaczyk.sheetsservice.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.dto.ErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    @Value("${spring.application.name}")
    private String SERVICE_NAME;

    @ExceptionHandler(GoogleSheetsIntegrationException.class)
    public ResponseEntity<ErrorResponse> handleGoogleSheetsIntegrationException(
            GoogleSheetsIntegrationException e, WebRequest request) {

        return buildErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException e, WebRequest request) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> errors = new ArrayList<>();
        fieldErrors.forEach(fieldError -> {
            errors.add(fieldError.getField() + " " + fieldError.getDefaultMessage());
        });

        return buildErrorResponse(e, HttpStatus.valueOf(e.getStatusCode().value()), request, errors);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception e,
                                                             HttpStatus status,
                                                             WebRequest request) {
        return buildErrorResponse(e, status, request, Collections.emptyList());
    }


    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception e,
                                                             HttpStatus status,
                                                             WebRequest request, List<String> messages) {

        ErrorResponse errorResponse = new ErrorResponse(
                Instant.now(),
                e.getMessage(),
                e.getClass().getSimpleName(),
                status.value(),
                SERVICE_NAME,
                request.getDescription(false),
                messages
        );
        String logMessage = String.format("""
                        ===========================================
                        Exception occurred %s
                         type: %s
                         message: %s
                         path: %s
                         ===========================================
                        """,
                LocalDateTime.now(),
                errorResponse.getErrorType(),
                errorResponse.getMessage(),
                errorResponse.getPath()
        );

        if (status.is4xxClientError()) {
            log.warn(logMessage);
        } else {
            log.error(logMessage, e);
        }

        return new ResponseEntity<>(errorResponse, status);
    }
}
