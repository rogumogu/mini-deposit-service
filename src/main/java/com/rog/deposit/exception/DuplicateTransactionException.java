package com.rog.deposit.exception;

public class DuplicateTransactionException extends DepositServiceException {
    public DuplicateTransactionException(String message) {
        super(message);
    }
}
