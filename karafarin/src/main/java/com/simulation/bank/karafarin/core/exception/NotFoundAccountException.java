package com.simulation.bank.karafarin.core.exception;

import com.simulation.bank.karafarin.core.model.ResponseStructure;

public class NotFoundAccountException extends TransactionException{
    public NotFoundAccountException(ResponseStructure errorResponseObject) {
        super(errorResponseObject);
    }
}
