# Mini Deposit Service

A production-inspired digital banking deposit microservice built with Java, Spring Boot, Kafka, and PostgreSQL. This project demonstrates real-world patterns for financial transaction orchestration and event-driven system design.

## Tech Stack
- Java 21 (LTS)
- Spring Boot 4.x
- Apache Kafka
- PostgreSQL

## Prerequisites
- Java 21 or higher
- Maven 3.6+

## How to Run

### 1. Build the project
```bash
./mvnw clean install
```

### 2. Run the application
```bash
./mvnw spring-boot:run
```

The service will start on `http://localhost:8080` by default.

### 3. Test the endpoints

**Create a deposit:**
```bash
curl -X POST http://localhost:8080/api/v1/deposits \
  -H "Content-Type: application/json" \
  -d '{
    "accountId": "ACC123456",
    "amount": 100.00,
    "currency": "USD",
    "description": "Test deposit"
  }'
```

**Get deposit status:**
```bash
curl http://localhost:8080/api/v1/deposits/{transactionId}
```

**Health check:**
```bash
curl http://localhost:8080/api/v1/deposits/health
```

## Available Endpoints
- `POST /api/v1/deposits` - Create a new deposit transaction
- `GET /api/v1/deposits/{transactionId}` - Retrieve deposit status
- `GET /api/v1/deposits/health` - Service health check