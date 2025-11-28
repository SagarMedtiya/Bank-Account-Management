package org.keen.bank.unit.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.keen.bank.dto.AccountResponse;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AccountResponseTest {
    @Test
    @DisplayName("Should create success response with factory method")
    void testAccountResponseSuccess() {
        AccountResponse response = AccountResponse.success("Account created successfully");

        assertTrue(response.getSuccess());
        assertEquals("Account created successfully", response.getMessage());
        assertNull(response.getErrorCode());
    }
    @Test
    @DisplayName("Should create error response with error code")
    void testAccountResponseErrorWithCode() {
        AccountResponse response = AccountResponse.error("Validation failed", "VALIDATION_ERROR");

        assertFalse(response.getSuccess());
        assertEquals("Validation failed", response.getMessage());
        assertEquals("VALIDATION_ERROR", response.getErrorCode());
    }
    @Test
    @DisplayName("Should build response using builder pattern")
    void testAccountResponseBuilder() {
        Map<String, Object> accountData = new HashMap<>();
        accountData.put("accountNumber", "ACC123");
        accountData.put("balance", 1500.0);

        AccountResponse response = AccountResponse.builder()
                .success(true)
                .message("Operation successful")
                .account(accountData)
                .newBalance(1500.0)
                .build();

        assertTrue(response.getSuccess());
        assertEquals("Operation successful", response.getMessage());
        assertEquals(accountData, response.getAccount());
        assertEquals(1500.0, response.getNewBalance());
    }
}
