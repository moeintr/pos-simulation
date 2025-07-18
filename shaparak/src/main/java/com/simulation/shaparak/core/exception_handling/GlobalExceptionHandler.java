package com.simulation.shaparak.core.exception_handling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simulation.shaparak.core.exception.NotFoundBankException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ObjectMapper objectMapper;

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseEntity handleHttpClientErrorException(HttpClientErrorException ex) throws JsonProcessingException {
        return objectMapper.readValue(ex.getResponseBodyAsString(), ErrorResponseEntity.class);
    }
    @ExceptionHandler(HttpServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseEntity handleHttpServerErrorException(HttpServerErrorException ex) throws JsonProcessingException {
        return objectMapper.readValue(ex.getResponseBodyAsString(), ErrorResponseEntity.class);
    }
}
