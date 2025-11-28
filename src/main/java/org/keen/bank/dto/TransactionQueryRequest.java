package org.keen.bank.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

public class TransactionQueryRequest {
    private String accountNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String transactionType;
    @Min(0)
    private Integer page;
    @Min(1)
    @Max(100)
    private Integer size;

    // Constructors
    public TransactionQueryRequest() {
        this.page = 0;
        this.size = 20;

    }

    public TransactionQueryRequest(String accountNumber, LocalDateTime startDate, LocalDateTime endDate,
                                   String transactionType, Integer page, Integer size) {
        this.accountNumber = accountNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.transactionType = transactionType;
        this.page = page != null ? page : 0;
        this.size = size != null ? size : 20;
    }
    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page != null ? page : 0;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size != null ? size : 20;
    }

    //Builder pattern
    public static class Builder{
        private String accountNumber;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String transactionType;
        private Integer page =0;
        private Integer size = 20;

        public Builder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }
        public Builder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder transactionType(String transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public Builder page(Integer page) {
            this.page = page;
            return this;
        }

        public Builder size(Integer size) {
            this.size = size;
            return this;
        }

        public TransactionQueryRequest build() {
            return new TransactionQueryRequest(accountNumber, startDate, endDate, transactionType, page, size);
        }
    }
    public static Builder builder(){
        return new Builder();
    }
}