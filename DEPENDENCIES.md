# Árvore de dependências (sem ciclos)

```
└─ [x] forgepack-security 					0.0.9 (dependência: forgepack-authentication)
	└─ [x] forgepack-authentication			0.0.5 (dependência: forgepack-authorization)
		└─ [x] forgepack-authorization 		0.0.8 (dependência: forgepack-core)
			└─ [x] forgepack-core			0.0.23 (dependência: forgepack-validation, forgepack-utils)
				├─ [x] forgepack-validation	0.0.6 (dependência: spring-boot-starter-validation)
				└─ [x] forgepack-utils		0.0.7 (dependência: spring-boot-starter-test, spring-boot-starter-mail, core de com.google.zxing)
```

## 📦 `forgepack-utils`
**Papel:** Utilitários transversais independentes de domínio — criptografia, QR code e e-mail.

| Classe						| Origem					|
|-------------------------------|---------------------------|
| `ServiceEmail` (interface)	| `api/service/`			|
| `ConfigurationAutomatic`		| `internal/configuration/`	|
| `ConfigurationEmail`			| `internal/configuration/`	|
| `ServiceEmailImpl`			| `internal/service/`		|
| `E2EE`						| `internal/utils/`			|
| `QRCode`						| `internal/utils/`			|

**Dependências Maven:** `spring-boot-starter-test`, `com.google.zxing:core`, `spring-boot-starter-mail`

## 📦 `forgepack-validation`
**Papel:** Conjunto de constraints de Bean Validation customizadas 100% portável, zero dependência de Spring Security ou JPA.

| Classe								| Origem					|
|---------------------------------------|---------------------------|
| `@HasDigit`, `@HasLength`, `@HasLetter`, `@HasLowerCase`, `@HasUpperCase`, `@Unique`: (interfaces)	| `api/annotation/` |
| `ServiceUniqueChackable` (interface)	| `api/service/`			|
| `Validator` (interface)				| `api/validator`			|
| `ValidatorHasDigit` (interface)		| `api/validator`			|
| `ValidatorHasLenght` (interface)		| `api/validator` 			|
| `ValidatorHasLetter` (interface)		| `api/validator` 			|
| `ValidatorHasLowerCase` (interface)	| `api/validator` 			|
| `ValidatorHasUpperCase` (interface)	| `api/validator` 			|
| `ValidatorUnique` (interface)			| `api/validator` 			|
| `ConfigurationAuto`					| `internal/configuration` 	|
| `ValidatorHasDigitImpl`				| `internal/validator`		|
| `ValidatorHasLenghtImpl`				| `internal/validator` 		|
| `ValidatorHasLetterImpl`				| `internal/validator` 		|
| `ValidatorHasLowerCaseImpl`			| `internal/validator` 		|
| `ValidatorHasUpperCaseImpl`			| `internal/validator` 		|
| `ValidatorImpl`						| `internal/validator` 		|
| `ValidatorUniqueImpl`					| `internal/validator` 		|

**Dependências Maven:** `spring-boot-starter-validation`

## 📦 `forgepack-core`
**Papel:** Base Project for Spring Forgepack library.

| Classe 						| Origem 					|
|-------------------------------|---------------------------|
| `ControllerCrudMutable`		| `api/controller/`			|
| `ControllerCrudRead`			| `api/controller/`			|
| `ControllerCrudRestorable`	| `api/controller/`			|
| `Mapper`						| `api/mapper/`				|
| `EntityCrud`					| `api/model/`				|
| `DTOIdentifiable`				| `api/payload/`			|
| `RepositoryCrud`				| `api/repository/`			|
| `RepositoryCrudWithName`		| `api/repository/`			|
| `ServiceCrudMutable`			| `api/service/`			|
| `ServiceCrudRead`				| `api/service/`			|
| `ServiceCrudRestorable`		| `api/service/`			|
| `ConfigurationAudit`			| `internal/configuration/`	|
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
| `ServiceCrudMutableImpl`		| `internal/service/`		|
| `ServiceCrudReadImpl`			| `internal/service/`		|
| `ServiceCrudRestorableImpl`	| `internal/service/`		|
| `ServiceUtils`				| `internal/service/`		|

