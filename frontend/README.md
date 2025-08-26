# Expenso - Simple Finance Tracker

A minimal finance tracking application built with Spring Boot (backend) and React (frontend).

## Features

### Backend (Spring Boot)
- User Registration with Email OTP Verification
- Simple Login with OTP (no JWT complexity)
- Forgot Password with Email OTP
- Transaction Management (Income/Expense)
- MongoDB Integration
- Gmail SMTP for emails

### Frontend (React)
- Clean, simple UI
- Login/Register with OTP flow
- Dashboard with Income vs Expense pie chart
- Transaction creation with predefined categories
- Responsive design

## Tech Stack

**Backend:**
- Spring Boot 3.2.0
- MongoDB
- Spring Mail (Gmail SMTP)
- Maven

**Frontend:**
- React 18
- Tailwind CSS
- Recharts (for pie chart)
- Lucide React (for icons)

## Setup Instructions

### Prerequisites
- Java 17+
- Node.js 18+
- MongoDB (running on localhost:27017)

### Backend Setup
1. Navigate to the `backend` folder
2. Update `src/main/resources/application.properties` if needed
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
   Or use your IDE to run `FinanceTrackerApplication.java`

### Frontend Setup
1. Navigate to the `frontend` folder
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start development server:
   ```bash
   npm run dev
   ```

### Database
MongoDB will automatically create the `finance_tracker` database and collections when you first run the application.

## API Endpoints

### User Management
- `POST /api/users/register` - Register new user
- `POST /api/users/verify-otp` - Verify registration OTP
- `POST /api/users/login` - Login user
- `POST /api/users/verify-login-otp` - Verify login OTP
- `POST /api/users/forgot-password` - Send forgot password OTP
- `POST /api/users/verify-reset-otp` - Verify reset OTP
- `POST /api/users/reset-password` - Reset password

### Transactions
- `POST /api/transactions` - Create transaction
- `GET /api/transactions/user/{userId}` - Get user transactions
- `PUT /api/transactions/{id}` - Update transaction
- `DELETE /api/transactions/{id}` - Delete transaction

## Project Structure

```
├── backend/
│   ├── src/main/java/com/financetracker/
│   │   ├── controller/          # REST Controllers
│   │   ├── model/               # Data Models
│   │   ├── repository/          # Data Access Layer
│   │   ├── service/             # Business Logic
│   │   └── FinanceTrackerApplication.java
│   └── src/main/resources/
│       └── application.properties
└── frontend/
    ├── src/
    │   ├── components/          # React Components
    │   ├── App.tsx              # Main App Component
    │   └── main.tsx             # Entry Point
    └── package.json
```

## Key Design Decisions

1. **Simple Authentication**: No JWT tokens, just simple session management in frontend
2. **Email OTP**: All verification uses email OTP for security
3. **Minimal UI**: Clean, functional interface without over-engineering
4. **Predefined Categories**: Fixed categories for transactions to keep it simple
5. **MongoDB**: Document database for easy data modeling
6. **No Complex State Management**: Simple React state, no Redux/Context complexity

This project demonstrates core full-stack development skills while maintaining simplicity and readability.