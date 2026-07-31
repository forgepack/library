Agora tenho uma visão completa do projeto. Com base nos 91 arquivos Java, nas dependências reais entre classes e nos domínios de responsabilidade, aqui está a análise completa.

---

## Visão geral do projeto atual

O `library` é um monolito de biblioteca com **5 domínios distintos** interligados:

```
Generic CRUD Framework → Validation → Utils → Security → Authorization → Authentication
```

---

## Proposta de divisão em 6 bibliotecas

> Sugiro **6 bibliotecas** ao invés de 5. A validação customizada merece sua própria lib — ela é 100% independente e reutilizável em qualquer projeto.

### Árvore de dependências (sem ciclos)

```
forgepack-core
    └── forgepack-validation  (opcional, pode ficar no core)
            └── forgepack-utils
                    └── forgepack-security
                            └── forgepack-authorization
                                    └── forgepack-authentication
```

---

### 📦 `forgepack-core`
**Papel:** Framework genérico de CRUD — contratos e implementações base que qualquer aplicação Spring Boot pode usar.

| Classe | Origem |
|--------|--------|
| `GenericAuditEntity` | `internal/model/` |
| `ControllerGeneric` (interface) | `api/controller/` |
| `ControllerLifecycle` (interface) | `api/controller/` |
| `ControllerGenericImpl` (abstract) | `internal/controller/` |
| `ServiceGeneric` (interface) | `api/service/` |
| `ServiceGenericImpl` (abstract) | `internal/service/` |
| `ServiceUniqueCheckable` (interface) | `api/service/` |
| `ServiceAuditorAwareImpl` | `internal/service/` |
| `RepositoryGeneric` (interface) | `api/repository/` |
| `RepositoryGenericWithName` (interface) | `api/repository/` |
| `Mapper` (interface) | `api/mapper/` |
| `DTOIdentifiable` (interface) | `api/payload/` |
| `ConfigurationAudit` | `internal/configuration/` |
| `ConfigurationCache` + `CacheConstants` + `PropertiesCache` | `internal/configuration/` |
| `ConfigurationHateoas` | `internal/configuration/` |
| `ConfigurationJPAAuto` | `internal/configuration/` |
| `GlobalExceptionHandler` + `ApiError` + `ValidationError` | `internal/exception/` |

**Dependências Maven:** `spring-boot-starter-data-jpa`, `spring-boot-starter-hateoas`, `spring-boot-starter-validation`, `caffeine`, `hibernate-envers`, `commons-lang3`, `commons-beanutils`

---

### 📦 `forgepack-validation`
**Papel:** Conjunto de constraints de Bean Validation customizadas — 100% portável, zero dependência de Spring Security ou JPA.

| Classe | Origem |
|--------|--------|
| `@HasDigit`, `@HasLetter`, `@HasLowerCase`, `@HasUpperCase`, `@HasLength`, `@Unique` | `api/annotation/` |
| `Validator` (interface) | `api/validator/` |
| `ValidatorHasDigit`...(interfaces) | `api/validator/` |
| `ValidatorImpl` | `internal/validator/` |
| `ValidatorHasDigitImpl`...(impls) | `internal/validator/` |
| `ValidatorUniqueImpl` | `internal/validator/` |

**Dependências Maven:** `spring-boot-starter-validation`  
> `@Unique` precisa de `ApplicationContext` — única ponte para o Spring. Pode ser mantida aqui ou movida para `forgepack-core`.

---

### 📦 `forgepack-utils`
**Papel:** Utilitários transversais independentes de domínio — criptografia, QR code e e-mail.

| Classe | Origem |
|--------|--------|
| `E2EE` | `internal/utils/` |
| `QRCode` | `internal/utils/` |
| `ServiceEmail` (interface) | `api/service/` |
| `ServiceEmailImpl` | `internal/service/` |

**Dependências Maven:** `commons-codec`, `com.google.zxing:core`, `spring-boot-starter-mail`  
> `Information` (helper de `SecurityContextHolder`) vai para `forgepack-security`, pois tem dependência de Spring Security.

---

### 📦 `forgepack-security`
**Papel:** Toda a infraestrutura de segurança HTTP — JWT, rate limiting, security headers, CORS e o `SecurityFilterChain`.

