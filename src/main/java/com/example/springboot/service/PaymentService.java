package com.example.springboot.service;

import com.example.springboot.factory.PaymentStrategyFactory;
import com.example.springboot.model.PaymentRequest;
import com.example.springboot.observer.PaymentObserver;
import com.example.springboot.strategy.PaymentStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentStrategyFactory strategyFactory;
    private final List<PaymentObserver> observers = new ArrayList<>();

    // Spring Autowires this list automatically
    public PaymentService(PaymentStrategyFactory strategyFactory, List<PaymentObserver> observerList) {
        this.strategyFactory = strategyFactory;
        this.observers.addAll(observerList);
    }

    public String executePayment(Long clientId, String method, PaymentRequest request, double amount) {
        // 1. Get strategy from Factory (Factory Pattern)
        PaymentStrategy strategy = strategyFactory.getStrategy(method);
        
        // 2. Validate (Strategy Pattern - Req 5.1.1)
        if (strategy.validate(request)) {
            try {
                // 3. Simulation Delay (Req 5.1.2)
                System.out.println("Processing payment via " + method + "... please wait.");
                Thread.sleep(3000); 
                
                // 4. Generate ID (Req 5.1.2)
                String txnId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                
                // 5. Logic Execution
                strategy.process(amount);
                
                // 6. Notify all subscribers (Observer Pattern)
                for (PaymentObserver observer : observers) {
                    observer.onPaymentSuccess(clientId, txnId, amount);
                }
                
                return txnId;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "ERROR";
            }
        }
        return "VALIDATION_FAILED";
    }
}