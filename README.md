# Hospital / Clinic System

Spring Boot REST API project. Student: Amalbekuulu Miras.

## Tech Stack
- Java 21, Spring Boot 3.2
- PostgreSQL, Spring Data JPA
- Spring Security 6 + JWT
- MapStruct, Lombok
- Springdoc OpenAPI (Swagger UI)
- Docker + Docker Compose

## Run locally

```bash
# start PostgreSQL + app
docker-compose up --build
```

Or run without Docker (PostgreSQL must be running on localhost:5432):
```bash
./gradlew bootRun
```

## Swagger UI
http://localhost:8080/swagger-ui.html

## API Endpoints

| Resource | Base URL |
|----------|----------|
| Auth | /api/auth |
| Patients | /api/patients |
| Doctors | /api/doctors |
| Departments | /api/departments |
| Appointments | /api/appointments |
| Medical Records | /api/medical-records |
| Files | /api/files |
| Reports | /api/reports |

## Auth
1. POST `/api/auth/register` — create account
2. POST `/api/auth/login` — get JWT token
3. Add header: `Authorization: Bearer <token>`
