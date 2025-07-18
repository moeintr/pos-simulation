package com.simulation.shaparak.core.exception;

public class NotFoundBankException extends TransactionException{
    public NotFoundBankException(Object errorResopneObject) {
        super(errorResopneObject);
    }
}
