package com.example.springboot.strategy;

import com.example.springboot.model.PaymentRequest;

/**
 * The Strategy Interface for the Strategy Design Pattern.
 * Requirement 7.1
 */
public interface PaymentStrategy {
    boolean validate(PaymentRequest request);
    String process(double amount);
    
    default String getMethodName() {
        return "GENERIC";
    }
}