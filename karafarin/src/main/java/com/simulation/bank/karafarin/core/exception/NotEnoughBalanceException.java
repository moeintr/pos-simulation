package com.simulation.bank.karafarin.core.exception;

import com.simulation.bank.karafarin.core.model.ResponseStructure;

public class NotEnoughBalanceException extends TransactionException{
    public NotEnoughBalanceException(ResponseStructure errorResponseObject) {
        super(errorResponseObject);
    }
}