| Classe | Origem |
|--------|--------|
| `ConfigurationSecurity` | `internal/configuration/` |
| `ConfigurationCors` + `PropertiesCors` | `internal/configuration/` |
| `ConfigurationJwt` | `internal/configuration/` |
| `PropertiesSecurityEndpoints` | `internal/configuration/` |
| `ConfigurationOpenAPI` + `PropertiesOpenAPI` | `internal/configuration/` |
| `FilterJwt` + `PropertiesJwt` | `internal/configuration/filter/` |
| `FilterRateLimiting` + `PropertiesRateLimit` | `internal/configuration/filter/` |
| `FilterSecurityHeaders` + `PropertiesSecurityHeaders` | `internal/configuration/filter/` |
| `Information` | `internal/utils/` |

**Dependências Maven:** `spring-boot-starter-security`, `jjwt-api/impl/jackson`, `bucket4j_jdk17-core`, `caffeine`  
**Depende de:** `forgepack-core`

---

### 📦 `forgepack-authorization`
**Papel:** Modelo RBAC completo — User/Role/Privilege com seus endpoints, DTOs e serviços. Inclui o `UserDetailsService` do Spring Security.

| Classe | Origem |
|--------|--------|
| `Privilege` entity + `RepositoryPrivilege` + `MapperPrivilege` + `DTORequestPrivilege` + `DTOResponsePrivilege` + `ServicePrivilege` + `ControllerPrivilege` | `internal/` |
| `Role` entity + `RepositoryRole` + `MapperRole` + `DTORequestRole` + `DTOResponseRole` + `ServiceRole` + `ControllerRole` | `internal/` |
| `User` entity + `RepositoryUser` + `MapperUser` + `DTORequestUser` + `DTOResponseUser` + `ServiceUser` + `ControllerUser` | `internal/` |
| `ServiceCustomUserDetails` | `internal/service/` |

**Depende de:** `forgepack-core`, `forgepack-validation`, `forgepack-security`, `forgepack-utils`  
> `User` implementa `UserDetails` → necessariamente depende de `spring-security`. `ServiceUser` usa `QRCode` e `ServiceEmail` para envio de boas-vindas → precisa de `forgepack-utils`.

---

### 📦 `forgepack-authentication`
**Papel:** Fluxo de autenticação — login, logout, refresh token, troca de senha, TOTP/2FA e o endpoint `/auth`.

| Classe | Origem |
|--------|--------|
| `Token` entity + `RepositoryToken` + `MapperToken` | `internal/` |
| `DTORequestUserAuth`, `DTORequestToken`, `DTOResponseToken` | `internal/payload/` |
| `ServiceAuthentication` (interface) | `api/service/` |
| `ServiceAuthenticationImpl` | `internal/service/` |
| `ControllerAuthentication` | `internal/controller/` |

**Depende de:** `forgepack-core`, `forgepack-security`, `forgepack-authorization`, `forgepack-utils`  
> `ServiceAuthenticationImpl` usa `E2EE`, `ConfigurationJwt`, `RepositoryUser`, `RepositoryToken` e TOTP manual via HMAC-SHA1.

---

## Resumo das coordenadas Maven

```xml
<!-- Foundation (use sempre) -->
dev.forgepack:forgepack-core
dev.forgepack:forgepack-validation

<!-- Utilitários (use se precisar de email/crypto/QR) -->
dev.forgepack:forgepack-utils

<!-- Segurança HTTP (JWT, rate limit, CORS) -->
dev.forgepack:forgepack-security

<!-- RBAC + gerenciamento de usuários -->
dev.forgepack:forgepack-authorization

<!-- Login / 2FA / refresh token *)
dev.forgepack:forgepack-authentication
```

---

## Decisões-chave

| Decisão | Motivo |
|---------|--------|
| `User` fica em `forgepack-authorization` | Ele é o ponto de integração RBAC + `UserDetails` — depende de `Role` |
| `Token` fica em `forgepack-authentication` | É estritamente um artefato do fluxo de auth (refresh token) |
| `Information` vai para `forgepack-security` | É a única classe de utils com dependência de `SecurityContextHolder` |
| `forgepack-validation` separado | Anotações como `@HasDigit`, `@Unique` são úteis em projetos sem security alguma |
| `ConfigurationOpenAPI` em `forgepack-security` | Documentação da API está no mesmo nível de infraestrutura da segurança HTTP |

---

## Quando usar cada lib isolada

- **Só precisa de CRUD genérico?** → `forgepack-core` + `forgepack-validation`
- **Precisa de JWT + rate limiting mas não quer RBAC?** → adiciona `forgepack-security`
- **Quer RBAC completo mas sem 2FA?** → adiciona `forgepack-authorization`
- **Quer tudo incluindo 2FA/TOTP?** → adiciona `forgepack-authentication`

Quer que eu monte a estrutura de diretórios e os pom.xml de cada uma dessas bibliotecas?

Created 3 todos