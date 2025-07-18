package com.simulation.bank.karafarin.usecase;

public interface UseCase<REQUEST, RESPONSE> {
    RESPONSE execute(REQUEST request);
}
