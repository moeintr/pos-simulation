package com.simulation.bank.mellat.core.exception;

import com.simulation.bank.mellat.core.model.ResponseStructure;

public class NotFoundAccountException extends TransactionException{
    public NotFoundAccountException(ResponseStructure errorResopneObject) {
        super(errorResopneObject);
    }
}
