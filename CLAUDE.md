# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Development Commands

- **Build project**: `./mvnw clean install` (Requires `GITHUB_ACTOR` and `GITHUB_TOKEN` env vars for GitHub Packages authentication via `settings.xml`)
- **Run application**: `./mvnw spring-boot:run`
- **Run all tests**: `./mvnw test`
- **Run a single test**: `./mvnw test -Dtest=ClassName`

## Architecture Overview

This is a Spring Boot 3.4.5 application using Java 17, organized with a domain-driven layered architecture.

### Project Structure
- `com.automation.core`: Contains business logic divided into domain modules (e.g., `abiaid`, `commerce`, `health`, `lands`, `revenue`, etc.).
    - **Layered Pattern**: Each domain typically follows:
        - `controller`: REST API endpoints.
        - `service`: Business logic interfaces. Implementations are found in a `serviceImpl` sub-package or alongside the interface.
        - `repository`: Data access layer using Spring Data JPA.
        - `model`: Database entities.
        - `dto`: Data Transfer Objects, strictly divided into `dto.request` and `dto.response` sub-packages.
- `com.automation.core.global`: Cross-cutting concerns: User management, Authentication (JWT), Roles, Permissions, global Approval system, and verification.
- `com.automation.core.reporting`: Centralized reporting using a Provider pattern. `ReportingDispatcher` routes requests to `ReportProvider` implementations based on the `ReportSource` enum.
- `com.automation.config`: Global configuration (Security, JWT, Email, App settings).
- `com.automation.util`: Utility classes, constants, enums, and mappers (including `util.integration` for Feign clients).
- `com.automation.common`: Shared components.
- `com.automation.events`: Asynchronous notification system using Spring `@EventListener` for `EmailNotificationEvent` and `SmsNotificationEvent`.

### Technical Stack
- **Framework**: Spring Boot 3.4.5 / Java 17
- **Database**: MySQL with Flyway migrations (`src/main/resources/db/migration/`).
- **Security**: Spring Security + JWT (`jjwt`).
- **API Docs**: SpringDoc OpenAPI / Swagger.
- **Storage**: File-based storage (root `/uploads` directory) managed via `StorageService` abstractions.
- **Libraries**: `iTextPDF` / `pdfbox`, `OpenFeign`, `Lombok`, `Thymeleaf` / `FreeMarker`.

## Common Development Tasks

### Adding a New Domain Module
1. Create a new package under `com.automation.core.<module_name>`.
2. Implement the layered structure: `model` $\rightarrow$ `repository` $\rightarrow$ `service` $\rightarrow$ `dto` $\rightarrow$ `controller`.
3. Ensure DTOs are placed in `dto.request` or `dto.response`.

### Implementing a New Report
1. Add a new entry to the `ReportSource` enum.
2. Create a new `ReportProvider` implementation defining `getSource()` and `generateReport()`.
3. The `ReportingDispatcher` will automatically detect the provider via Spring DI.

### Sending Notifications
1. Publish an `EmailNotificationEvent` or `SmsNotificationEvent` using `ApplicationEventPublisher`.
2. The `NotificationService` handles the event based on the `@EventListener` conditions.

### Database Migrations
- Use Flyway scripts in `src/main/resources/db/migration/` following `V<Version>__<Description>.sql`.

## Workflow & Branching Policy

- **Branch-First Development:** Verify the current branch before any code changes.
- **Constraint:** Do not apply changes directly to `main`, `master`, `staging`, `development`, or `pre-development`.
- **Requirement:** Use dedicated branches: `feature/description` or `fix/issue-name`.
- **Workflow:** Assess task $\rightarrow$ Check branch $\rightarrow$ Create/Switch branch $\rightarrow$ Implement.
