package org.keen.bank.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.keen.bank.dto.AccountRequest;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class AccountService {
    /*
    * Build dynamic UPDATE query based on provided fields
    */
    public void buildUpdateQuery(Exchange exchange){
        Map<String, Object> request = exchange.getIn().getBody(Map.class);
        String accountNumber = exchange.getIn().getHeader("accountNumber", String.class);

        StringBuilder query = new StringBuilder("UPDATE accounts SET updated_at = CURRENT_TIMESTAMP");
        Map<String, Object> params = new HashMap<>();
        boolean hasFields = false;

        if (request.containsKey("customerName")) {
            query.append(", customer_name = :#customerName");
            params.put("customerName", request.get("customerName"));
            hasFields = true;
        }
        if (request.containsKey("email")) {
            query.append(", email = :#email");
            params.put("email", request.get("email"));
            hasFields = true;
        }
        if (request.containsKey("phone")) {
            query.append(", phone = :#phone");
            params.put("phone", request.get("phone"));
            hasFields = true;
        }
        if (request.containsKey("accountType")) {
            query.append(", account_type = :#accountType");
            params.put("accountType", request.get("accountType"));
            hasFields = true;
        }
        if (request.containsKey("status")) {
            query.append(", status = :#status");
            params.put("status", request.get("status"));
            hasFields = true;
        }

        if (!hasFields) {
            throw new IllegalArgumentException("No valid fields provided for update");
        }

        query.append(" WHERE account_number = :#accountNumber");
        params.put("accountNumber", accountNumber);

        exchange.getIn().setHeader("updateQuery", query.toString());
        exchange.getIn().setBody(params);
    }
    /*
    * Validate account creation request
//    */
//    public void validateAccountCreation(Exchange exchange){
//        AccountRequest request = exchange.getIn().getBody(AccountRequest.class);
//        if(request.getAccountNumber() == null || request.getAccountNumber().trim().isEmpty()){
//            throw new IllegalArgumentException("Account number is required");
//        }
//        if(request.getCustomerName() == null || request.getCustomerName().trim().isEmpty()){
//            throw new IllegalArgumentException("Customer name is required");
//        }
//        if(request.getAccountType() == null || request.getAccountType().trim().isEmpty()){
//            throw new IllegalArgumentException("Account type is required");
//        }
//    }
    /*
    * Generate unique transaction id
     */
    public String generateTransactionId(){
        return "TXN" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
    }

    public String escapeSql(String input) {
        return input.replace("'","''");
    }
}
