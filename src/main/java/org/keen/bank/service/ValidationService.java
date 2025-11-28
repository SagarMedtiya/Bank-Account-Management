package org.keen.bank.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.keen.bank.dto.AccountRequest;
import org.keen.bank.dto.TransferRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ValidationService {
    private static final Logger log = LoggerFactory.getLogger(ValidationService.class);
    public void validateAccountCreation(Exchange exchange) {
        AccountRequest request = exchange.getIn().getBody(AccountRequest.class);

        if (request.getAccountNumber() == null || request.getAccountNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Account number is required");
        }
        if (request.getCustomerName() == null || request.getCustomerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name is required");
        }
        if (request.getAccountType() == null || request.getAccountType().trim().isEmpty()) {
            throw new IllegalArgumentException("Account type is required");
        }

        log.info("Account creation validation passed for: {}", request.getAccountNumber());
    }

    public void validateTransferRequest(Exchange exchange) {
        TransferRequest request = exchange.getIn().getBody(TransferRequest.class);

        if (request.getFromAccount() == null || request.getFromAccount().trim().isEmpty()) {
            throw new IllegalArgumentException("From account is required");
        }
        if (request.getToAccount() == null || request.getToAccount().trim().isEmpty()) {
            throw new IllegalArgumentException("To account is required");
        }
        if (request.getAmount() == null) {
            throw new IllegalArgumentException("Amount is required");
        }
        if (request.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (request.getFromAccount().equals(request.getToAccount())) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        log.info("Transfer validation passed: {} -> {}", request.getFromAccount(), request.getToAccount());
    }

    public void validateTransactionAmount(Exchange exchange) {
        Double amount = exchange.getIn().getBody(Double.class);

        if (amount == null) {
            throw new IllegalArgumentException("Amount is required");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
}
