package org.keen.bank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse {
    private Boolean success;
    private String message;
    private String errorCode;
    private Map<String, Object> account;
    private String accountNumber;
    private Double newBalance;
    private String transactionId;

    // Constructors
    public AccountResponse() {}

    public AccountResponse(Boolean success, String message, String errorCode,
                           Map<String, Object> account, String accountNumber,
                           Double newBalance, String transactionId) {
        this.success = success;
        this.message = message;
        this.errorCode = errorCode;
        this.account = account;
        this.accountNumber = accountNumber;
        this.newBalance = newBalance;
        this.transactionId = transactionId;
    }
    // Getters and Setters
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Map<String, Object> getAccount() {
        return account;
    }

    public void setAccount(Map<String, Object> account) {
        this.account = account;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(Double newBalance) {
        this.newBalance = newBalance;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    //static factory methods
    public static AccountResponse success(String message){
        AccountResponse response = new AccountResponse();
        response.success = true;
        response.message = message;
        return response;
    }
    public static AccountResponse error(String message){
        AccountResponse response = new AccountResponse();
        response.success = false;
        response.message = message;
        return response;
    }
    public static AccountResponse error(String message, String errorCode){
        AccountResponse response = error(message);
        response.errorCode = errorCode;
        return response;
    }
    //Builder pattern
    public static class Builder {
        private Boolean success;
        private String message;
        private String errorCode;
        private Map<String, Object> account;
        private String accountNumber;
        private Double newBalance;
        private String transactionId;

        public Builder success(Boolean success) {
            this.success = success;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder account(Map<String, Object> account) {
            this.account = account;
            return this;
        }

        public Builder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder newBalance(Double newBalance) {
            this.newBalance = newBalance;
            return this;
        }

        public Builder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public AccountResponse build() {
            return new AccountResponse(success, message, errorCode, account, accountNumber, newBalance, transactionId);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
