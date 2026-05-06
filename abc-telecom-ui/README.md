# ABC Telecom UI - Angular 21

## Frontend for Postpaid Billing System

### Technology Stack

- Angular 21
- TypeScript
- Angular Material / Bootstrap
- RxJS
- HttpClient
- JWT Authentication

### Project Structure

```
abc-telecom-ui/
├── src/
│   ├── app/
│   │   ├── auth/
│   │   │   ├── login/
│   │   │   ├── register/
│   │   │   └── guards/
│   │   ├── dashboard/
│   │   ├── customer/
│   │   │   ├── profile/
│   │   │   ├── services/
│   │   │   └── usage-history/
│   │   ├── billing/
│   │   │   ├── invoices/
│   │   │   └── payment/
│   │   ├── admin/
│   │   │   ├── user-management/
│   │   │   └── invoice-management/
│   │   ├── shared/
│   │   │   ├── services/
│   │   │   └── models/
│   │   └── app.module.ts
│   ├── assets/
│   ├── styles/
│   ├── main.ts
│   └── index.html
├── package.json
├── angular.json
├── tsconfig.json
└── README.md
```

### Key Features

1. **Authentication Module**
   - User Registration
   - Login with JWT
   - OAuth 2.0 Integration
   - Route Guards

2. **Customer Dashboard**
   - Service Overview
   - Outstanding Invoices
   - Quick Actions

3. **Usage Management**
   - View Usage History
   - Download Usage Reports
   - Filter by Date Range

4. **Billing & Invoices**
   - Invoice List
   - Invoice Details
   - PDF Download
   - Payment Status Tracking

5. **Payment Processing**
   - Payment Form
   - Multiple Payment Methods
   - Transaction History
   - Confirmation Messages

6. **Admin Panel**
   - User Management (CRUD)
   - Role Assignment
   - Invoice Generation
   - System Reports

### Installation

```bash
npm install
```

### Development Server

```bash
ng serve
```

Access: http://localhost:4200

### Build for Production

```bash
ng build --prod
```

### Running Tests

```bash
ng test
```

### Environment Configuration

Create `src/environments/environment.ts`:

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```
