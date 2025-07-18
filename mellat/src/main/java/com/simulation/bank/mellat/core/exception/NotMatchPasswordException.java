package com.simulation.bank.mellat.core.exception;

import com.simulation.bank.mellat.core.model.ResponseStructure;

public class NotMatchPasswordException extends TransactionException{
    public NotMatchPasswordException(ResponseStructure errorResponseObject) {
        super(errorResponseObject);
    }
}
