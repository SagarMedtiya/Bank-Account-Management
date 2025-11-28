package org.keen.bank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionListResponse {
    private Boolean success;
    private String message;
    private List<TransactionResponse> transactions;
    private Integer totalCount;
    // Constructors
    public TransactionListResponse() {}

    public TransactionListResponse(Boolean success, String message, List<TransactionResponse> transactions, Integer totalCount) {
        this.success = success;
        this.message = message;
        this.transactions = transactions;
        this.totalCount = totalCount;
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

    public List<TransactionResponse> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionResponse> transactions) {
        this.transactions = transactions;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
    // Static factory methods
    public static TransactionListResponse success(List<TransactionResponse> transactions) {
        return new TransactionListResponse(true, "Transactions retrieved successfully", transactions, transactions != null ? transactions.size(): 0);

    }
    public static TransactionListResponse error(String message){
        return new TransactionListResponse(false, message, null, 0);
    }
    //Builder pattern
    public static class Builder{
        private Boolean success;
        private String message;
        private List<TransactionResponse> transactions;
        private Integer totalCount;

        public Builder success(Boolean success){
            this.success = success;
            return this;
        }
        public Builder message(String message){
            this.message = message;
            return this;
        }
        public Builder transactions(List<TransactionResponse> transactions){
            this.transactions = transactions;
            return this;
        }
        public Builder totalCount(Integer totalCount){
            this.totalCount = totalCount;
            return this;
        }
        public TransactionListResponse build(){
            return new TransactionListResponse(success, message, transactions, totalCount);
        }
    }
    public static Builder builder(){
        return new Builder();
    }
}
