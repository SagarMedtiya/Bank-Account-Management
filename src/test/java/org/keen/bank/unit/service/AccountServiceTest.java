package org.keen.bank.unit.service;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.keen.bank.service.AccountService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {

    private AccountService accountService;
    private DefaultCamelContext context;

    @BeforeEach
    void setUp() {
        accountService = new AccountService();
        context = new DefaultCamelContext();
    }
    @Test
    @DisplayName("Should generate unique transaction IDs")
    void testGenerateTransactionId_Unique() {
        String id1 = accountService.generateTransactionId();
        String id2 = accountService.generateTransactionId();

        assertNotNull(id1);
        assertNotNull(id2);
        assertTrue(id1.startsWith("TXN"));
        assertNotEquals(id1, id2);
    }

    @Test
    @DisplayName("Should build valid UPDATE query with multiple fields")
    void testBuildUpdateQuery_WithMultipleFields() {
        Exchange exchange = new DefaultExchange(context);
        Map<String, Object> request = new HashMap<>();
        request.put("customerName", "Updated Name");
        request.put("email", "updated@email.com");
        request.put("phone", "9876543210");

        exchange.getIn().setBody(request);
        exchange.getIn().setHeader("accountNumber", "ACC123");

        assertDoesNotThrow(() -> accountService.buildUpdateQuery(exchange));

        String updateQuery = exchange.getIn().getHeader("updateQuery", String.class);
        Map<String, Object> params= exchange.getIn().getBody(Map.class);
        assertNotNull(updateQuery);
        assertTrue(updateQuery.contains("customer_name"));
        assertTrue(updateQuery.contains("email"));
        assertTrue(updateQuery.contains("phone"));
        assertTrue(updateQuery.contains("ACC123"));
        //verify parameters are set correctly

        assertNotNull(params);
        assertEquals("Updated Name", params.get("customerName"));
        assertEquals("updated@email.com", params.get("email"));
        assertEquals("9876543210", params.get("phone"));
        assertEquals("ACC123", params.get("accountNumber"));
    }
    @Test
    @DisplayName("Should escape SQL injection characters")
    void testEscapeSql_InjectionPrevention() {
        String input = "test'; DROP TABLE accounts; --";
        String result = accountService.escapeSql(input);

        assertEquals("test''; DROP TABLE accounts; --", result);
        assertTrue(result.contains("''")); // This should be true - single quotes are escaped
        assertFalse(result.contains("';"));
    }
    @Test
    @DisplayName("Should throw exception when no valid fields provided for update")
    void testBuildUpdateQuery_NoValidFields() {
        Exchange exchange = new DefaultExchange(context);
        Map<String, Object> request = new HashMap<>();
        request.put("invalidField", "value");

        exchange.getIn().setBody(request);
        exchange.getIn().setHeader("accountNumber", "ACC123");

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> accountService.buildUpdateQuery(exchange));
        assertEquals("No valid fields provided for update", exception.getMessage());
    }
}
