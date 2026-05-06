# ABC Telecom - Postpaid Billing System

A comprehensive microservices-based postpaid billing system for telecom operators built with Spring Boot, PostgreSQL, and Angular.

## Technology Stack

### Backend
- **Framework**: Spring Boot 3.x
- **Language**: Java 25
- **Database**: PostgreSQL
- **Authentication**: OAuth 2.0 with JWT
- **Service Discovery**: Eureka Registry
- **API Gateway**: Spring Cloud Gateway
- **Load Balancing**: Ribbon/Spring Cloud LoadBalancer
- **Resilience**: Hystrix Circuit Breaker
- **Build Tool**: Maven

### Frontend
- **Framework**: Angular 21
- **UI Components**: Angular Material/Bootstrap
- **HTTP Client**: Angular HttpClient

## Project Structure

```
abc-telecom-billing/
├── abc-telecom-auth-service/        # OAuth 2.0 Authentication Service
├── abc-telecom-api-gateway/         # API Gateway
├── abc-telecom-eureka-server/       # Service Registry
├── abc-telecom-user-service/        # User Management Service
├── abc-telecom-customer-service/    # Customer Management Service
├── abc-telecom-billing-service/     # Billing & Invoice Service
├── abc-telecom-payment-service/     # Payment Processing Service
├── abc-telecom-ui/                  # Angular Frontend
├── docker-compose.yml               # Docker Configuration
└── docs/                             # Documentation

```

## Features

### User Management
- User registration with role-based access control
- JWT-based authentication
- OAuth 2.0 integration
- User profile management

### Customer Management
- Customer profile management
- Service subscription management
- Usage tracking

### Billing & Invoicing
- Usage record tracking
- Automatic invoice generation
- Invoice management and tracking

### Payment Processing
- Secure payment processing
- Payment history tracking
- Multiple payment methods

### Admin Panel
- User management (CRUD operations)
- Role assignment and management
- Invoice generation and monitoring
- System analytics

## Module Details

Each microservice is independently deployable and communicates through:
1. REST APIs (Inter-service communication)
2. Service Discovery (Eureka)
3. API Gateway for unified entry point
4. Load Balancing for high availability

## Getting Started

### Prerequisites
- Java 25 JDK
- Maven 3.8+
- PostgreSQL 15+
- Node.js 18+ (for Angular)
- IntelliJ IDEA 2024+

### Setup Instructions

1. Clone the repository
2. Navigate to each service directory
3. Run `mvn clean install` for each module
4. Configure PostgreSQL database
5. Start services in order:
   - Eureka Server
   - Auth Service
   - API Gateway
   - Other microservices

## API Documentation

See individual service README files for detailed API endpoints.

## Testing

- Unit Tests: JUnit 5
- Integration Tests: Spring Test, TestContainers
- API Tests: REST Assured
- Target Coverage: 80%+

## Contributors

- rramanarayanan

## License

MIT License
