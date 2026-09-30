# Clinic-Management-Microservices

A Spring Boot microservices-based Clinic Management Platform demonstrating service discovery, inter-service communication, API Gateway, centralized configuration, and resilience.

## Architecture

```text
                         Client / Postman
                                |
                                v
                     +----------------------+
                     |     API Gateway      |
                     |        :8080         |
                     +----------+-----------+
                                |
                    +-----------+-----------+
                    |           |           |
                    v           v           v
               Patient       Doctor     Appointment
               Service       Service       Service
                :8081         :8082         :8083
                    |           |           |
                    v           v           v
               patient_db   doctor_db   appointment_db
                               
        +----------------+          +----------------+
        | Eureka Server  |          | Config Server  |
        |     :8761      |          |     :8888      |
        +----------------+          +----------------+
```

## Services

| Component           | Port | Responsibility                 |
| ------------------- | ---: | ------------------------------ |
| API Gateway         | 8080 | Single entry point for clients |
| Patient Service     | 8081 | Patient management             |
| Doctor Service      | 8082 | Doctor management              |
| Appointment Service | 8083 | Appointment management         |
| Eureka Server       | 8761 | Service discovery              |
| Config Server       | 8888 | Centralized configuration      |

Each business service owns its **own database**.

---

## Request Flow

A client does not directly call individual services.

For example:

```text
POST /api/v1/appointments
        |
        v
API Gateway :8080
        |
        v
Appointment Service :8083
        |
        +---- Feign ----> Eureka
        |                   |
        |                   +--> Patient Service :8081
        |
        +---- Feign ----> Eureka
                            |
                            +--> Doctor Service :8082
        |
        v
Appointment DB
```

### 1. API Gateway

The client calls:

```text
localhost:8080
```

The Gateway routes requests to the appropriate service.

Example:

```text
/api/v1/patients/**      -> patient-service
/api/v1/doctors/**       -> doctor-service
/api/v1/appointments/** -> appointment-service
```

---

### 2. Eureka Service Discovery

Services register themselves with Eureka.

```text
patient-service      -> :8081
doctor-service       -> :8082
appointment-service  -> :8083
```

Services use **service names instead of hardcoded URLs**.

For example:

```java
@FeignClient(name = "patient-service")
```

Eureka tells Feign where the actual Patient Service instance is running.

---

### 3. OpenFeign

Appointment Service communicates with Patient and Doctor Services using Feign.

```text
Appointment Service
        |
        | @FeignClient
        v
Patient / Doctor Service
```

This avoids manually writing HTTP client code or hardcoding:

```text
http://localhost:8081
http://localhost:8082
```

---

### 4. Database-per-Service

Each business service owns its database.

```text
Patient Service      -> patient_db
Doctor Service       -> doctor_db
Appointment Service  -> appointment_db
```

Appointment Service stores:

```text
patientId
doctorId
```

rather than creating JPA relationships to Patient or Doctor entities.

This keeps services independently deployable and prevents shared database coupling.

---

### 5. Resilience4j

Remote calls can fail because another service may be unavailable.

Resilience4j provides a circuit breaker:

```text
Appointment
     |
     v
Patient Service
     |
     X  unavailable
     |
     v
Circuit Breaker
     |
     v
Fallback
```

The circuit breaker prevents continuous calls to a failing service.

Important states:

```text
CLOSED
   |
   | failures exceed threshold
   v
OPEN
   |
   | wait period
   v
HALF-OPEN
```

---

### 6. Spring Cloud Config

Configuration is centralized through Config Server.

```text
Config Server :8888
       |
       +--> patient-service.yml
       +--> doctor-service.yml
       +--> appointment-service.yml
       +--> api-gateway.yml
```

Services identify themselves using:

```yaml
spring:
  application:
    name: patient-service
```

Config Server then provides the corresponding configuration.

---

## Technology Stack

* Java 21
* Spring Boot
* Spring Cloud
* Spring Data JPA
* PostgreSQL
* Spring Cloud Gateway
* Netflix Eureka
* Spring Cloud OpenFeign
* Spring Cloud Config
* Resilience4j
* Maven
* Docker / Docker Compose

## Key Microservices Concepts Demonstrated

```text
API Gateway
     ↓
Service Discovery
     ↓
Load-balanced service communication
     ↓
OpenFeign
     ↓
Circuit Breaker
     ↓
Centralized Configuration
     ↓
Database per Service
```

### Core principle

> Each service owns its data and responsibility, communicates through APIs, and discovers other services dynamically rather than depending on hardcoded locations.

## Future Improvements

Potential production-level additions:

* Spring Boot Actuator
* Micrometer metrics
* Distributed tracing / OpenTelemetry
* Centralized logging
* Spring Security
* OAuth2 / JWT
* Secret management
* Docker/Kubernetes deployment
* CI/CD
* Automated integration tests
