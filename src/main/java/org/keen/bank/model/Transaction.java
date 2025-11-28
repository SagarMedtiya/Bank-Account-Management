package org.keen.bank.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "transaction_id", unique = true, nullable = false)
    public String transactionId;

    @Column(name = "from_account", nullable = false)
    public String fromAccount;

    @Column(name = "to_account")
    public String toAccount;

    @Column(name = "transaction_type", nullable = false)
    public String transactionType;

    @Column(nullable = false)
    public Double amount;

    public String description;

    @Column(name = "transaction_date")
    public LocalDateTime transactionDate;

    public String status = "COMPLETED";

    @Column(name = "balance_after")
    public Double balanceAfter;

    @PrePersist
    protected void onCreate() {
        transactionDate = LocalDateTime.now();
    }
}
