package org.keen.bank.unit.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.keen.bank.dto.TransferRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TransferRequestTest {
    @Test
    @DisplayName("Should build TransferRequest using builder pattern")
    void testTransferRequestBuilder() {
        TransferRequest request = TransferRequest.builder()
                .fromAccount("ACC001")
                .toAccount("ACC002")
                .amount(500.0)
                .description("Monthly savings")
                .build();

        assertEquals("ACC001", request.getFromAccount());
        assertEquals("ACC002", request.getToAccount());
        assertEquals(500.0, request.getAmount());
        assertEquals("Monthly savings", request.getDescription());
    }

    @Test
    @DisplayName("Should handle null description")
    void testTransferRequestNullDescription() {
        TransferRequest request = new TransferRequest("ACC001", "ACC002", 100.0, null);

        assertNull(request.getDescription());
    }
}
