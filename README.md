# Multi-Organization Transaction Tracker (Backend)

Backend-only REST API using Spring Boot, Spring Security (Google OAuth2 + JWT), and PostgreSQL.

## Quick Start

1. Set environment variables:
   - `GOOGLE_CLIENT_ID`
   - `GOOGLE_CLIENT_SECRET`
   - `JWT_SECRET`
   - (optional) configure DB in `application.yml`

2. Run:
```bash
./mvnw spring-boot:run
```

3. Navigate to `/oauth2/authorization/google` to initiate Google login.

On success, backend responds with `{ "token": "<JWT>" }`.

## Tech Stack
Java 21, Spring Boot 3.3, Spring Security, OAuth2 Client, OAuth2 Resource Server (JWT), PostgreSQL, JPA/Hibernate, Flyway (optional).

## Modules
- Authentication with Google OAuth2 and JWT issuance.
- Organizations CRUD (scaffold to be implemented).
- Transactions for sales/purchases (scaffold to be implemented).

## License
MIT
