# Banking Management System

A RESTful Banking Management System built with **Java, Spring Boot, Spring Security, and Spring Data JPA**. The application supports customer and employee management, bank account operations, and secure financial transactions with role-based access control.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java | Backend development |
| Spring Boot | REST API framework |
| Spring Security | Authentication and authorization |
| Spring Data JPA / Hibernate | Database persistence |
| MariaDB | Relational database |
| Maven | Dependency management |
| Lombok | Boilerplate reduction |
| Jakarta Validation | Request validation |

## Features

### Authentication & Authorization
- HTTP Basic Authentication
- BCrypt password hashing
- Role-based access control using `@PreAuthorize`
- Three user roles: `ADMIN`, `EMPLOYEE`, and `CUSTOMER`

### User & Employee Management
- Customer registration and profile management
- Employee registration and management
- Administrative access to user information
- One-to-one user relationships using `@MapsId`

### Bank Account Management
- Create and manage customer accounts
- Activate and block accounts
- View account details and balances
- Validate account numbers and ownership
- Prevent deletion of accounts with non-zero balances

### Banking Operations
- Deposit funds
- Withdraw funds
- Transfer funds between accounts
- Validate available balance and account status
- Use `BigDecimal` for precise financial calculations
- Transaction management using `@Transactional`

## Database Design

| Entity | Description |
|---|---|
| User | Authentication credentials and user roles |
| Customer | Customer information linked to a user |
| Employee | Employee information linked to a user |
| Account | Bank account details, balance, and status |

### Relationships

- **User ↔ Customer:** One-to-One
- **User ↔ Employee:** One-to-One
- **Customer → Account:** One-to-Many

## API Overview

| Module | Operations |
|---|---|
| Users | Retrieve, update, and manage users |
| Customers | Register and manage customer profiles |
| Employees | Register and manage employees |
| Accounts | Create, retrieve, update, delete, activate, and block accounts |
| Transactions | Deposit, withdraw, and transfer funds |

## Security

The application uses Spring Security with HTTP Basic Authentication and role-based authorization.

| Role | Responsibilities |
|---|---|
| ADMIN | Administrative management |
| EMPLOYEE | Employee and authorized banking operations |
| CUSTOMER | Personal account access and banking transactions |

Account ownership is validated before customer-specific financial operations.

## Getting Started

### Prerequisites

- Java 25
- Maven
- MariaDB
- IntelliJ IDEA or another Java IDE

### 1. Clone the Repository

```bash
git clone https://github.com/Inawaf9/project3.git
cd project3
```

### 2. Create the Database

```sql
CREATE DATABASE project3;
```

### 3. Configure the Application

Update `src/main/resources/application.properties`:

```properties
spring.application.name=project3

spring.datasource.url=jdbc:mariadb://localhost:3306/project3
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Use `ddl-auto=update` for local development only when the database schema is already compatible. Schema migrations should be managed explicitly in production.

### 4. Run the Application

```bash
./mvnw spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```

## Project Structure

```text
src/main/java/com/nawaf/project3/
├── Api/
├── Config/
├── Controller/
├── DTO/
├── Model/
├── Repository/
├── Service/
└── Project3Application.java
```

## Implementation Highlights

- Layered architecture separating controllers, services, and repositories
- Method-level authorization with `@PreAuthorize`
- Authenticated user access through `@AuthenticationPrincipal`
- Centralized business validation using custom exceptions
- Financial operations managed with `@Transactional`
- JPA Dirty Checking for entity updates
- Shared primary-key relationships using `@MapsId`

## Notes

This project is intended for educational purposes. It demonstrates banking business logic and backend architecture, but is not designed for production financial services.

In particular, concurrent balance updates require additional locking or concurrency control before production use.

## License

This project is intended for educational and portfolio use.
