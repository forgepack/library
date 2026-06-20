# _ForgePack - Library API_
[![GitHub stars](https://img.shields.io/github/stars/forgepack/library?style=social)](https://github.com/forgepack/library)
[![GitHub forks](https://img.shields.io/github/forks/forgepack/library?style=social)](https://github.com/forgepack/library/fork)
[![GitHub watchers](https://img.shields.io/github/watchers/forgepack/library?style=social)](https://github.com/forgepack/library)

![GitHub last commit](https://img.shields.io/github/last-commit/forgepack/library)
![Test Coverage](https://img.shields.io/badge/coverage-5%25-orange)
![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Code Quality](https://img.shields.io/badge/code%20quality-A-brightgreen)

## Necessary Tech Stack
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17.5-blue?logo=postgresql)
![PostGIS](https://img.shields.io/badge/PostGIS-3.5-blue?logo=postgis)

![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-2025.1.1.1-000000?logo=intellijidea)
![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen?logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.2.2-brightgreen?logo=spring)
![Maven](https://img.shields.io/badge/Maven-3.12.1-blue?logo=apachemaven)

## Description

_Library API_ is a robust and modern platform for consolidating interdisciplinary applications.

## SUMMARY
- [1.QUALITY & TESTING](#1-quality-&-testing)
- [2.API DOCUMENTATION](#2-api-documentation)
- [3.ARTIFACT COORDINATES](#3-artifact-coordinates)
- [4.Publish th GPG Key](#4-publish-the-gpg-key)
- [5.Configure Maven Credentials](#5-configure-maven-credentials)
- [6.Publish the Artifact](#6-publish-the-artifact)
- [7.Tag the release](#7-tag-the-release)
- [8.Artifact Coordinates](#8-artifact-coordinates)
- [Developers](#developers)
- [License](#license)

## 1. QUALITY & TESTING

### 1.1. Current Coverage Metrics

GENERAL COVERAGE: 5%
TOTAL NUMBER OF TESTS: 126

| Package                                              | Coverage |        |
|:-----------------------------------------------------|:--------:|:------:|
| 📁 dev.forgepack.library.persistence.model           |   100%   |   ✅   |
| 📁 dev.forgepack.library.configuration.interceptor   |   40%    |   🟡   |
| 📁 dev.forgepack.library.utils                       |   34%    |   🟡   |
| 📁 dev.forgepack.library.persistence.payload.request |   18%    |   🟠   |
| 📁 dev.forgepack.library.configuration               |   18%    |   🟠   |
| 📁 dev.forgepack.library.controller                  |    6%    |   🔴   |
| 📁 dev.forgepack.library.service                     |    2%    |   🔴   |
| 📁 demais pacotes                                    |    0%    |   🔴   |

### 1.2. Types of Tests Implemented
1. __Unit Tests__: Service layer, Utils, DTOs
2. __Integration Tests__: Testcontainers + PostgreSQL
3. __Controller Tests__: MockMVC with validation REST
4. __Validation Tests__: Bean Validation and custom annotations
5. __Security Tests__: JWT, authentication, authorization

## 2. API Documentation

### 2.1. Main Endpoints

All resources follow the RESTful standard with complete CRUD operations
> __Base URL__ [http://localhost:8080/demo](http://localhost:8080/demo`)

| Endpoint                                                  | Method                        | Description                                                         | Exemple              |
|-----------------------------------------------------------|:------------------------------|:--------------------------------------------------------------------|:---------------------|
| [CREATE](http://localhost:8080/demo/user)                 | `POST /{resource}`            | path to item creation                                               | `POST /user`         |
| [RETRIEVE](http://localhost:8080/demo/user/id)            | `GET /{resource}/{id}`        | path to search for item by id                                       | `GET /user/123`      |
| [RETRIEVE ALL](http://localhost:8080/demo/user/attribute) | `GET /{resource}/{attribute}` | path to search for item by attribute or all items without attribute | `GET /user/username` |
| [UPDATE](http://localhost:8080/demo/user/id)              | `PUT /{resource}/{id}`        | path to item update                                                 | `PUT /user/123`      |
| [DELETE](http://localhost:8080/demo/user/id)              | `DELETE /{resource}/{id}`     | path to item delete                                                 | `DELETE /user/123`   |

## 2.2. Swagger Documentation
Access the interactive documentation when the application is running.
> __Swagger__ [http://localhost:8080/demo/v1/swagger-ui/index.html](http://localhost:8080/demo/v1/swagger-ui/index.html)

### 🔗 __Links Úteis__
> __Home__: [http://localhost:8080/demo](http://localhost:8080/demo)

> __Health Check__: [http://localhost:8080/demo/actuator/health](http://localhost:8080/demo/actuator/health)

> __Metrics__: [http://localhost:8080/demo/actuator/metrics](http://localhost:8080/demo/actuator/metrics)

> __Info__: [http://localhost:8080/demo/actuator/info](http://localhost:8080/demo/actuator/info)

## 2.2. Available Resources
| Resource      | Endpoint          | Description        | Exemple                    |
|:--------------|:------------------|:-------------------|:---------------------------|
| __Users__     | `/demo/user`      | Gestão de usuários | `GET /demo/user`           |
| __Roles__     | `/demo/role`      | Controle de perfis | `POST /demo/role`          |
| __Privilege__ | `/demo/privilege` | Privilégios        | `PUT /demo/privilege/1`    |
| __Research__  | `/demo/research`  | Privilégios        | `DELETE /demo/privilege/1` |

## 2.3. Authentication & Authorization

```http
# login and obtaining the JWT
POST /demo/auth/login
Content-Type: application/json
{
    "username": "12345678", 
    "password": "P@ssword123"
}

# response
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "type": "Bearer ",
    "expiration": "2024-12-11T10:30:00Z"
}

# use the token in subsequent requests
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## 2.4. Pagination

```http
# pagination
GET /demo/user/search?page=2&size=5

# ordering
GET /demo/user/search?sort=name,desc&sort=email,asc

# custom filters  
GET /demo/user/search?name=João&active=true&page=0&size=10
```

## 3. ARTIFACT COORDINATES

### 3.1. Dependency declaration
```xml

<dependencies>
    <dependency>
        <groupId>dev.forgepack</groupId>
        <artifactId>library</artifactId>
        <version>1.0.78</version>
    </dependency>
    <!-- ╔══════════════════════════════════════════════╗ -->
    <!-- ║             HIBERNATE EXTENSIONS             ║ -->
    <!-- ╚══════════════════════════════════════════════╝ -->
    <dependency>
        <groupId>org.hibernate.orm</groupId>
        <artifactId>hibernate-spatial</artifactId>
    </dependency>
    <!-- ╔══════════════════════════════════════════════╗ -->
    <!-- ║            DATABASE CONFIGURATIONS           ║ -->
    <!-- ╚══════════════════════════════════════════════╝ -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>net.postgis</groupId>
        <artifactId>postgis-jdbc</artifactId>
        <version>2025.1.1</version>
    </dependency>
</dependencies>
```

### 3.2. Plugin declaration
```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <executions>
        <execution>
            <goals>
                <goal>build-info</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### 3.3. Custom application properties:
```properties
# ╔══════════════════════════════════════════════╗
# ║  DATABASE CONFIGURATIONS - LOCAL POSTGRESQL  ║
# ╚══════════════════════════════════════════════╝
spring.datasource.url = jdbc:postgresql://localhost:5432/demo
spring.datasource.username = postgres
spring.datasource.password = G@delha
spring.datasource.platform = postgres
spring.datasource.driver-class-name = org.postgresql.Driver

# ╔══════════════════════════════════════════════╗
# ║          HIBERNATE/JPA CONFIGURATIONS        ║
# ╚══════════════════════════════════════════════╝
# DDL-AUTO = create: recreates database on each startup (careful in production!)
spring.jpa.hibernate.ddl-auto = create
spring.jpa.properties.hibernate.dialect = org.hibernate.spatial.dialect.postgis.PostgisPG95Dialect
spring.jpa.properties.hibernate.default_schema = demo
spring.jpa.show-sql = true

forgepack.security.endpoints.permitAll=/public/**, /health/custom
forgepack.security.endpoints.permitPost=/api/register, /api/forgot-password
forgepack.security.endpoints.permitPut=/api/confirm-email
```
## DEVELOPERS

### Contributors
> _[Gadelha TI](https://github.com/gadelhati)_ - *Architect & Lead Developer*

## LICENCE

This project is licensed under the __MIT License__ - see the [MIT LICENSE]( https://choosealicense.com/licenses/mit/) file for details.

```text
MIT License

Copyright (c) 2024 Gadelha TI

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

<div style="text-align: center;">

__⭐ Did you like the project? Leave a star! ⭐__

[![GitHub stars](https://img.shields.io/github/stars/forgepack/library?style=social)](https://github.com/forgepack/library)
[![GitHub forks](https://img.shields.io/github/forks/forgepack/library?style=social)](https://github.com/forgepack/library/fork)
[![GitHub watchers](https://img.shields.io/github/watchers/forgepack/library?style=social)](https://github.com/forgepack/library)

__Made by [Forgepack](https://github.com/forgepack)__

</div>