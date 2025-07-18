package com.simulation.bank.karafarin.core.exception_handling;

import com.simulation.bank.karafarin.core.entity.TransactionStatus;
import com.simulation.bank.karafarin.core.exception.NotEnoughBalanceException;
import com.simulation.bank.karafarin.core.exception.NotFoundAccountException;
import com.simulation.bank.karafarin.core.exception.NotMatchPasswordException;
import com.simulation.bank.karafarin.core.exception.TransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotMatchPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleNotMatchPasswordException(NotMatchPasswordException ex) {
        ErrorResponseEntity errorResponseEntity = ex.getErrorResponseEntity();
        errorResponseEntity.setErrorMessage("رمز عبور کارت اشتباه است");
        return errorResponseEntity;
    }
    @ExceptionHandler(NotEnoughBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleNotEnoughBalanceException(NotEnoughBalanceException ex) {
        ErrorResponseEntity errorResponseEntity = ex.getErrorResponseEntity();
        errorResponseEntity.setErrorMessage("موجودی حساب کافی نیست");
        return errorResponseEntity;
    }
    @ExceptionHandler(NotFoundAccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleNotFoundAccountException(NotFoundAccountException ex) {
        ErrorResponseEntity errorResponseEntity = ex.getErrorResponseEntity();
        errorResponseEntity.setErrorMessage("حساب متصل به شماره کارت وجود ندارد");
        return errorResponseEntity;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        FieldError error = ex.getFieldError();
        ErrorResponseEntity errorResponseEntity = new ErrorResponseEntity();
        errorResponseEntity
                .setErrorMessage(error.getDefaultMessage())
                .setTransactionDate(LocalDateTime.now())
                .setStatus(TransactionStatus.FAILED)
                .setTransactionId(UUID.randomUUID().toString());
        return errorResponseEntity;
    }
    @ExceptionHandler(TransactionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleTransactionException(TransactionException ex) {
        ErrorResponseEntity errorResponseEntity = ex.getErrorResponseEntity();
        errorResponseEntity.setErrorMessage("خطای ناشناخته تراکنش بانک");
        return errorResponseEntity;
    }
}
