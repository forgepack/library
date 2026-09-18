# Árvore de dependências (sem ciclos)
```
└─ [x] forgepack-authorization 			0.0.3 (dependência: forgepack-core, forgepack-validation, forgepack-security)
	└─ [x] forgepack-authentication		0.0.0 (dependência: forgepack-core, forgepack-security, forgepack-authorization)

		├─ [x] forgepack-security 		0.0.5 (dependência: forgepack-core)

		└─ [x] forgepack-core			0.0.23 (dependência: forgepack-validation, forgepack-utils)
			├─ [x] forgepack-validation	0.0.6 (dependência: spring-boot-starter-validation)
			└─ [x] forgepack-utils		0.0.7 (dependência: spring-boot-starter-mail, core de com.google.zxing)
```

## 📦 `forgepack-utils`
**Papel:** Utilitários transversais independentes de domínio — criptografia, QR code e e-mail.

| Classe						| Origem					|
|-------------------------------|---------------------------|
| `ServiceEmail` (interface)	| `api/service/`			|
| `E2EE`						| `internal/utils/`			|
| `QRCode`						| `internal/utils/`			|
| `ServiceEmailImpl`			| `internal/service/`		|

**Dependências Maven:** `com.google.zxing:core`, `spring-boot-starter-mail`

## 📦 `forgepack-validation`
**Papel:** Conjunto de constraints de Bean Validation customizadas 100% portável, zero dependência de Spring Security ou JPA.

| Classe						| Origem					|
|-------------------------------|---------------------------|
| `@HasDigit`, `@HasLength`, `@HasLetter`, `@HasLowerCase`, `@HasUpperCase`, `@Unique`: (interfaces)	| `api/annotation/` |
| `ServiceUniqueChackable` (interface)	| `api/service/`			|
| `Validator` (interface)		| `api/validator`			|
| `ValidatorHasDigit` (interface)	| `api/validator`		|
| `ValidatorHasLenght` (interface)	| `api/validator` 		|
| `ValidatorHasLetter` (interface)	| `api/validator` 		|
| `ValidatorHasLowerCase` (interface)	| `api/validator` 		|
| `ValidatorHasUpperCase` (interface)	| `api/validator` 		|
| `ValidatorUnique` (interface)	| `api/validator` 		|
| `ValidatorHasDigitImpl`		| `internal/validator`		|
| `ValidatorHasLenghtImpl`		| `internal/validator` 		|
| `ValidatorHasLetterImpl`		| `internal/validator` 		|
| `ValidatorHasLowerCaseImpl`	| `internal/validator` 		|
| `ValidatorHasUpperCaseImpl`	| `internal/validator` 		|
| `ValidatorImpl`				| `internal/validator` 		|
| `ValidatorUniqueImpl`			| `internal/validator` 		|
| `ConfigurationAuto`			| `internal/configuration` 	|

**Dependências Maven:** `spring-boot-starter-validation`

## 📦 `forgepack-core`
**Papel:** Base Project for Spring Forgepack library.

| Classe 						| Origem 					|
|-------------------------------|---------------------------|
| `ControllerCrudMutable`		| `api/controller/`			|
| `ControllerCrudRead`			| `api/controller/`			|
| `ControllerCrudRestorable`	| `api/controller/`			|
| `Mapper`						| `api/mapper/`				|
| `DTOIdentifiable`				| `api/payload/`			|
| `RepositoryCrud`				| `api/repository/`			|
| `RepositoryCrudWithName`		| `api/repository/`			|
| `ServiceCrudMutable`			| `api/service/`			|
| `ServiceCrudRead`				| `api/service/`			|
| `ServiceCrudRestorable`		| `api/service/`			|
| `ConfigurationHateoas`		| `internal/configuration/`	|
| `ConfigurationJPAAuto`		| `internal/configuration/`	|
| `ConfigurationOpenAPI`		| `internal/configuration/`	|
| `PropertiesOpenAPI`			| `internal/configuration/`	|
| `ControllerCrudMutableImpl`	| `internal/controller/`	|
| `ControllerCrudReadImpl`		| `internal/controller/`	|
| `ControllerCrudRestorableImpl`| `internal/controller/`	|
| `ApiError`					| `internal/exception/`		|
| `GlobalExceptionHandler`		| `internal/exception/`		|
| `ValidationError`				| `internal/exception/`		|
| `EntityCrud`					| `internal/model/`			|
| `ServiceCrudMutableImpl`		| `internal/service/`		|
| `ServiceCrudReadImpl`			| `internal/service/`		|
| `ServiceCrudRestorableImpl`	| `internal/service/`		|
| `ServiceUtils`				| `internal/service/`		|

