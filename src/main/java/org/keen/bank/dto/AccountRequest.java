package org.keen.bank.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountRequest {

    @NotBlank(message="Account number is required")
    @JsonProperty("accountNumber")
    public  String accountNumber;

    @NotBlank(message="Customer name is required")
    @JsonProperty("customerName")
    public String customerName;
    @JsonProperty("email")
    public String email;
    @JsonProperty("phone")
    public String phone;
    @NotBlank(message="Account type is required")
    @JsonProperty("accountType")
    public String accountType;
    @PositiveOrZero(message="Initial balance cannot be negative")
    @JsonProperty("initialBalance")
    public Double initialBalance = 0.0;

    public AccountRequest() {}
    public AccountRequest(String accountNumber, String customerName, String email,
                          String phone, String accountType, Double initialBalance) {
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.email = email;
        this.phone = phone;
        this.accountType = accountType;
        this.initialBalance = initialBalance != null ? initialBalance : 0.0;
    }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public Double getInitialBalance() { return initialBalance; }
    public void setInitialBalance(Double initialBalance) { this.initialBalance = initialBalance != null ? initialBalance : 0.0; }

    public static class Builder{
        private String accountNumber;
        private String customerName;
        private String email;
        private String phone;
        private String accountType;
        private Double initialBalance = 0.0;
        public Builder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder accountType(String accountType) {
            this.accountType = accountType;
            return this;
        }

        public Builder initialBalance(Double initialBalance) {
            this.initialBalance = initialBalance;
            return this;
        }
        public AccountRequest build() {
            return new AccountRequest(accountNumber, customerName, email, phone, accountType, initialBalance);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
