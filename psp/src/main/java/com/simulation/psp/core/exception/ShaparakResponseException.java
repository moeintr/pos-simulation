package com.simulation.psp.core.exception;

public class ShaparakResponseException extends TransactionException{
    public ShaparakResponseException(Object errorResopneObject) {
        super(errorResopneObject);
    }
}
