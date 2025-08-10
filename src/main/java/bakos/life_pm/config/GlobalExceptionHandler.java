package bakos.life_pm.config;

import bakos.life_pm.dto.response.ErrorResponse;
import bakos.life_pm.exception.BusinessLogicRtException;
import bakos.life_pm.exception.SystemConfigurationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    //TODO: check error codes and sync with FE logic.

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Exception occurred: {}, Request Details: {}", ex.getMessage(), request.getDescription(false), ex);
        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessLogicRtException.class)
    public ResponseEntity<ErrorResponse> handleBusinessLogicRtExceptions(BusinessLogicRtException ex, WebRequest request) {
        log.warn("Exception occurred: {}, Request Details: {}", ex.getMessage(), request.getDescription(false), ex);
        ErrorResponse errorResponse = new ErrorResponse(null, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        log.error("Invalid argument: {}, Request Details: {}", ex.getMessage(), request.getDescription(false), ex);
        return new ResponseEntity<>("Invalid argument", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, String>> handleMethodValidationException(HandlerMethodValidationException ex) {
        Map<String, String> errors = new HashMap<>();

        for (MessageSourceResolvable error : ex.getAllErrors()) {
            String fieldName = getFieldName(error);
            errors.put(fieldName, error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (MessageSourceResolvable error : ex.getAllErrors()) {
            String fieldName = getFieldName(error);
            errors.put(fieldName, error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    private String getFieldName(MessageSourceResolvable error) {
        if (error instanceof FieldError) {
            return ((FieldError) error).getField();
        } else if (error instanceof ObjectError) {
            return ((ObjectError) error).getObjectName();
        } else {
            return "error";
        }
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleValidationException(EntityNotFoundException ex, WebRequest request) {
        log.error("Exception occurred: {}, Request Details: {}", ex.getMessage(), request.getDescription(false), ex);
        ErrorResponse errorResponse = new ErrorResponse(null, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedOperationException(UnsupportedOperationException ex, WebRequest request) {
        log.error("Exception occurred: {}, Request Details: {}", "Unsupported operation (Ask BE to implement this method)", request.getDescription(false));
        ErrorResponse errorResponse = new ErrorResponse(null, "Unsupported operation (Ask BE to implement this method)");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SystemConfigurationException.class)
    public ResponseEntity<ErrorResponse> handleSystemConfigurationException(UnsupportedOperationException ex, WebRequest request) {
        log.warn("Exception occurred: {}, Request Details: {}", ex.getMessage(), request.getDescription(false));
        ErrorResponse errorResponse = new ErrorResponse(null, "Contact the administrator if the error persist.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }












}