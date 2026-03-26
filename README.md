# Springdale ERP Backend

Production-ready School ERP backend built with Spring Boot, Java, MySQL, JWT authentication, role-based access control, and REST APIs.

## Overview

This project provides a secure and scalable backend for a School ERP platform. It includes authentication, authorization, modular domain packages, validation, exception handling, and API documentation support.

## Tech Stack

* Java 17
* Spring Boot
* Spring Security
* JWT Authentication
* Spring Data JPA
* MySQL
* Maven
* Swagger / OpenAPI
* Spring Boot Actuator
* Flyway

## Core Features

* JWT-based authentication and refresh flow
* Role-based access control
* Student management
* Faculty management
* Attendance management
* Fees management
* Exams management
* Timetable management
* Notices management
* Grievances management
* Reports module
* Global exception handling
* Request validation
* CORS support
* Environment-based configuration
* Production-ready layered architecture

## Project Structure

```text
src/
  main/
    java/com/springdale/erp/
      attendance/
      auth/
      common/
      config/
      exams/
      faculty/
      fees/
      grievances/
      notices/
      reports/
      security/
      students/
      timetable/
      users/
      ErpApplication.java
    resources/
      db/
      application.yml
      application-dev.yml
      application-prod.yml
      logback-spring.xml
  test/
    java/
.mvn/
mvnw
mvnw.cmd
pom.xml
.env.example
.gitignore
README.md
```

## Prerequisites

Make sure the following are installed on your system:

* Java 17
* Maven 3.6+
* MySQL 8+
* Git

## Configuration Strategy

This project uses profile-based configuration:

* `application.yml` → shared defaults
* `application-dev.yml` → local development settings
* `application-prod.yml` → production overrides

Sensitive values are loaded from environment variables and must not be committed to GitHub.

## Environment Variables

Create a local `.env` file for development or configure these variables in your hosting platform.

### Example Development Variables

```env
SPRING_PROFILES_ACTIVE=dev
SERVER_PORT=8080

DB_URL=jdbc:mysql://localhost:3306/school_erp?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
DB_USERNAME=root
DB_PASSWORD=your_db_password

JWT_SECRET=replace-with-strong-32-plus-char-secret
CORS_ALLOWED_ORIGIN=http://localhost:5173
```

### Example Production Variables

```env
SPRING_PROFILES_ACTIVE=prod
SERVER_PORT=8080

DB_URL=jdbc:mysql://your-db-host:3306/school_erp?useSSL=false&serverTimezone=UTC
DB_USERNAME=your_db_user
DB_PASSWORD=your_db_password

JWT_SECRET=replace-with-strong-32-plus-char-secret
CORS_ALLOWED_ORIGIN=https://school-erp-frontend-ts.vercel.app
```

## Running Locally

### 1. Clone the Repository

```bash
git clone https://github.com/Sidd-Tiwari/springdale-erp-backend.git
cd springdale-erp-backend
```

### 2. Configure Environment Variables

Set the required environment variables in your system or create a local `.env` mechanism if you use one in your shell.

### 3. Start MySQL

Ensure MySQL is running and the configured database is accessible.

### 4. Run the Application

Using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

### 5. Build the Project

```bash
./mvnw clean package
```

On Windows:

```bash
mvnw.cmd clean package
```

### 6. Run the Generated JAR

```bash
java -jar target/*.jar
```

## API Documentation

When running locally, access:

* Swagger UI: `http://localhost:8080/swagger-ui/index.html`
* OpenAPI Docs: `http://localhost:8080/v3/api-docs`
* Health Endpoint: `http://localhost:8080/actuator/health`

## Authentication

This backend uses JWT-based authentication.

### Public Auth Endpoints

* `POST /api/auth/login`
* `POST /api/auth/refresh`
* `POST /api/auth/forgot-password` if enabled

### Protected Endpoints

All secured endpoints require this header:

