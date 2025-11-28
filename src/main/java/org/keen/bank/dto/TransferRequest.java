package org.keen.bank.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferRequest {
    @NotBlank(message="From account is required")
    private String fromAccount;
    @NotBlank(message="To account is required")
    private String toAccount;
    @NotNull(message="Amount is required")
    @Positive(message="Amount must be positive")
    private Double amount;

    private String description;
    public TransferRequest() {
    }
    public TransferRequest(String fromAccount, String toAccount, Double amount, String description) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.description = description;
    }
    // Getters and Setters
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //Builder pattern
    public static class Builder{
        private String fromAccount;
        private String toAccount;
        private Double amount;
        private String description;
        public Builder fromAccount(String fromAccount) {
            this.fromAccount = fromAccount;
            return this;
        }

        public Builder toAccount(String toAccount) {
            this.toAccount = toAccount;
            return this;
        }

        public Builder amount(Double amount) {
            this.amount = amount;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public TransferRequest build() {
            return new TransferRequest(fromAccount, toAccount, amount, description);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}
