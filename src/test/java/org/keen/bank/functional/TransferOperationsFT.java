package org.keen.bank.functional;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransferOperationsFT {
    private static final String FROM_ACCOUNT = "TRANSFER001";
    private static final String TO_ACCOUNT = "TRANSFER002";

    @Test
    @Order(1)
    @DisplayName("Should setup test accounts for transfer operations")
    void setupTestAccounts() {
        // Create source account
        Map<String, Object> account1 = new HashMap<>();
        account1.put("accountNumber", FROM_ACCOUNT);
        account1.put("customerName", "Transfer Source");
        account1.put("accountType", "SAVINGS");
        account1.put("initialBalance", 10000.0);

        given()
                .contentType(ContentType.JSON)
                .body(account1)
                .when()
                .post("/api/accounts/create")
                .then()
                .statusCode(200);

        // Create destination account
        Map<String, Object> account2 = new HashMap<>();
        account2.put("accountNumber", TO_ACCOUNT);
        account2.put("customerName", "Transfer Destination");
        account2.put("accountType", "CHECKING");
        account2.put("initialBalance", 5000.0);

        given()
                .contentType(ContentType.JSON)
                .body(account2)
                .when()
                .post("/api/accounts/create")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    @DisplayName("Should transfer funds successfully between accounts")
    void testTransfer_Success() {
        Map<String, Object> transferRequest = new HashMap<>();
        transferRequest.put("fromAccount", FROM_ACCOUNT);
        transferRequest.put("toAccount", TO_ACCOUNT);
        transferRequest.put("amount", 1500.0);
        transferRequest.put("description", "Functional test transfer");

        given()
                .contentType(ContentType.JSON)
                .body(transferRequest)
                .when()
                .post("/api/accounts/transfer")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("message", containsString("Transfer completed"));
    }

    @Test
    @Order(3)
    @DisplayName("Should reject transfer to same account")
    void testTransfer_SameAccount() {
        Map<String, Object> transferRequest = new HashMap<>();
        transferRequest.put("fromAccount", FROM_ACCOUNT);
        transferRequest.put("toAccount", FROM_ACCOUNT);
        transferRequest.put("amount", 100.0);

        given()
                .contentType(ContentType.JSON)
                .body(transferRequest)
                .when()
                .post("/api/accounts/transfer")
                .then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", containsString("same account"));
    }

    @Test
    @Order(4)
    @DisplayName("Should reject transfer with insufficient funds")
    void testTransfer_InsufficientFunds() {
        Map<String, Object> transferRequest = new HashMap<>();
        transferRequest.put("fromAccount", FROM_ACCOUNT);
        transferRequest.put("toAccount", TO_ACCOUNT);
        transferRequest.put("amount", 50000.0);

        given()
                .contentType(ContentType.JSON)
                .body(transferRequest)
                .when()
                .post("/api/accounts/transfer")
                .then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", containsString("Insufficient funds"));
    }

    @Test
    @Order(5)
    @DisplayName("Should verify account balances after transfer")
    void testVerifyBalancesAfterTransfer() {
        // Check FROM account balance
        given()
                .pathParam("accountNumber", FROM_ACCOUNT)
                .when()
                .get("/api/accounts/{accountNumber}/balance")
                .then()
                .statusCode(200)
                .body("balance", lessThan(10000.0f));

        // Check TO account balance
        given()
                .pathParam("accountNumber", TO_ACCOUNT)
                .when()
                .get("/api/accounts/{accountNumber}/balance")
                .then()
                .statusCode(200)
                .body("balance", greaterThan(5000.0f));
    }

    @Test
    @Order(6)
    @DisplayName("Should retrieve transaction history")
    void testGetTransactions() {
        given()
                .pathParam("accountNumber", FROM_ACCOUNT)
                .when()
                .get("/api/accounts/{accountNumber}/transactions")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("transactions", notNullValue());
    }
}
