
#  Expenso: Finance Tracker
A full-stack personal finance management application that enables users to track income and expenses with secure authentication and real-time data visualization. The application features a robust Spring Boot backend with RESTful APIs and a responsive React frontend, providing a seamless user experience across all devices.



## Screenshots

![App Screenshot](<img width="1761" height="904" alt="Screenshot 2025-08-25 193256" src="https://github.com/user-attachments/assets/d9f91d5f-f680-495a-ad98-f31af476c698" />)





## Features



### Financial Tracking
- **Transaction Management** - Add, view, and categorize income/expenses
- **Real-time Dashboard** - Visual representation of financial data
- **Category-based Organization** - Predefined categories for easy tracking
- **Balance Calculation** - Automatic net balance computation

### User Management
- **Secure Registration** with email OTP verification
- **Multi-Factor Authentication** for login security
- **Password Reset** functionality with email OTP

### Technical Features
- **Responsive Design** - Works seamlessly on desktop and mobile
- **Real-time Updates** - Instant UI updates after data changes
- **Custom Notifications** - In-page notification system
- **Data Visualization** - CSS-based pie charts for expense analysis
## Tech Stack

- **Frontend**

React 18

Vite (for fast builds and HMR)

Tailwind CSS (utility-first styling)

Lucide React (modern icons)

- **Backend**

Spring Boot (REST API & services)

Spring Data MongoDB (database integration)

Spring Mail (email-based OTP service)

Maven (dependency management & build tool)

- **Database**

MongoDB ## Setup Instructions

### Prerequisites
- Java 17 or higher
- Node.js 18 or higher
- MongoDB running on localhost:27017

### Backend Setup
```bash
cd backend
./mvnw spring-boot:run
```

### Frontend Setup
```bash
npm install
npm run dev
```
