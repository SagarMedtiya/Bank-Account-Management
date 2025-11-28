# Bank Account Managemen Microservice

This document outlines the comprehensive testing strategy for the Banking Application built with Java, Quarkus, Apache Camel, and PostgreSQL. The application provides core banking functionalities including account management, transactions, and fund transfers.
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

## Test Environment Requirements

### Software Requirements:

* Java 17 or higher

* PostgreSQL 13+

* Apache Maven 3.8+

* JMeter 5.5+

* Docker (optional, for containerized testing)

## Test Structure
```
src/test/java/org/keen/bank/unit/
├── service/
│   ├── AccountServiceTest.java
│   └── ValidationServiceTest.java
├── dto/
│   ├── AccountRequestTest.java
│   └── TransferRequestTest.java
└── processor/
    └── ValidationProcessorTest.java

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

### Test Data Strategy
 -- Test accounts for functional testing
 ```
INSERT INTO accounts (account_number, customer_name, account_type, balance) VALUES
('FUNC001', 'Functional Test User 1', 'SAVINGS', 5000.0),
('FUNC002', 'Functional Test User 2', 'CHECKING', 3000.0);
```
