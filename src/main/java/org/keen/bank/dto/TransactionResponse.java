package org.keen.bank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {
    private Long id;
    private String transactionId;
    private String fromAccount;
    private String toAccount;
    private String transactionType;
    private BigDecimal amount;
    private String description;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transactionDate;

    private String status;
    private BigDecimal balanceAfter;

    // Constructors
    public TransactionResponse() {}

    public TransactionResponse(Long id, String transactionId, String fromAccount, String toAccount,
                               String transactionType, BigDecimal amount, String description,
                               LocalDateTime transactionDate, String status, BigDecimal balanceAfter) {
        this.id = id;
        this.transactionId = transactionId;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.status = status;
        this.balanceAfter = balanceAfter;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
    // Builder pattern
    public static class Builder{
        private Long id;
        private String transactionId;
        private String fromAccount;
        private String toAccount;
        private String transactionType;
        private BigDecimal amount;
        private String description;
        private LocalDateTime transactionDate;
        private String status;
        private BigDecimal balanceAfter;
        public Builder id(Long id){
            this.id = id;
            return this;
        }
        public Builder transactionId(String transactionId){
            this.transactionId = transactionId;
            return this;
        }
        public Builder fromAccount(String fromAccount){
            this.fromAccount = fromAccount;
            return this;
        }
        public Builder toAccount(String toAccount){
            this.toAccount = toAccount;
            return this;
        }
        public Builder transactionType(String transactionType){
            this.transactionType = transactionType;
            return this;
        }
        public Builder amount(BigDecimal amount){
            this.amount = amount;
            return this;
        }
        public Builder description(String description){
            this.description = description;
            return this;
        }
        public Builder transactionDate(LocalDateTime transactionDate){
            this.transactionDate = transactionDate;
            return this;
        }
        public Builder status(String status){
            this.status = status;
            return this;
        }
        public Builder balanceAfter(BigDecimal balanceAfter){
            this.balanceAfter = balanceAfter;
            return this;
        }
        public TransactionResponse build(){
            return new TransactionResponse(id, transactionId, fromAccount, toAccount, transactionType, amount, description, transactionDate, status, balanceAfter);
        }
    }
    public static Builder builder(){
        return new Builder();
    }
    //Utility methods
    public static TransactionResponse fromDeposit(String transactionId, String accountNumber, BigDecimal amount, BigDecimal newBalance){
        return builder()
                .transactionId(transactionId)
                .fromAccount(accountNumber)
                .transactionType("DEPOSIT")
                .amount(amount)
                .description("Cash Deposit")
                .transactionDate(LocalDateTime.now())
                .status("COMPLETED")
                .balanceAfter(newBalance)
                .build();
    }
    public static TransactionResponse fromWithdrawal(String transactionId, String accountNumber, BigDecimal amount, BigDecimal newBalance){
        return builder()
                .transactionId(transactionId)
                .fromAccount(accountNumber)
                .transactionType("WITHDRAWAL")
                .amount(amount)
                .description("Cash Withdrawal")
                .transactionDate(LocalDateTime.now())
                .status("COMPLETED")
                .balanceAfter(newBalance)
                .build();
    }
    public static TransactionResponse fromTransfer(String transactionId, String fromAccount, String toAccount, BigDecimal amount, String description, BigDecimal fromAccountBalance, BigDecimal newBalance){
        return builder()
                .transactionId(transactionId)
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .transactionType("TRANSFER")
                .amount(amount)
                .description(description != null ? description : "Fund Transfer")
                .transactionDate(LocalDateTime.now())
                .status("COMPLETED")
                .balanceAfter(fromAccountBalance)
                .build();
    }
    @Override
    public String toString(){
        return "TransactionResponse{"+
                "id=" + id +
                ", transactionId='" + transactionId + '\''+
                ", fromAccount='" + fromAccount + '\'' +
                ", toAccount='" + toAccount + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", transactionDate=" + transactionDate +
                ", status='" + status + '\'' +
                ", balanceAfter=" + balanceAfter +
                '}';
    }
}

























