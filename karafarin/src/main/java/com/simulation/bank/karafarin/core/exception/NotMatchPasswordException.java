package com.simulation.bank.karafarin.core.exception;

import com.simulation.bank.karafarin.core.model.ResponseStructure;

public class NotMatchPasswordException extends TransactionException{
    public NotMatchPasswordException(ResponseStructure errorResponseObject) {
        super(errorResponseObject);
    }
}