```http
Authorization: Bearer <your_jwt_token>
```

## Role-Based Access

The application supports role-based authorization.

Typical roles include:

* `ADMIN`
* `FACULTY`
* `STUDENT`

Adjust these according to your backend implementation.

## CORS

For local development, allow the frontend origin:

```text
http://localhost:5173
```

For production, allow only the deployed frontend origin:

```text
https://school-erp-frontend-ts.vercel.app
```

Do not use wildcard CORS in production.

## Database and Migrations

This project is designed for MySQL.

### Development

In development, schema auto-update may be enabled depending on `application-dev.yml`.

### Production

In production, use:

* `ddl-auto: validate`
* Flyway migrations enabled

Do not use `create`, `create-drop`, or `update` in production.

## Production Recommendations

Before deploying to production, ensure the following:

* Use `SPRING_PROFILES_ACTIVE=prod`
* Use a strong JWT secret of at least 32 characters
* Set correct production database credentials
* Restrict CORS to the production frontend domain
* Disable demo seed data
* Disable Swagger in production unless explicitly required
* Keep `show-sql=false`
* Use Flyway for schema changes
* Expose only necessary Actuator endpoints

## Deploying to Render

This backend can be deployed directly from GitHub to Render.

### Recommended Render Settings

#### Service Type

* Web Service

#### Repository

* `Sidd-Tiwari/springdale-erp-backend`

#### Branch

* `main`

#### Build Command

```bash
./mvnw clean package -DskipTests
```

#### Start Command

```bash
java -jar target/*.jar
```

#### Health Check Path

```text
/actuator/health
```

### Required Render Environment Variables

```env
SPRING_PROFILES_ACTIVE=prod
SERVER_PORT=8080

DB_URL=jdbc:mysql://your-db-host:3306/school_erp?useSSL=false&serverTimezone=UTC
DB_USERNAME=your_db_user
DB_PASSWORD=your_db_password

JWT_SECRET=replace-with-strong-32-plus-char-secret
CORS_ALLOWED_ORIGIN=https://school-erp-frontend-ts.vercel.app
```

## Git Ignore Notes

Make sure the following are not pushed to GitHub:

* `target/`
* `bin/`
* `.settings/`
* `.project`
* `.classpath`
* `.factorypath`
* `.env`
* real secrets
* logs

## Security Notes

* Never commit real database credentials or JWT secrets
* Never expose internal admin endpoints publicly without authorization
* Keep Swagger disabled in production if not required
* Do not allow all origins in production
* Use HTTPS in production
* Rotate secrets if they were ever exposed

## Troubleshooting

### 1. CORS Error

If the frontend cannot access the backend, verify:

* `CORS_ALLOWED_ORIGIN` is set correctly
* frontend is calling the correct production backend URL
* backend is restarted after env changes

### 2. 401 Unauthorized

Check:

* token is present
* token is sent as `Bearer <token>`
* token is not expired
* secured endpoint is not being called without login

### 3. Database Connection Error

Check:

* MySQL server is running
* `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` are correct
* network/firewall allows the connection

### 4. Render Build Failure

Check:

* Maven Wrapper files exist: `.mvn/`, `mvnw`, `mvnw.cmd`
* `pom.xml` is valid
* Java version is compatible
* env variables are correctly configured

### 5. Swagger Not Opening in Production

This is expected if Swagger is disabled in `application-prod.yml`.

## Example Local Workflow

```bash
git clone https://github.com/Sidd-Tiwari/springdale-erp-backend.git
cd springdale-erp-backend
mvnw.cmd clean package
mvnw.cmd spring-boot:run
```

## Example Git Workflow

```bash
git add .
git commit -m "Updated backend modules"
git push origin main
```

## Maintainer

**Sidd Tiwari**

GitHub: `https://github.com/Sidd-Tiwari`

## License

This project is for internal, educational, or product use as defined by the repository owner.
