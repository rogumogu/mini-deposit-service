package com.rog.deposit.exception;

public class DepositProcessingException extends DepositServiceException {
    public DepositProcessingException(String message) {
        super(message);
    }

    public DepositProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
