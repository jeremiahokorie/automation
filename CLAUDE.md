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
- `com.automation.core`: Contains business logic divided into domain modules.
    - **Modules**: `abiaid`, `basepa`, `bauchiplanningboard`, `commerce`, `document`, `global`, `highcourt`, `inspection`, `lands`, `lga`, `mda`, `payment`, `reporting`, `revenue`, `rlms`, `tracking`, `wardactivity`, `yankarireserve`.
    - **Layered Pattern**: Each domain typically follows:
        - `controller`: REST API endpoints.
        - `service`: Business logic interfaces. Implementations are found in either a `serviceImpl` sub-package or alongside the interface (e.g., `InspectionService` and `InspectionServiceImpl`).
        - `repository`: Data access layer using Spring Data JPA.
        - `model`: Database entities.
        - `dto`: Request and response objects (`dto.request` and `dto.response`).
- `com.automation.core.global`: Cross-cutting concerns including User management, Authentication, Roles, Permissions, a global Approval system, and a `verification` module.
- `com.automation.core.reporting`: A centralized reporting system using a Provider pattern. `ReportingDispatcher` routes requests to specific `ReportProvider` implementations (e.g., `CommerceReportProvider`) based on the `ReportSource` enum.
- `com.automation.config`: Global configuration for Security, JWT, Email, and App settings.
- `com.automation.util`: Utility classes, constants, enums, and mappers.
    - `util.integration`: Contains Feign clients and configurations for external services (e.g., InfoBip).
    - `util.jwt`: JWT utility classes.
- `com.automation.common`: Common shared components and utilities.
- `com.automation.events`: Event-driven notification system. `NotificationService` uses `@EventListener` to handle `EmailNotificationEvent` and `SmsNotificationEvent` asynchronously.

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

### Implementing a New Report
1. Add a new entry to the `ReportSource` enum.
2. Create a new `ReportProvider` implementation that defines the `getSource()` and `generateReport()` methods.
3. The `ReportingDispatcher` will automatically detect and use the provider via Spring dependency injection.

### Sending Notifications
1. Create or use an existing `EmailNotificationEvent` or `SmsNotificationEvent`.
2. Publish the event using Spring's `ApplicationEventPublisher`.
3. The `NotificationService` will handle the event based on the `type` condition defined in the `@EventListener`.

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
