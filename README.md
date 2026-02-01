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
- PostgreSQL 12+ (running locally or accessible)
- Apache Kafka (for event processing)

## Database Setup

### 1. Create the database
Connect to PostgreSQL and run the following commands:

```sql
-- Create the database
CREATE DATABASE deposit_db;

-- Create the deposits table (optional - JPA will auto-create with ddl-auto: update)
CREATE TABLE IF NOT EXISTS deposit (
    id BIGSERIAL PRIMARY KEY,
    transaction_id VARCHAR(255) UNIQUE NOT NULL,
    idempotency_key VARCHAR(255) UNIQUE NOT NULL,
    channel VARCHAR(50) NOT NULL,
    service_type VARCHAR(50) NOT NULL,
    debtor_account JSONB NOT NULL,
    creditor_account JSONB NOT NULL,
    amount NUMERIC(19, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    status VARCHAR(50) NOT NULL,
    additional_data JSONB,
    timestamp TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better query performance
CREATE INDEX idx_transaction_id ON deposit(transaction_id);
CREATE INDEX idx_idempotency_key ON deposit(idempotency_key);
CREATE INDEX idx_status ON deposit(status);
CREATE INDEX idx_timestamp ON deposit(timestamp);
```

### 2. Configure database connection
Update `src/main/resources/application.yaml` with your PostgreSQL credentials:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/deposit_db
    username: postgres
    password: your_password
```

## How to Run

### 1. Build the project
```bash
./mvnw clean install
```

### 2. Run the application
```bash
./mvnw spring-boot:run
```

The service will start on `http://localhost:2056` by default.

### 3. Test the endpoints

**Create a deposit:**

Intrabank deposit example:
```bash
curl -X POST http://localhost:2056/api/v1/deposits \
  -H "Content-Type: application/json" \
  -H "channel: intrabank" \
  -H "idempotency-Key: $(uuidgen)" \
  -H "transaction-id: $(uuidgen)" \
  -H "request-source: mobile-app" \
  -d '{
    "debtorAccount": {
      "name": "John Doe",
      "accountId": "1234567890",
      "bankCode": "BDO"
    },
    "creditorAccount": {
      "name": "Jane Smith",
      "accountId": "0987654321",
      "bankCode": "BDO"
    },
    "amount": 1000.00,
    "currency": "PHP",
    "additionalData": {
      "purpose": "Payment for services",
      "reference": "INV-2024-001"
    },
    "debtorNotification": {
      "email": "john.doe@example.com",
      "mobile": "+639171234567"
    },
    "creditorNotification": {
      "email": "jane.smith@example.com",
      "mobile": "+639189876543"
    }
  }'
```

InstaPay deposit example:
```bash
curl -X POST http://localhost:2056/api/v1/deposits \
  -H "Content-Type: application/json" \
  -H "channel: instapay" \
  -H "idempotency-Key: $(uuidgen)" \
  -H "transaction-id: $(uuidgen)" \
  -H "request-source: web-app" \
  -d '{
    "debtorAccount": {
      "name": "Alice Johnson",
      "accountId": "1122334455",
      "bankCode": "BPI"
    },
    "creditorAccount": {
      "name": "Bob Williams",
      "accountId": "5544332211",
      "bankCode": "BDO"
    },
    "amount": 5000.00,
    "currency": "PHP",
    "additionalData": {
      "purpose": "Fund transfer"
    }
  }'
```

PESONet deposit example:
```bash
curl -X POST http://localhost:2056/api/v1/deposits \
  -H "Content-Type: application/json" \
  -H "channel: pesonet" \
  -H "idempotency-Key: $(uuidgen)" \
  -H "transaction-id: $(uuidgen)" \
  -H "request-source: api" \
  -d '{
    "debtorAccount": {
      "name": "Company ABC",
      "accountId": "9988776655",
      "bankCode": "MBTC"
    },
    "creditorAccount": {
      "name": "Supplier XYZ",
      "accountId": "5566778899",
      "bankCode": "UCPB"
    },
    "amount": 25000.00,
    "currency": "PHP",
    "additionalData": {
      "purpose": "Supplier payment",
      "invoiceNumber": "INV-2024-500"
    }
  }'
```

## Available Endpoints
- `POST /api/v1/deposits` - Create a new deposit transaction

### Required Headers
- `channel` - Deposit channel type (`intrabank`, `instapay`, `pesonet`)
- `idempotency-Key` - Unique key to prevent duplicate transactions
- `transaction-id` - Unique transaction identifier
- `request-source` - Source of the request (e.g., `mobile-app`, `web-app`, `api`)