**Dependências Maven:** `forgepack-validation`, `forgepack-utils`, `spring-boot-starter-test`, `hibernate-envers`, `commons-lang3`, `commons-beanutils`, `springdoc-openapi-starter-webmvc-ui`, `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `spring-boot-starter-hateoas`.

## 📦 `forgepack-authorization`
**Papel:** Modelo RBAC completo — User/Role/Privilege com seus endpoints, DTOs e serviços. Inclui JWT e o `UserDetailsService` do Spring Security.

| Classe 														| Origem 							|
|---------------------------------------------------------------|-----------------------------------|
| `ControllerPrivilege`, `ControllerRole` and `ControllerUser` extends `ControllerCrudRestorable`	| `internal/controller`	|
| `MapperPrivilege`, `MapperRole` and `MapperUser` implements `Mapper`	| `internal/mapper`			|
| `Privilege`, `Role` and `User` extends `EntityCrud`				| `internal/model`					|
| `DTORequestPrivilege`, `DTORequestRole` and `DTORequestUser` implements `DTOIdentifiable`			| `internal/payload`	|
| `DTOResponsePrivilege`, `DTOResponseRole` and `DTOResponseUser` extends `RepresentationModel` implements `DTOIdentifiable`		| `internal/payload` |
| `RepositoryPrivilege`, `RepositoryRole`, `RepositoryUser` extends `RepositoryCrud`	| `internal/repository`	|
| `ServicePrivilege`, `ServiceRole`, `ServiceUser` extends `ServiceCrudRestorableImpl`			| `internal/service`	|
| `ServiceCustomUserDetails` | `internal/service/` |

**Depende de:** `forgepack-core`, `spring-boot-starter-test`

## 📦 `forgepack-authentication`
**Papel:** Fluxo de autenticação — login, logout, refresh token, troca de senha, TOTP/2FA e o endpoint `/auth`.

| Classe | Origem |
|--------|--------|
| `ServiceAuthentication` (interface) 	| `api/service/`			|
| `JwtFilter` 							| `internal/configuration/filter`	|
| `CacheConfiguration`, `CacheConstants`, `CacheProperties` 	| `internal/configuration/`	|
| `JwtConfiguration`					| `internal/configuration`	|
| `JwtProperties` 						| `internal/configuration`	|
| `ControllerAuthentication` 			| `internal/controller/`	|
| `MapperToken` implements `Mapper`		| `internal/mapper`			|
| `Token` extends `EntityCrud`, `CustomUserDetails` extends `User` implements `UserDetails`	| `internal/model`	|
| `DTORequestToken`, `DTORequestUserAuth`, `DTOResponseToken` | `internal/payload`	|
| `RepositoryToken`						| `internal/repository`		|
| `ServiceAuthenticationImpl`, `ServideCustomUserDetails` 			| `internal/service`	|	
| `QRCode`								| `internal/utils`			|

**Depende de:** `forgepack-authorization`, `commons-codec`, `caffeine`, `spring-boot-starter-security`, `jjwt-api`, `jjwt-impl`, `jjwt-jackson`

## 📦 `forgepack-security`
**Papel:** Toda a infraestrutura de segurança HTTP — rate limiting, security headers, CORS e o `SecurityFilterChain`.

| Classe 														| Origem 							|
|---------------------------------------------------------------|-----------------------------------|
| `ConfigurationSecurity`										| `internal/configuration/` 		|
| `ConfigurationCors` + `PropertiesCors`						| `internal/configuration/` 		|
| `PropertiesSecurityEndpoints` 								| `internal/configuration/` 		|
| `FilterRateLimiting` + `PropertiesRateLimit` 					| `internal/configuration/filter/` 	|
| `FilterSecurityHeaders` + `PropertiesSecurityHeaders` 		| `internal/configuration/filter/` 	|

**Dependências Maven:** `forgepack-authentication`, `bucket4j_jdk17-core`, `spring-boot-starter-test`

---
### Necessidades de banco de dados:
- pedido: banco relacional
- busca por descrição: elasticsearch ou opensearch
- mensagem entre serviços: apache kafka ou rabitmq