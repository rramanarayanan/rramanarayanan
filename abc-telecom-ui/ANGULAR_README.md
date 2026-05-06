# Angular Frontend - ABC Telecom Billing System

## Project Structure

```
src/
├── app/
│   ├── auth/                    # Authentication module
│   │   ├── login/
│   │   │   ├── login.component.ts
│   │   │   ├── login.component.html
│   │   │   └── login.component.css
│   │   └── register/
│   │       ├── register.component.ts
│   │       ├── register.component.html
│   │       └── register.component.css
│   ├── dashboard/              # Dashboard module
│   │   ├── dashboard.component.ts
│   │   ├── dashboard.component.html
│   │   └── dashboard.component.css
│   ├── shared/                 # Shared module
│   │   ├── components/
│   │   │   ├── navbar/
│   │   ├── guards/
│   │   │   └── auth.guard.ts
│   │   ├── interceptors/
│   │   │   └── auth.interceptor.ts
│   │   ├── models/
│   │   │   ├── auth.model.ts
│   │   │   ├── customer.model.ts
│   │   │   └── billing.model.ts
│   │   └── services/
│   │       ├── auth.service.ts
│   │       ├── customer.service.ts
│   │       └── billing.service.ts
│   ├── app-routing.module.ts
│   ├── app.module.ts
│   ├── app.component.ts
│   ├── app.component.html
│   └── app.component.css
├── environments/
│   ├── environment.ts
│   └── environment.prod.ts
├── main.ts
└── index.html
```

## Installation

```bash
cd abc-telecom-ui
npm install
```

## Development Server

```bash
ng serve
# or
npm start
```

Access at `http://localhost:4200`

## Build

```bash
ng build --prod
# or
npm run build
```

## Running Tests

```bash
ng test
# or
npm test
```

## Features Implemented

### Authentication Module
- User Registration
- User Login
- JWT Token Management
- Auth Guard for Route Protection
- Auth Interceptor for HTTP Requests

### Dashboard
- Welcome Message
- Invoice Summary
- Quick Statistics

### Services
- **AuthService**: Handles authentication
- **CustomerService**: Manages customer data
- **BillingService**: Handles invoices and payments

### Guards & Interceptors
- **AuthGuard**: Protects routes from unauthorized access
- **AuthInterceptor**: Automatically adds JWT token to requests

## API Configuration

Update `src/environments/environment.ts` for development:
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```

Update `src/environments/environment.prod.ts` for production:
```typescript
export const environment = {
  production: true,
  apiUrl: 'http://api.abc-telecom.com/api'
};
```

## Future Components

- Customer Profile Management
- Service Management
- Usage History
- Invoice Details
- Payment Processing
- Admin Panel
- Reports

## Technologies Used

- Angular 17
- TypeScript
- Angular Material
- Bootstrap 5
- RxJS
