package com.simulation.psp.core.exception_handling;

import com.simulation.psp.core.exception.NotFoundPosException;
import com.simulation.psp.core.exception.ShaparakResponseException;
import com.simulation.psp.core.model.TransactionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundPosException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleNotFoundPosException(NotFoundPosException ex) {
        return ex.getErrorResponseEntity().setErrorMessage("دستگاه پوز یافت نشد");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        FieldError error = ex.getFieldError();
        return new ErrorResponseEntity()
                .setErrorMessage(error.getDefaultMessage())
                .setStatus(TransactionStatus.FAILED)
                .setTransactionDate(LocalDateTime.now())
                .setTransactionId(UUID.randomUUID().toString());
    }

    @ExceptionHandler(ShaparakResponseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleShaparakResponseException(ShaparakResponseException ex) {
        return ex.getErrorResponseEntity();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return new ErrorResponseEntity().setErrorMessage(ex.getMessage());
    }
}
