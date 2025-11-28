package org.keen.bank.functional;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountManagementFT {
    private static final String TEST_ACCOUNT_1 = "FUNCACC001";
    private static final String TEST_ACCOUNT_2 = "FUNCACC002";

    @Test
    @Order(1)
    @DisplayName("Should create account successfully with valid data")
    void testCreateAccount_Success() {
        Map<String, Object> accountRequest = new HashMap<>();
        accountRequest.put("accountNumber", TEST_ACCOUNT_1);
        accountRequest.put("customerName", "Functional Test User");
        accountRequest.put("email", "functional@test.com");
        accountRequest.put("phone", "1234567890");
        accountRequest.put("accountType", "SAVINGS");
        accountRequest.put("initialBalance", 5000.0);

        given()
                .contentType(ContentType.JSON)
                .body(accountRequest)
                .when()
                .post("/api/accounts/create")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("message", equalTo("Account created successfully"));
    }
    @Test
    @Order(2)
    @DisplayName("Should reject duplicate account creation")
    void testCreateAccount_DuplicateAccount() {
        Map<String, Object> accountRequest = new HashMap<>();
        accountRequest.put("accountNumber", TEST_ACCOUNT_1);
        accountRequest.put("customerName", "Another User");
        accountRequest.put("accountType", "CHECKING");
        accountRequest.put("initialBalance", 1000.0);

        given()
                .contentType(ContentType.JSON)
                .body(accountRequest)
                .when()
                .post("/api/accounts/create")
                .then()
                .statusCode(409)
                .body("success", equalTo(false))
                .body("message", equalTo("Account already exists"));
    }

    @Test
    @Order(3)
    @DisplayName("Should retrieve account details successfully")
    void testGetAccount_Success() {
        given()
                .pathParam("accountNumber", TEST_ACCOUNT_1)
                .when()
                .get("/api/accounts/{accountNumber}")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("account.account_number", equalTo(TEST_ACCOUNT_1))
                .body("account.customer_name", equalTo("Functional Test User"))
                .body("account.account_type", equalTo("SAVINGS"))
                .body("account.balance", equalTo(5000.0f));
    }

    @Test
    @Order(4)
    @DisplayName("Should return 404 for non-existent account")
    void testGetAccount_NotFound() {
        given()
                .pathParam("accountNumber", "NONEXISTENT123")
                .when()
                .get("/api/accounts/{accountNumber}")
                .then()
                .statusCode(404)
                .body("success", equalTo(false))
                .body("message", equalTo("Account not found"));
    }

    @Test
    @Order(5)
    @DisplayName("Should get account balance successfully")
    void testGetBalance_Success() {
        given()
                .pathParam("accountNumber", TEST_ACCOUNT_1)
                .when()
                .get("/api/accounts/{accountNumber}/balance")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("accountNumber", equalTo(TEST_ACCOUNT_1))
                .body("balance", equalTo(5000.0f));
    }

    @Test
    @Order(6)
    @DisplayName("Should deposit amount successfully")
    void testDeposit_Success() {
        given()
                .pathParam("accountNumber", TEST_ACCOUNT_1)
                .queryParam("amount", 1000.0)
                .when()
                .post("/api/accounts/{accountNumber}/deposit")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("message", containsString("Deposit completed"));
    }

    @Test
    @Order(7)
    @DisplayName("Should reject deposit with invalid amount")
    void testDeposit_InvalidAmount() {
        given()
                .pathParam("accountNumber", TEST_ACCOUNT_1)
                .queryParam("amount", -100.0)
                .when()
                .post("/api/accounts/{accountNumber}/deposit")
                .then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", containsString("Amount must be positive"));
    }

    @Test
    @Order(8)
    @DisplayName("Should withdraw amount successfully")
    void testWithdraw_Success() {
        given()
                .pathParam("accountNumber", TEST_ACCOUNT_1)
                .queryParam("amount", 500.0)
                .when()
                .post("/api/accounts/{accountNumber}/withdraw")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("message", containsString("Withdrawal completed"));
    }

    @Test
    @Order(9)
    @DisplayName("Should reject withdrawal with insufficient funds")
    void testWithdraw_InsufficientFunds() {
        given()
                .pathParam("accountNumber", TEST_ACCOUNT_1)
                .queryParam("amount", 100000.0)
                .when()
                .post("/api/accounts/{accountNumber}/withdraw")
                .then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", containsString("Insufficient funds"));
    }

}
