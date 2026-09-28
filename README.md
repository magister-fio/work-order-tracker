# Work Order Tracker

A small full-stack Work Order Tracker built for a technical assessment.

The application uses:

- Java 21 + Spring Boot for the backend
- React + TypeScript + Vite for the frontend
- SQL Server for persistence
- Docker for the local database
- GitHub Actions for CI

The app supports creating, listing, updating, changing status, and deleting work orders.

## Run locally

### 1. Start SQL Server

```bash
docker pull mcr.microsoft.com/mssql/server:2022-latest

docker run \
  -e "ACCEPT_EULA=Y" \
  -e "MSSQL_SA_PASSWORD=WorkOrder123!" \
  -p 1433:1433 \
  --name workorder-sql \
  -d mcr.microsoft.com/mssql/server:2022-latest
```

Create the database:

```bash
docker exec -it workorder-sql /opt/mssql-tools18/bin/sqlcmd \
  -S localhost \
  -U sa \
  -P "WorkOrder123!" \
  -C
```

Then:

```sql
CREATE DATABASE workorder;
GO
```

### 2. Start backend

```bash
cd backend
mvn spring-boot:run
```

Backend:

```text
http://localhost:8080
```

API:

```text
http://localhost:8080/api/work-orders
```

### 3. Start frontend

```bash
cd frontend
npm install
npm run dev
```

Open:

```text
http://localhost:5173
```

## Assumptions

I kept the project intentionally simple and focused on the assessment requirements.

- Authentication was considered out of scope.
- Status values are limited to `OPEN`, `IN_PROGRESS`, and `COMPLETED`.
- SQL Server is expected to run locally through Docker.
- Hibernate manages the schema during local development.

## Tradeoffs

I avoided adding unnecessary complexity.

For example, I used simple React state and `fetch` instead of a larger frontend state-management library, and I kept the backend structure small with controller, service, repository, DTO, and entity layers.

For a larger production application, I would use database migrations, stronger environment configuration, and more extensive automated testing.

## What I would improve with more time

I would add:

- More unit and integration tests
- Frontend component tests
- Swagger / OpenAPI documentation
- Flyway or Liquibase migrations
- Docker Compose
- Authentication and authorization
- Pagination, filtering, and search
- Better production logging and monitoring

## Production deployment and monitoring

I would deploy the backend as a container, host the frontend through static hosting/CDN, and use a managed SQL Server database.

For monitoring, I would track API latency, error rates, JVM usage, database health, and application logs. I would also add Spring Boot Actuator and centralized logging.

## How I used AI

I used AI as a development partner for focused tasks such as:

- helping with Spring Boot and React setup
- frontend styling
