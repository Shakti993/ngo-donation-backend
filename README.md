# NGO Donation Backend

Spring Boot 3.5 backend for the NGO Donation Platform.

## Prerequisites

- Java 21
- PostgreSQL running on `localhost:5432`
- Database named `ngo_donation_db`

## Configure local development

The `dev` profile is active by default. It uses the local PostgreSQL password configured for this development setup. You can override it for another environment with `DB_PASSWORD`; do not commit production passwords or JWT secrets.

```powershell
$env:DB_USERNAME = "postgres"
$env:DB_PASSWORD = "your-postgres-password"
$env:JWT_SECRET = "your-base64-encoded-secret"
```

## Run

```powershell
.\mvnw.cmd spring-boot:run
```

The API starts at `http://localhost:8080`.
