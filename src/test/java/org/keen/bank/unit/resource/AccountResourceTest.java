package org.keen.bank.unit.resource;


import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.keen.bank.dto.AccountRequest;
import org.keen.bank.dto.TransferRequest;
import org.keen.bank.resource.AccountResource;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;

@QuarkusTest
public class AccountResourceTest {


    ProducerTemplate producerTemplate;

    @Inject
    AccountResource accountResource;

    @BeforeEach
    void setUp(){
        //Create a mock ProducerTemplate
        producerTemplate = Mockito.mock(ProducerTemplate.class);
        //Manually inject the mock into the resource
        accountResource.producerTemplate = producerTemplate;
        // Debug: Verify the mock is properly set
        System.out.println("ProducerTemplate mock set: " + (accountResource.producerTemplate == producerTemplate));
    }
    @Test
    @DisplayName("Should create account successfully with valid data")
    void testCreateAccount_Success() {
        // Arrange
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("accountNumber", "A001");
        requestMap.put("customerName", "Sagar Medtiya");
        requestMap.put("email", "sagarmed@gmail.com");
        requestMap.put("phone", "1234567890");
        requestMap.put("accountType", "SAVINGS");
        requestMap.put("initialBalance", 1000.0);

        String expectedResponse = "{\"success\": true, \"message\": \"Account created successfully\"}";

        // FIXED: Mock for AccountRequest instead of Map
        Mockito.when(producerTemplate.requestBody(eq("direct:createAccount"), any(AccountRequest.class)))
                .thenReturn(expectedResponse);

        // Act
        Response response = accountResource.createAccount(requestMap);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());
    }

    @Test
    @DisplayName("Should return error when account creation fails")
    void testCreateAccount_Exception() {
        // Arrange
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("accountNumber", "ACC123");
        requestMap.put("customerName", "John Doe");
        requestMap.put("accountType", "SAVINGS");

        // FIXED: Mock for AccountRequest instead of Map
        Mockito.when(producerTemplate.requestBody(eq("direct:createAccount"), any(AccountRequest.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act
        Response response = accountResource.createAccount(requestMap);

        // Assert
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity().toString().contains("success"));
        assertTrue(response.getEntity().toString().contains("false"));
        assertTrue(response.getEntity().toString().contains("Database error"));
    }

    @Test
    @DisplayName("Should update account successfully")
    void testUpdateAccount_Success() {
        // Arrange
        String accountNumber = "ACC123";
        Map<String, Object> updateFields = new HashMap<>();
        updateFields.put("email", "updated@email.com");
        updateFields.put("phone", "9876543210");

        String expectedResponse = "{\"success\": true, \"message\": \"Account updated successfully\"}";

        Mockito.when(producerTemplate.requestBodyAndHeader(
                        eq("direct:updateAccount"),
                        eq(updateFields),
                        eq("accountNumber"),
                        eq(accountNumber)))
                .thenReturn(expectedResponse);

        // Act
        Response response = accountResource.updateAccount(accountNumber, updateFields);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());

        Mockito.verify(producerTemplate).requestBodyAndHeader(
                eq("direct:updateAccount"),
                eq(updateFields),
                eq("accountNumber"),
                eq(accountNumber)
        );
    }

    @Test
    @DisplayName("Should deposit amount successfully")
    void testDeposit_Success() {
        // Arrange
        String accountNumber = "ACC123";
        Double amount = 500.0;

        String expectedResponse = "{\"success\": true, \"message\": \"Deposit completed\"}";

        Mockito.when(producerTemplate.requestBodyAndHeader(
                        eq("direct:deposit"),
                        eq(amount),
                        eq("accountNumber"),
                        eq(accountNumber)))
                .thenReturn(expectedResponse);

        // Act
        Response response = accountResource.deposit(accountNumber, amount);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());

        Mockito.verify(producerTemplate).requestBodyAndHeader(
                eq("direct:deposit"),
                eq(amount),
                eq("accountNumber"),
                eq(accountNumber)
        );
    }

    @Test
    @DisplayName("Should return error when deposit fails")
    void testDeposit_Exception() {
        // Arrange
        String accountNumber = "ACC123";
        Double amount = 500.0;

        Mockito.when(producerTemplate.requestBodyAndHeader(
                        eq("direct:deposit"),
                        eq(amount),
                        eq("accountNumber"),
                        eq(accountNumber)))
                .thenThrow(new RuntimeException("Deposit failed"));

        // Act
        Response response = accountResource.deposit(accountNumber, amount);

        // Assert
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity().toString().contains("success"));
        assertTrue(response.getEntity().toString().contains("false"));
    }

    @Test
    @DisplayName("Should withdraw amount successfully")
    void testWithdraw_Success() {
        // Arrange
        String accountNumber = "ACC123";
        Double amount = 200.0;

        String expectedResponse = "{\"success\": true, \"message\": \"Withdrawal completed\"}";

        Mockito.when(producerTemplate.requestBodyAndHeader(
                        eq("direct:withdraw"),
                        eq(amount),
                        eq("accountNumber"),
                        eq(accountNumber)))
                .thenReturn(expectedResponse);

        // Act
        Response response = accountResource.withdraw(accountNumber, amount);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());

        Mockito.verify(producerTemplate).requestBodyAndHeader(
                eq("direct:withdraw"),
                eq(amount),
                eq("accountNumber"),
                eq(accountNumber)
        );
    }

    @Test
    @DisplayName("Should transfer funds successfully")
    void testTransfer_Success() {
        // Arrange
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setFromAccount("ACC001");
        transferRequest.setToAccount("ACC002");
        transferRequest.setAmount(300.0);
        transferRequest.setDescription("Test transfer");

        String expectedResponse = "{\"success\": true, \"message\": \"Transfer completed successfully\"}";

        Mockito.when(producerTemplate.requestBody(
                        eq("direct:transfer"),
                        eq(transferRequest)))
                .thenReturn(expectedResponse);

        // Act
        Response response = accountResource.transfer(transferRequest);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());

        Mockito.verify(producerTemplate).requestBody(
                eq("direct:transfer"),
                eq(transferRequest));
    }

    @Test
    @DisplayName("Should get account balance successfully")
    void testGetBalance_Success() {
        // Arrange
        String accountNumber = "ACC123";
        String expectedResponse = "{\"success\": true, \"balance\": 1500.0, \"accountNumber\": \"ACC123\"}";

        Mockito.when(producerTemplate.requestBodyAndHeader(
                        eq("direct:getBalance"),
                        isNull(),
                        eq("accountNumber"),
                        eq(accountNumber)))
                .thenReturn(expectedResponse);

        // Act
        Response response = accountResource.getBalance(accountNumber);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());

        Mockito.verify(producerTemplate).requestBodyAndHeader(
                eq("direct:getBalance"),
                isNull(),
                eq("accountNumber"),
                eq(accountNumber)
        );
    }
}
