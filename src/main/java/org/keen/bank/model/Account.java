package org.keen.bank.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "account_number", unique = true, nullable = false)
    public String accountNumber;

    @Column(name = "customer_name", nullable = false)
    public String customerName;

    public String email;
    public String phone;

    @Column(name = "account_type", nullable = false)
    public String accountType;

    public Double balance = 0.0;
    public String status = "ACTIVE";

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
