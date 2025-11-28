package org.keen.bank.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.camel.ProducerTemplate;
import org.keen.bank.dto.AccountRequest;
import org.keen.bank.dto.TransactionListResponse;
import org.keen.bank.dto.TransferRequest;

import java.util.HashMap;
import java.util.Map;

@Path("/api/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {
    @Inject
    ProducerTemplate producerTemplate;

    @POST
    @Path("/create")
    public Response createAccount(Map<String, Object> requestMap){
        try{
            System.out.println("Received Map: " + requestMap);
            System.out.println("AccountNumber from Map: " + requestMap.get("accountNumber"));
            System.out.println("CustomerName from Map: " + requestMap.get("customerName"));
            // Convert map to AccountRequest if needed, or send map directly to Camel
            AccountRequest request = new AccountRequest();
            request.setAccountNumber((String) requestMap.get("accountNumber"));
            request.setCustomerName((String) requestMap.get("customerName"));
            request.setEmail((String) requestMap.get("email"));
            request.setPhone((String) requestMap.get("phone"));
            request.setAccountType((String) requestMap.get("accountType"));
            request.setInitialBalance(requestMap.get("initialBalance") != null ?
                    Double.valueOf(requestMap.get("initialBalance").toString()) : 0.0);
            Object result = producerTemplate.requestBody("direct:createAccount", request);
            return Response.ok(result).build();
        }catch(Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("success", false, "message", e.getMessage())).build();
        }
    }

    @PUT
    @Path("/{accountNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAccount(@PathParam("accountNumber") String accountNumber, Map<String, Object> updateFields){
        try{
            Object result = producerTemplate.requestBodyAndHeader("direct:updateAccount",updateFields,"accountNumber",accountNumber);
            return Response.ok(result).build();
        }
        catch(Exception e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("success", false, "message", e.getMessage())).build();
        }
    }

    @POST
    @Path("/{accountNumber}/deposit")
    public Response deposit(@PathParam("accountNumber") String accountNumber, @QueryParam("amount") Double amount){
        try{
            if(amount == null || amount <= 0){
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("success", false, "message", "Amount must be positive")).build();
            }
            Object result = producerTemplate.requestBodyAndHeader("direct:deposit",amount,"accountNumber",accountNumber);
            return Response.ok(result).build();
        }catch(Exception e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("success", false, "message", e.getMessage())).build();
        }
    }

    @POST
    @Path("/{accountNumber}/withdraw")
    public Response withdraw(@PathParam("accountNumber") String accountNumber, @QueryParam("amount") Double amount){
        try{
            if(amount == null || amount <=0){
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("success", false, "message", "Amount must be positive")).build();
            }
            Object result = producerTemplate.requestBodyAndHeader("direct:withdraw", amount, "accountNumber",accountNumber);
            return Response.ok(result).build();
        }catch(Exception e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("success", false, "message", e.getMessage())).build();
        }
    }

    @POST
    @Path("/transfer")
    public Response transfer(TransferRequest request){
        try{
            Object result = producerTemplate.requestBody("direct:transfer", request);
            return Response.ok(result).build();
        }catch(Exception e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("success", false, "message", e.getMessage())).build();
        }
    }

    @GET
    @Path("/{accountNumber}")
    public Response getAccount(@PathParam("accountNumber") String accountNumber){
        try{
            Object result = producerTemplate.requestBodyAndHeader("direct:getAccount",null,"accountNumber",accountNumber);
            return Response.ok(result).build();
        }
        catch(Exception e){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("success", false, "message", e.getMessage())).build();
        }
    }
//    @GET
//    @Path("/{accountNumber}/transaction")
//    public Response getTransaction(@PathParam("accountNumber") String accountNumber){
//        try{
//            Object result = producerTemplate.requestBodyAndHeader("direct:getTransaction",null,"accountNumber",accountNumber);
//            return Response.ok(result).build();
//        }catch(Exception e){
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity(Map.of("success", false, "message", e.getMessage()))
//                    .build();
//        }
//    }
    @GET
    @Path("/{accountNumber}/balance")
    public Response getBalance(@PathParam("accountNumber") String accountNumber){
        try{
            Object result = producerTemplate.requestBodyAndHeader("direct:getBalance",null,"accountNumber",accountNumber);
            return Response.ok(result).build();
        }catch(Exception e){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("success", false, "message", e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{accountNumber}/transactions")
    public Response getTransactions(@PathParam("accountNumber") String accountNumber,
                                    @QueryParam("startDate") String startDate,
                                    @QueryParam("endDate") String endDate,
                                    @QueryParam("type") String transactionType,
                                    @QueryParam("page") @DefaultValue("0") Integer page,
                                    @QueryParam("size") @DefaultValue("20") Integer size){
        try{
            Map<String, Object> queryParams = new HashMap<>();
            queryParams.put("accountNumber", accountNumber);
            if(startDate != null) queryParams.put("startDate", startDate);
            if(endDate != null) queryParams.put("endDate", endDate);
            if(transactionType != null) queryParams.put("transactionType", transactionType);
            queryParams.put("page", page);
            queryParams.put("size", size);

            Object result = producerTemplate.requestBodyAndHeader("direct:getTransactions",null,queryParams);
            return Response.ok(result).build();
        }
        catch(Exception e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(TransactionListResponse.error(e.getMessage()))
                    .build();
        }

    }

}
