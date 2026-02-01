package com.rog.deposit.exception;

public class DepositServiceException extends RuntimeException {
    public DepositServiceException(String message) {
        super(message);
    }

    public DepositServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
