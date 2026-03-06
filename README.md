# ForgePack Library

Artifact published on Maven Central.

```
dev.forgepack:library:1.0.0
```

---

## Publishing to Maven Central

This document describes the steps required to publish a library to Maven Central using the `dev.forgepack` namespace.

---

### 1. Create Sonatype Account

Create an account on Sonatype Central Portal.

After creating the account:

1. Claim your **groupId / namespace**
2. Prove ownership of the namespace

Example namespace:

```
dev.forgepack
```

---

### 2. Prove Namespace Ownership

Sonatype requires proof that you control the namespace.

Common methods:

#### Option A — DNS verification

Create a **TXT DNS record** in the domain associated with your namespace.

Example:

```
Type: TXT
Name: dev.forgepack
Value: <verification-key>
```

#### Option B — GitHub namespace

You can also verify ownership using a GitHub repository under the same namespace.

---

### 3. Create a GPG Key

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

---

### 4. Publish the GPG Key

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

### 5. Configure Maven Credentials

Edit your Maven settings file:

```
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

### 6. Publish the Artifact

Run the Maven deploy command:

```bash
mvn clean deploy
```

This will publish the following artifacts:

```
library-1.0.0.jar
library-1.0.0.pom
library-1.0.0-sources.jar
library-1.0.0-javadoc.jar
library-1.0.0.asc
```

---

### 7. Tag the Release

After publishing, create a Git tag for the version.

```bash
git tag v1.0.0
git push origin v1.0.0
```

---

## Versioning Strategy

This project follows **Semantic Versioning**.

```
MAJOR.MINOR.PATCH
```

Examples:

```
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

---

## Artifact Coordinates

Dependency declaration:

```xml
<dependency>
    <groupId>dev.forgepack</groupId>
    <artifactId>library</artifactId>
    <version>1.0.0</version>
</dependency>
```

---
