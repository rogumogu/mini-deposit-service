package com.rog.deposit.exception;

import com.rog.deposit.config.ErrorCodeProperties;
import com.rog.deposit.config.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorCodeProperties errorCodeProperties;

    @ExceptionHandler(DepositNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDepositNotFoundException(DepositNotFoundException ex) {
        log.error("Deposit not found: {}", ex.getMessage());
        
        ErrorResponse errorResponse = errorCodeProperties.get("DepositNotFoundException").copy();
        if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
            errorResponse.setErrorReason(ex.getMessage());
        }
        
        return errorResponse.toResponseEntity();
    }

    @ExceptionHandler(DuplicateTransactionException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateTransactionException(DuplicateTransactionException ex) {
        log.error("Duplicate transaction: {}", ex.getMessage());
        
        ErrorResponse errorResponse = errorCodeProperties.get("DuplicateTransactionException").copy();
        if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
            errorResponse.setErrorReason(ex.getMessage());
        }
        
        return errorResponse.toResponseEntity();
    }

    @ExceptionHandler(InvalidChannelException.class)
    public ResponseEntity<ErrorResponse> handleInvalidChannelException(InvalidChannelException ex) {
        log.error("Invalid channel: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = errorCodeProperties.get("InvalidChannelException").copy();
        if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
            errorResponse.setReasonParameters(ex.getMessage());
        }
        
        return errorResponse.toResponseEntity();
    }

    @ExceptionHandler(DepositProcessingException.class)
    public ResponseEntity<ErrorResponse> handleDepositProcessingException(DepositProcessingException ex) {
        log.error("Deposit processing error: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = errorCodeProperties.get("DepositProcessingException").copy();
        if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
            errorResponse.setErrorReason(ex.getMessage());
        }
        
        return errorResponse.toResponseEntity();
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        log.error("Missing required header: {}", ex.getMessage());
        
        ErrorResponse errorResponse = errorCodeProperties.get("MissingRequestHeaderException").copy();
        errorResponse.setReasonParameters(ex.getHeaderName());
        
        return errorResponse.toResponseEntity();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Invalid argument: {}", ex.getMessage());

        ErrorResponse errorResponse = errorCodeProperties.get("IllegalArgumentException").copy();
        if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
            errorResponse.setErrorReason(ex.getMessage());
        }
        
        return errorResponse.toResponseEntity();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = errorCodeProperties.get("Exception").copy();
        
        return errorResponse.toResponseEntity();
    }
}
