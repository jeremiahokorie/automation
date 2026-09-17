# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Development Commands

- Build project: `./mvnw clean install`
- Run application: `./mvnw spring-boot:run`
- Run all tests: `./mvnw test`
- Run a single test: `./mvnw test -Dtest=ClassName`

## Architecture Overview

This is a Spring Boot 3.4.5 application using Java 17, organized with a domain-driven layered architecture.

### Project Structure
- `com.automation.core`: Main business logic divided into domain modules (e.g., `commerce`, `lands`, `wardactivity`, `lga`, `payment`, `global`).
    - Each domain typically follows a layered pattern:
        - `controller`: REST API endpoints.
        - `service`: Business logic interfaces and their implementations (`ServiceImpl`).
        - `repository`: Data access layer using Spring Data JPA.
        - `model`: Database entities.
        - `dto`: Request and response objects for API communication.
- `com.automation.config`: Global configuration for Security, JWT, Email, and App settings.
- `com.automation.util`: Utility classes, constants, enums, and mappers.
- `com.automation.events`: Event-driven notification system for Email and SMS.
- `com.automation.core.global`: Cross-cutting concerns including User management, Authentication, Roles, Permissions, and a global Approval system.

### Technical Stack
- **Framework**: Spring Boot 3.4.5
- **Database**: MySQL with Flyway for schema migrations.
- **Security**: Spring Security with JWT (using `jjwt` library) for authentication and role-based access control.
- **API Documentation**: SpringDoc OpenAPI / Swagger.
- **Key Libraries**:
    - `iTextPDF` / `pdfbox`: PDF generation and manipulation.
    - `OpenFeign`: For external service integration.
    - `Lombok`: To reduce boilerplate code.
    - `Thymeleaf` / `FreeMarker`: Template engines for emails and documents.
    - `spring-boot-starter-validation`: For request body validation.

## Common Development Tasks

### Adding a New Domain Module
1. Create a new package under `com.automation.core.<module_name>`.
2. Implement the layered structure:
    - `model`: Define JPA entities.
    - `repository`: Create Spring Data JPA repositories.
    - `service`: Define a service interface and its `ServiceImpl`.
    - `dto`: Create request and response DTOs.
    - `controller`: Implement REST endpoints using the service.

### Database Migrations
- All schema changes must be handled via Flyway.
- Create new migration scripts in `src/main/resources/db/migration/` following the naming convention `V<Version>__<Description>.sql`.

### API Development
- Use Lombok for boilerplate reduction.
- Annotate controllers with Swagger/OpenAPI annotations for documentation.
- Ensure all new endpoints are secured via Spring Security and integrated with the JWT authentication system.

## Workflow & Branching Policy

- **Branch-First Development:** Before making any code changes, creating new features, or implementing bug fixes, you MUST verify the current branch.
- **Requirement:** If not already on a dedicated feature or fix branch, you must suggest or create a new, appropriately named branch (e.g., `feature/description` or `fix/issue-name`) using `git checkout -b <branch-name>`.
- **Constraint:** Do not apply code changes directly to `main`, `master`, `staging`, `development`, or `pre-develoment` branches.
- **Workflow:**
    1. Assess the task.
    2. Check the current git branch.
    3. If necessary, execute the git command to switch to a new branch.
    4. Proceed with code implementation only after the branch is confirmed.
