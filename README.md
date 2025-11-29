
# Bank Account Managemen Microservice

A comprehensive banking microservice built with Quarkus Camel (XML-DSL) that provides core banking operations including account management, transactions, and fund transfers using PostgreSQL as the database.
=======

# Bank-Account-Management
=======
# bank-account-management-microservice

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.


## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.


## Application Architecture

* Framework: Quarkus with Apache Camel integration

* Database: PostgreSQL with JPA/Hibernate

* API Style: RESTful endpoints

* Key Features: Account management, deposits, withdrawals, transfers, transaction history

## Environment Requirements

### Software Requirements:

* Java 17 or higher
* Apache Camel 4.11.0 (XML-DSL)
* PostgreSQL 13+
* Apache Maven 3.8+
* JMeter 5.5+
* Docker (optional, for containerized testing)
## Project Structure
```
src/main/java/org/keen/bank/
├── dto/                          # Data Transfer Objects
│   ├── AccountRequest.java
│   ├── AccountResponse.java
│   ├── TransferRequest.java
│   └── TransactionResponse.java
├── service/                      # Business Logic Services
│   ├── AccountService.java
│   └── ValidationService.java
├── resource/                     # REST Endpoints
│   └── AccountResource.java

src/main/resources/
├── application.properties        # Application configuration
├── import.sql                   # Database schema & sample data
└── camel/                       # Camel XML Routes
    ├── account-routes.xml
    ├── transaction-routes.xml
    ├── validation-routes.xml
    └── transaction-recording-routes.xml
```
## Core Functionalities
### Account Management
* Account Creation: Create new bank accounts with validation
* Account Update: Modify account details dynamically
* Account Retrieval: Get account information and balance
### Transaction Operations
* Cash Deposit: Add funds to accounts
* Cash Withdrawal: Remove funds with balance validation
* Fund Transfer: Transfer between accounts with full validation
### Validation & Security
* Input validation for all operations
* Sufficient funds verification
* Account status checks
* Duplicate account prevention

## API Endpoints
### Account Management
| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/accounts/create` | Create new account |
| PUT | `/api/accounts/{accountNumber}` | Update account details |
| GET | `/api/accounts/{accountNumber}` | Get account information |

### Transaction Operations
| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/accounts/{accountNumber}/deposit` | Deposit funds |
| POST | `/api/accounts/{accountNumber}/withdraw` | Withdraw funds |
| POST | `/api/accounts/transfer` | Transfer between accounts |

## Camel XML Routes Architecture
### Route Files Structure
* account-routes.xml - Account CRUD operations
* transaction-routes.xml - Financial transactions
* validation-routes.xml - Input validation logic
* transaction-recording-routes.xml - Audit logging

## Business Logic Services
### AccountService
* Dynamic SQL query building for updates
* Account validation logic
* Transaction ID generation
### ValidationService
* Input validation for all operations
* Business rule enforcement
* Error message formatting
## Test Structure
```
src/test/java/org/keen/bank/unit/
├── service/
│   ├── AccountServiceTest.java
├── dto/
│   ├── AccountRequestTest.java
|   ├── AccountResponseTest.java
│   └── TransferRequestTest.java
├── resource/
|   ├── AccountResourceTest.java

```
## Functional Testing
###  API Endpoints Coverage
| HTTP Method | Endpoint | Purpose | Test Priority |
| --- | --- | --- | --- |
| POST | `/api/accounts/create` | Create new account | High |
| GET | `/api/accounts/{accountNumber}` | Get account details | High |
| PUT | `/api/accounts/{accountNumber}` | Update account | Medium |
| GET | `/api/accounts/{accountNumber}/balance` | Check balance | High |
| POST | `/api/accounts/{accountNumber}/deposit` | Deposit funds | High |
| POST | `/api/accounts/{accountNumber}/withdraw` | Withdraw funds | High |
| POST | `/api/accounts/transfer` | Transfer between accounts | High |

## Testint Strategy
### Unit Tests
* Route functionality testing
* Service layer testing
* Validation logic testing
### Load Testing
* Concurrent account operations
* High-volume transaction processing
* Database connection pooling
 ### Test accounts for functional testing
 ```
INSERT INTO accounts (account_number, customer_name, account_type, balance) VALUES
('FUNC001', 'Functional Test User 1', 'SAVINGS', 5000.0),
('FUNC002', 'Functional Test User 2', 'CHECKING', 3000.0);
```
## Future Enhancements
### Planned Features
1.  Authentication & Authorization
2.  Real-time notifications
3.  Batch processing for bulk operations
4.  Advanced reporting and analytics
5.  Microservices decomposition

### Scalability Improvements
-   Horizontal scaling support
-   Database sharding strategy
-   Message queue integration
-   Caching layer implementation


[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
>>>>>>> 6db71d1 (init)
>>>>>>> 87795fa (Merge remote main into local main)
