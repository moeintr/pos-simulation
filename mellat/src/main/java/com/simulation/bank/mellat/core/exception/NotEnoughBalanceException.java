package com.simulation.bank.mellat.core.exception;

import com.simulation.bank.mellat.core.model.ResponseStructure;

public class NotEnoughBalanceException extends TransactionException{
    public NotEnoughBalanceException(ResponseStructure errorResopneObject) {
        super(errorResopneObject);
    }
}
