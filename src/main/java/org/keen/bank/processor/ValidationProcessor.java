package org.keen.bank.processor;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Map;

@ApplicationScoped
public class ValidationProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String operation = exchange.getIn().getHeader("operation", String.class);
        switch (operation){
            case "validateTransfer":
                validateTransfer(exchange);
                break;
            case "validateAccount":
                validateAccount(exchange);
                break;
        }
    }
    private void validateTransfer(Exchange exchange){
        Map<String, Object> body = exchange.getIn().getBody(Map.class);
        String fromAccount =(String) body.get("fromAccount");
        String toAccount =(String) body.get("toAccount");
        Double amount =(Double) body.get("amount");

        if (fromAccount == null || toAccount == null || amount == null) {
            throw new IllegalArgumentException("From account, to account, and amount are required");
        }

        if (fromAccount.equals(toAccount)) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
    }
    private void validateAccount(Exchange exchange){
        String accountNumber = exchange.getIn().getHeader("accountNumber", String.class);

        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number is required");
        }
    }
}