**Dependências Maven:** `forgepack-validation`, `forgepack-utils`, `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `spring-boot-starter-hateoas`, `springdoc-openapi-starter-webmvc-ui`, `spring-boot-starter-validation`, `hibernate-envers`, `commons-lang3`, `commons-beanutils`.

## 📦 `forgepack-security`
**Papel:** Toda a infraestrutura de segurança HTTP — rate limiting, security headers, CORS e o `SecurityFilterChain`.

| Classe 														| Origem 							|
|---------------------------------------------------------------|-----------------------------------|
| `ConfigurationSecurity`										| `internal/configuration/` 		|
| `ConfigurationCors` + `PropertiesCors`						| `internal/configuration/` 		|
| `ConfigurationCache` + `CacheConstants` + `PropertiesCache` 	| `internal/configuration/` 		|
| `PropertiesSecurityEndpoints` 								| `internal/configuration/` 		|
| `FilterRateLimiting` + `PropertiesRateLimit` 					| `internal/configuration/filter/` 	|
| `FilterSecurityHeaders` + `PropertiesSecurityHeaders` 		| `internal/configuration/filter/` 	|

**Dependências Maven:** `forgepack-core`, `spring-boot-starter-security`, `bucket4j_jdk17-core`, `caffeine`

## 📦 `forgepack-authorization`
**Papel:** Modelo RBAC completo — User/Role/Privilege com seus endpoints, DTOs e serviços. Inclui JWT e o `UserDetailsService` do Spring Security.

| Classe 														| Origem 							|
|---------------------------------------------------------------|-----------------------------------|
| `Privilege` entity + `Role` entity + `User` entity			| `internal/model` |
| `MapperPrivilege` + `MapperRole` + `MapperUser` implements `Mappper`			| `internal/model` |
| `DTORequestPrivilege` + `DTORequestRole` + `DTORequestUser` implements `DTOIdentifiable`			| `internal/payload`	|
| `DTOResponsePrivilege` + `DTOResponseRole` + `DTOResponseUser` extends `RepresentationModel` implements `DTOIdentifiable`		| `internal/payload` |
| `ControllerPrivilege`, `ControllerRole`, `ControllerUser` extends `ControllerCrudRestorable`	| `internal/controller`	|
| `ServicePrivilege`, `ServiceRole`, `ServiceUser` extends `ServiceCrudRestorableImpl`			| `internal/service`	|
| `RepositoryPrivilege`, `RepositoryRole`, `RepositoryUser` extends `RepositoryCrud`	| `internal/repository`	|
| `ServiceCustomUserDetails` | `internal/service/` |
| `ConfigurationJwt`											| `internal/configuration/` 		|
| `FilterJwt` + `PropertiesJwt` 								| `internal/configuration/filter/` 	|
| `ServiceAuditorAwareImpl` 									| `internal/service/` 				|
| `ConfigurationAudit` 											| `internal/configuration/` 		|
| `EntityCrud`													| `internal/model/`					|
| `Information`													| `internal/utils/` 				|

**Depende de:** `forgepack-security`, `jjwt-api/impl/jackson`


## 📦 `forgepack-authentication`
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
### Necessidades de banco de dados:
- pedido: banco relacional
- busca por descrição: elasticsearch ou opensearch
- mensagem entre serviços: apache kafka ou rabitmq