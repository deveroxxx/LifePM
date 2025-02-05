package bakos.life_pm.config;

import bakos.life_pm.dto.ErrorResponse;
import bakos.life_pm.exception.BusinessLogicRtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Exception occurred: {}, Request Details: {}", ex.getMessage(), request.getDescription(false), ex);
        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessLogicRtException.class)
    public ResponseEntity<ErrorResponse> handleBusinessLogicRtExceptions(BusinessLogicRtException ex, WebRequest request) {
        log.error("Exception occurred: {}, Request Details: {}", ex.getMessage(), request.getDescription(false), ex);
        ErrorResponse errorResponse = new ErrorResponse(null, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        log.error("Invalid argument: {}, Request Details: {}", ex.getMessage(), request.getDescription(false), ex);
        return new ResponseEntity<>("Invalid argument", HttpStatus.BAD_REQUEST);
    }
}