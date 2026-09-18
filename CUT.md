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
    └── forgepack-validation
            └── forgepack-utils
                    └── forgepack-security
                            └── forgepack-authorization
                                    └── forgepack-authentication
```

| `ServiceAuditorAwareImpl` | `internal/service/` |
| `ConfigurationAudit` | `internal/configuration/` |

`caffeine`

### 📦 `forgepack-security`
**Papel:** Toda a infraestrutura de segurança HTTP — JWT, rate limiting, security headers, CORS e o `SecurityFilterChain`.

| Classe | Origem |
|--------|--------|
        | `ConfigurationSecurity` | `internal/configuration/` |
| `ConfigurationCors` + `PropertiesCors` | `internal/configuration/` |
        | `ConfigurationJwt` | `internal/configuration/` |
| `PropertiesSecurityEndpoints` | `internal/configuration/` |
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

**Depende de:** `forgepack-core`, `forgepack-security`
> `User` implementa `UserDetails` → necessariamente depende de `spring-security`.

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

<!-- RBAC(Role-Based Access Control) + gerenciamento de usuários -->
dev.forgepack:forgepack-authorization

<!-- Login / 2FA / refresh token *)
dev.forgepack:forgepack-authentication
```

---

## Decisões-chave

| Decisão | Motivo |
|---------|--------|
| `User` fica em `forgepack-authorization` | Ele é o ponto de integração RBAC(Role-Based Access Control) + `UserDetails` — depende de `Role` |
| `Token` fica em `forgepack-authentication` | É estritamente um artefato do fluxo de auth (refresh token) |
| `Information` vai para `forgepack-security` | É a única classe de utils com dependência de `SecurityContextHolder` |
| `forgepack-validation` separado | Anotações como `@HasDigit`, `@Unique` são úteis em projetos sem security alguma |
| `ConfigurationOpenAPI` em `forgepack-security` | Documentação da API está no mesmo nível de infraestrutura da segurança HTTP |

---

## Quando usar cada lib isolada

- **Só precisa de CRUD genérico?** → `forgepack-core` + `forgepack-validation`
- **Precisa de JWT + rate limiting mas não quer RBAC(Role-Based Access Control)?** → adiciona `forgepack-security`
- **Quer RBAC completo mas sem 2FA?** → adiciona `forgepack-authorization`
- **Quer tudo incluindo 2FA/TOTP?** → adiciona `forgepack-authentication`

Quer que eu monte a estrutura de diretórios e os pom.xml de cada uma dessas bibliotecas?

Created 3 todos

Domain-independent cross-cutting utilities: encryption, QR code, and email