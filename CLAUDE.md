# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Development Commands

- Build project: `./mvnw clean install` (Requires `GITHUB_ACTOR` and `GITHUB_TOKEN` env vars for GitHub Packages authentication via `settings.xml`)
- Run application: `./mvnw spring-boot:run`
- Run all tests: `./mvnw test`
- Run a single test: `./mvnw test -Dtest=ClassName`

## Architecture Overview

This is a Spring Boot 3.4.5 application using Java 17, organized with a domain-driven layered architecture.

### Project Structure
- `com.automation.core`: Contains business logic divided into domain modules (e.g., `abiaid`, `basepa`, `commerce`, `lands`, `revenue`, `wardactivity`). Each domain typically follows a layered pattern:
    - `controller`: REST API endpoints.
    - `service`: Business logic interfaces. Implementation classes are found in either a `serviceImpl` sub-package or directly within the `service` package.
    - `repository`: Data access layer using Spring Data JPA.
    - `model`: Database entities.
    - `dto`: Request and response objects, often further divided into `request` and `response` sub-packages.
- `com.automation.core.global`: Cross-cutting concerns including User management, Authentication, Roles, Permissions, and a global Approval system.
- `com.automation.core.reporting`: A centralized reporting system using a Provider pattern. `ReportingDispatcher` routes requests to specific `ReportProvider` implementations (e.g., `CommerceReportProvider`).
- `com.automation.config`: Global configuration for Security, JWT, Email, and App settings.
- `com.automation.util`: Utility classes, constants, enums, and mappers.
- `com.automation.common`: Common shared components and utilities.
- `com.automation.events`: Event-driven notification system for Email and SMS.

### Technical Stack
- **Framework**: Spring Boot 3.4.5
- **Database**: MySQL with Flyway for schema migrations.
- **Security**: Spring Security with JWT (using `jjwt` library) for authentication and role-based access control.
- **API Documentation**: SpringDoc OpenAPI / Swagger.
- **Key Libraries**: `iTextPDF` / `pdfbox` (PDFs), `OpenFeign` (External services), `Lombok`, `Thymeleaf` / `FreeMarker` (Templates), `spring-boot-starter-validation`.

## Common Development Tasks

### Adding a New Domain Module
1. Create a new package under `com.automation.core.<module_name>`.
2. Implement the layered structure: `model` $\rightarrow$ `repository` $\rightarrow$ `service` $\rightarrow$ `dto` $\rightarrow$ `controller`.

### Database Migrations
- Use Flyway for all schema changes.
- Scripts go in `src/main/resources/db/migration/` following the `V<Version>__<Description>.sql` convention.

### API Development
- Use Lombok for boilerplate reduction.
- Annotate controllers with OpenAPI annotations.
- Ensure endpoints are secured via Spring Security and integrated with the JWT system.

## Workflow & Branching Policy

- **Branch-First Development:** You MUST verify the current branch before any code changes.
- **Constraint:** Do not apply changes directly to `main`, `master`, `staging`, `development`, or `pre-develoment`.
- **Requirement:** Use a dedicated branch: `git checkout -b feature/description` or `git checkout -b fix/issue-name`.
- **Workflow:** Assess task $\rightarrow$ Check branch $\rightarrow$ Create/Switch branch $\rightarrow$ Implement.
