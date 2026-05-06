# ABC Telecom Billing System - Setup Guide

## Prerequisites

- Java 25 JDK
- Maven 3.8+
- PostgreSQL 15+
- Git
- IntelliJ IDEA 2024+ (Optional but recommended)

## Step 1: Clone Repository

```bash
git clone <repository-url>
cd abc-telecom-billing
```

## Step 2: Database Setup

### Using PostgreSQL Locally

1. Install PostgreSQL 15
2. Create databases:

```sql
CREATE DATABASE abc_telecom_auth;
CREATE DATABASE abc_telecom_user;
CREATE DATABASE abc_telecom_customer;
CREATE DATABASE abc_telecom_billing;
CREATE DATABASE abc_telecom_payment;
```

### Using Docker

```bash
docker-compose up -d postgres
```

## Step 3: Build Project

```bash
cd abc-telecom-billing
mvn clean install
```

## Step 4: Start Services in Order

### Terminal 1 - Start Eureka Server

```bash
cd abc-telecom-eureka-server
mvn spring-boot:run
```

Access: http://localhost:8761

### Terminal 2 - Start Auth Service

```bash
cd abc-telecom-auth-service
mvn spring-boot:run
```

### Terminal 3 - Start API Gateway

```bash
cd abc-telecom-api-gateway
mvn spring-boot:run
```

Access: http://localhost:8080

### Terminal 4 - Start User Service

```bash
cd abc-telecom-user-service
mvn spring-boot:run
```

### Terminal 5 - Start Customer Service

```bash
cd abc-telecom-customer-service
mvn spring-boot:run
```

### Terminal 6 - Start Billing Service

```bash
cd abc-telecom-billing-service
mvn spring-boot:run
```

### Terminal 7 - Start Payment Service

```bash
cd abc-telecom-payment-service
mvn spring-boot:run
```

## Step 5: Verify Services

### Check Eureka Dashboard

Visit: http://localhost:8761

All services should be registered.

### Test API Gateway

```bash
curl http://localhost:8080/api/auth/validate
```

## Using Docker Compose (Alternative)

```bash
docker-compose up -d
```

This starts all services and PostgreSQL automatically.

## Service Ports

| Service | Port | Path |
|---------|------|------|
| API Gateway | 8080 | http://localhost:8080 |
| Auth Service | 8081 | http://localhost:8081 |
| User Service | 8082 | http://localhost:8082 |
| Customer Service | 8083 | http://localhost:8083 |
| Billing Service | 8084 | http://localhost:8084 |
| Payment Service | 8085 | http://localhost:8085 |
| Eureka Server | 8761 | http://localhost:8761 |

## API Testing

### Register User

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123",
    "confirmPassword": "password123",
    "role": "CUSTOMER"
  }'
```

### Login User

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "testuser",
    "password": "password123"
  }'
```

## Troubleshooting

### Port Already in Use

```bash
# Find process using port
lsof -i :8080

# Kill process
kill -9 <PID>
```

### Database Connection Error

Ensure PostgreSQL is running and databases are created.

### Eureka Service Not Registering

Check that Eureka Server is running on port 8761.

## Next Steps

1. Implement remaining business logic in each service
2. Create Angular 21 frontend
3. Add integration tests
4. Configure circuit breakers
5. Deploy to production
