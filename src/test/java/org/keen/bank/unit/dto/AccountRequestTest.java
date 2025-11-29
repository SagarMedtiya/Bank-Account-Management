package org.keen.bank.unit.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.keen.bank.dto.AccountRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class  AccountRequestTest {
    @Test
    @DisplayName("Should build AccountRequest using builder pattern")
    void testAccountRequestBuilder() {
        AccountRequest request = AccountRequest.builder()
                .accountNumber("ACC123")
                .customerName("Sagar Medtiya")
                .email("sagar@email.com")
                .phone("1234567890")
                .accountType("SAVINGS")
                .initialBalance(1000.0)
                .build();

        assertEquals("ACC123", request.getAccountNumber());
        assertEquals("Sagar Medtiya", request.getCustomerName());
        assertEquals("sagar@email.com", request.getEmail());
        assertEquals("1234567890", request.getPhone());
        assertEquals("SAVINGS", request.getAccountType());
        assertEquals(1000.0, request.getInitialBalance());
    }
    @Test
    @DisplayName("Should set default initial balance when null")
    void testAccountRequestDefaultInitialBalance() {
        AccountRequest request = new AccountRequest();
        request.setInitialBalance(null);

        assertEquals(0.0, request.getInitialBalance());
    }

    @Test
    @DisplayName("Should handle null initial balance in constructor")
    void testAccountRequestConstructor_NullBalance() {
        AccountRequest request = new AccountRequest("ACC123", "John Doe", "email@test.com",
                "1234567890", "SAVINGS", null);

        assertEquals(0.0, request.getInitialBalance());
    }
}

