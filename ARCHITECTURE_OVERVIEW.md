# ARCHITECTURE OVERVIEW

The __ForgePack Library API__ is built on a layered architecture following the __MVC (Model-View-Controller)__ pattern, adapted for REST APIs.

## 1. MAIN LAYERS

| Layer            | Package       | Responsibility                                                             |
|:-----------------|:--------------|:---------------------------------------------------------------------------|
| __Presentation__ | `controller`  | Handles HTTP requests, validates DTOs, and delegates to the Service layer. |
| __Service__      | `service`     | Contains the core business and transactional logic.                        |
| __Persistence__  | `persistence` | Interacts with the database (JPA/Hibernate).                               |

## 2. KEY DESIGN PATTERNS

### 2.1. CRUD Abstraction (`ControllerGeneric` and `ServiceGeneric`)

* __`ControllerGeneric.java`:__ Implements all CRUD operations, HATEOAS, and security rules (`@PreAuthorize`) in an abstract manner. This ensures that all entity endpoints follow the same pattern without code duplication.
* __`ServiceGeneric.java`:__ Contains common business logic for CRUD operations, such as pagination and sorting.

## 3. REQUEST FLOW (Example: User Creation)

1. __Request:__ The client sends a `POST` request to `/user` with the `DTORequestUser`.
2. __Controller:__ `ControllerUser` receives the request, handled by `ControllerGeneric`.
3. __Validation:__ The `@Valid` annotation validates the `DTORequestUser`.
4. __Service:__ `ServiceUser` (inheriting from `ServiceGeneric`) executes business logic and transaction management.
5. __Persistence:__ `RepositoryUser` saves the entity to the database.
6. __Response:__ `ControllerGeneric` returns a `ResponseEntity.created` containing the `DTOResponseUser` and a HATEOAS link to the new resource.

## ARCHITECTURE & TECHNOLOGIES

### __Detailed Tech Stack__

| Category          | Tecnology / Padrão                            | Detalhes                                                      |
|:------------------|:----------------------------------------------|:--------------------------------------------------------------|
| __Framework__     | Java 17, Spring Boot 3.5.4                    | Modern and robust framework for RESTful APIs                  |
| __Persistence__   | PostgreSQL 17.5, PostGIS, Hibernate Spatial   | Native support for geospatial data and complex queries        |
| __Security__      | Spring Security 6.2.2, JWT, @PreAuthorize     | Authentication via JWT and granular access control            |
| __Design__        | ControllerGeneric, Java Records, HATEOAS      | Abstraction of CRUD operations, immutable DTOs                |
| __Quality__       | JaCoCo 0.8.11, PIT 1.15.8, Surefire, Failsafe | Code coverage, mutation testing, unit and integration testing |
| __Testing__       | Testcontainers 1.20.4, Mockito, JUnit 5       | Testing with real PostgreSQL containers, advanced mocks       |
| __DevOps__        | Docker, Maven, CI/CD Ready                    | Full containerization and build/deploy automation             |
| __Documentation__ | OpenAPI 3, Swagger UI, JavaDoc                | Interactive API documentation                                 |

### 🎨 __Padrões de Design Implementados__
- __Generic Controller Pattern__: Reusable CRUD operations
- __DTO Pattern com Records__: Immutable and type-safe data transfer
- __Repository Pattern__: Persistence abstraction
- __Service Layer Pattern__: Isolated business logic
- __Exception Handler__: Centralized error handling
- __Security Interceptors__: Multi-layered access control
