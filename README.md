# _ForgePack Library_

[![GitHub stars](https://img.shields.io/github/stars/forgepack/library?style=social)](https://github.com/forgepack/library)
[![GitHub forks](https://img.shields.io/github/forks/forgepack/library?style=social)](https://github.com/forgepack/library/fork)
[![GitHub watchers](https://img.shields.io/github/watchers/forgepack/library?style=social)](https://github.com/forgepack/library)

![GitHub last commit](https://img.shields.io/github/last-commit/forgepack/library)
![Test Coverage](https://img.shields.io/badge/coverage-5%25-orange)
![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Code Quality](https://img.shields.io/badge/code%20quality-A-brightgreen)

## Necessary Tech stack

![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17.5-blue?logo=postgresql)
![PostGIS](https://img.shields.io/badge/PostGIS-3.5-blue?logo=postgis)

![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-2025.1.1.1-000000?logo=intellijidea)
![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen?logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.2.2-brightgreen?logo=spring)
![Maven](https://img.shields.io/badge/Maven-3.12.1-blue?logo=apachemaven)

## Description

How publish an artifact on Maven Central.

## Summary
- [1.Publishing to Maven Central](#1-publishing-to-maven-central)
- [2.Prove Namespace ownership](#2-prove-namespace-ownership)
- [3.Create a GPG Key](#3-create-a-gpg-key)
- [4.Publish th GPG Key](#4-publish-the-gpg-key)
- [5.Configure Maven Credentials](#5-configure-maven-credentials)
- [6.Publish the Artifact](#6-publish-the-artifact)
- [7.Tag the release](#7-tag-the-release)
- [8.Artifact Coordinates](#8-artifact-coordinates)
- [Developers](#developers)
- [License](#license)

Artifact published on Maven Central.
```bash
dev.forgepack:library:1.0.0
```

## 1. Publishing to Maven Central

This document describes the steps required to publish a library to Maven Central using the `dev.forgepack` namespace.

### 1.1. Create Sonatype Account

Create an account on Sonatype Central Portal.

After creating the account:
1. Claim your **groupId / namespace**
2. Prove ownership of the namespace

Example namespace:
```
dev.forgepack
```

---

## 2. Prove Namespace Ownership

Sonatype requires proof that you control the namespace.

Common methods:
### 2.1. Option A — DNS verification

Create a **TXT DNS record** in the domain associated with your namespace.

Example:
```bash
Type: TXT
Name: dev.forgepack
Value: <verification-key>
```

### 2.2. Option B — GitHub namespace

You can also verify ownership using a GitHub repository under the same namespace.

## 3. Create a GPG Key

Maven Central requires all artifacts to be **cryptographically signed**.

Generate or list your GPG key:
```bash
gpg --list-secret-keys --keyid-format LONG
```

Example output:

```bash
sec   rsa4096/ABCD1234EF567890 2026-03-05 [SC]
      9A1B2C3D4E5F67890123456789ABCDEF12345678
uid           Marcelo Gadelha <gadelha.ti@forgepack.dev>
```

The **fingerprint** is the long key:

```
9A1B2C3D4E5F67890123456789ABCDEF12345678
```

## 4. Publish the GPG Key

Upload the public key to a public keyserver.
```bash
gpg --keyserver keyserver.ubuntu.com --send-keys SEU_FINGERPRINT
```

Example:
```bash
gpg --keyserver keyserver.ubuntu.com --send-keys 9A1B2C3D4E5F67890123456789ABCDEF12345678
```

Verify that the key is publicly available:
```bash
gpg --keyserver keyserver.ubuntu.com --recv-keys SEU_FINGERPRINT
```

---

## 5. Configure Maven Credentials

Edit your Maven settings file:
```bash
~/.m2/settings.xml
```

Add your Sonatype credentials:
```xml
<servers>
    <server>
        <id>central</id>
        <username>SEU_USUARIO</username>
        <password>SEU_TOKEN</password>
    </server>
</servers>
```

---

## 6. Publish the Artifact

Run the Maven deploy command:
```bash
mvn clean deploy
```

This will publish the following artifacts:
```bash
library-1.0.0.jar
library-1.0.0.pom
library-1.0.0-sources.jar
library-1.0.0-javadoc.jar
library-1.0.0.asc
```

## 7. Tag the Release

After publishing, create a Git tag for the version.
```bash
git tag v1.0.0
git push origin v1.0.0
```

## Versioning Strategy

This project follows **Semantic Versioning**.
```properties
MAJOR.MINOR.PATCH
```

Examples:
```txt
1.0.0  → first stable release
1.1.0  → new feature
1.1.1  → bug fix
2.0.0  → breaking change
```

| Change          | Version Type |
| --------------- | ------------ |
| Bug fix         | PATCH        |
| New feature     | MINOR        |
| Breaking change | MAJOR        |

## 8. Artifact Coordinates

### 8.1 Dependency declaration
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

### 8.2. Custom application properties:
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
```
## Developers

### Contributors
> _[Gadelha TI](https://github.com/gadelhati)_ - *Architect & Lead Developer*

## License

This project is licensed under the **MIT License** - see the [MIT LICENSE]( https://choosealicense.com/licenses/mit/) file for details.

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
---

<div align="center">

**⭐ Did you like the project? Leave a star! ⭐**

[![GitHub stars](https://img.shields.io/github/stars/forgepack/library?style=social)](https://github.com/forgepack/library)
[![GitHub forks](https://img.shields.io/github/forks/forgepack/library?style=social)](https://github.com/forgepack/library/fork)
[![GitHub watchers](https://img.shields.io/github/watchers/forgepack/library?style=social)](https://github.com/forgepack/library)

**Made by [Forgepack](https://github.com/forgepack)**

</div>