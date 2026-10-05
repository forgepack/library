# Árvore de dependências (sem ciclos)

```
└─ [x] forgepack-security 					0.0.9 (dependência: forgepack-authentication)
	└─ [x] forgepack-authentication			0.0.5 (dependência: forgepack-authorization)
		├─ [x] forgepack-utils				0.0.9
		└─ [x] forgepack-authorization 		0.0.9
			├─ [x] forgepack-validation		0.0.9
			└─ [x] forgepack-core			0.0.26
```

## 📦 `forgepack-utils`
**Papel:** Utilitários como criptografia, QR code e e-mail.

| Classe						| Origem					|
|-------------------------------|---------------------------|
| `EncryptorException`			| `api/exception/`			|
| `EmailService` (interface)	| `api/service/`			|
| `EncryptorService` (interface)	| `api/service/`			|
| `EmailAutoConfiguration`		| `internal/configuration/`	|
| `EncryptorAutoConfiguration`	| `internal/configuration/`	|
| `EmailServiceImpl`			| `internal/service/`		|
| `EncryptorServiceImpl`		| `internal/service/`		|
| `QRCode`						| `internal/utils/`			|

**Dependências Maven:** `spring-boot-starter-test` escopo `test`, `spring-boot-starter-mail`, `com.google.zxing:core`

## 📦 `forgepack-validation`
**Papel:** Conjunto de constraints de Bean Validation customizadas, sem dependência direta de Spring Security ou JPA.

| Classe								| Origem					|
|---------------------------------------|---------------------------|
| `@HasDigit`, `@HasLength`, `@HasLetter`, `@HasLowerCase`, `@HasUpperCase`, `@Unique`: (interfaces)	| `api/annotation/` |
| `UniqueCheckableService` (interface)	| `api/service/`			|
| `ValidatorRules` (interface)			| `api/validator/`			|
| `HasDigitValidator` (interface)		| `api/validator/`			|
| `HasLengthValidator` (interface)		| `api/validator/` 			|
| `HasLetterValidator` (interface)		| `api/validator/` 			|
| `HasLowerCaseValidator` (interface)	| `api/validator/` 			|
| `HasUpperCaseValidator` (interface)	| `api/validator/` 			|
| `UniqueValidator` (interface)			| `api/validator/` 			|
| `HasDigitValidatorImpl`				| `internal/validator/`		|
| `HasLengthValidatorImpl`				| `internal/validator/` 	|
| `HasLetterValidatorImpl`				| `internal/validator/` 	|
| `HasLowerCaseValidatorImpl`			| `internal/validator/` 	|
| `HasUpperCaseValidatorImpl`			| `internal/validator/` 	|
| `ValidatorRulesImpl`						| `internal/validator/` 	|
| `UniqueValidatorImpl`					| `internal/validator/` 	|

**Dependências Maven:** `spring-boot-starter-test` escopo `test`, `spring-boot-starter-validation`

## 📦 `forgepack-core`
**Papel:** Projeto base.

| Classe 						| Origem 					|
|-------------------------------|---------------------------|
| `MutableController`			| `api/controller/`			|
| `ReadController`				| `api/controller/`			|
| `RestorableController`		| `api/controller/`			|
| `Mapper`						| `api/mapper/`				|
| `EntityCrud`					| `api/model/`				|
| `DTOIdentifiable`				| `api/payload/`			|
| `CrudRepository`				| `api/repository/`			|
| `CrudWithNameRepository`		| `api/repository/`			|
| `MutableService`		 		| `api/service/`			|
| `ReadService`					| `api/service/`			|
| `RestorableService`			| `api/service/`			|
| `AuditAutoConfiguration`		| `internal/configuration/`	|
| `HateoasAutoConfiguration`	| `internal/configuration/`	|
| `OpenAPIAutoConfiguration`	| `internal/configuration/`	|
| `OpenAPIProperties`			| `internal/configuration/`	|
| `WebAutoConfiguration`		| `internal/configuration/`	|
| `MutableControllerImpl`		| `internal/controller/`	|
| `ReadControllerImpl`			| `internal/controller/`	|
| `RestorableControllerImpl`	| `internal/controller/`	|
| `ApiError`					| `internal/exception/`		|
| `GlobalExceptionHandler`		| `internal/exception/`		|
| `ValidationError`				| `internal/exception/`		|
| `MutableServiceImpl`			| `internal/service/`		|
| `ReadServiceImpl`				| `internal/service/`		|
| `RestorableImpl`				| `internal/service/`		|
| `ServiceUtils`				| `internal/service/`		|

**Dependências Maven:** `h2`, `spring-boot-starter-test` escopo `test`, `hibernate-envers`, `commons-lang3`, `commons-beanutils`, `springdoc-openapi-starter-webmvc-ui`, `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `spring-boot-starter-hateoas`.

## 📦 `forgepack-authorization`
**Papel:** Modelo RBAC(Role-Based Access Control) completo, gerenciamento de User/Role/Privilege com seus endpoints, DTOs e serviços.

| Classe 														| Origem 							|
|---------------------------------------------------------------|-----------------------------------|
| `AuthorizationAutoConfiguration` 	| `internal/configuration/`	|
| `ControllerPrivilege`, `ControllerRole` and `ControllerUser` extends `ControllerCrudRestorable`	| `internal/controller/`	|
| `MapperPrivilege`, `MapperRole` and `MapperUser` implements `Mapper`	| `internal/mapper/`			|
| `Privilege`, `Role` and `User` extends `EntityCrud`				| `internal/model/`					|
| `DTORequestPrivilege`, `DTORequestRole` and `DTORequestUser` implements `DTOIdentifiable`			| `internal/payload/`	|
| `DTOResponsePrivilege`, `DTOResponseRole` and `DTOResponseUser` extends `RepresentationModel` implements `DTOIdentifiable`		| `internal/payload/` |
| `RepositoryPrivilege`, `RepositoryRole`, `RepositoryUser` extends `RepositoryCrud`	| `internal/repository/`	|
| `ServicePrivilege`, `ServiceRole`, `ServiceUser` extends `ServiceCrudRestorableImpl`			| `internal/service/`	|

**Depende de:** `forgepack-core`, `forgepack-validation`, `spring-boot-starter-test` escopo `test`

## 📦 `forgepack-authentication`
**Papel:** Fluxo de autenticação — login, logout, refresh token, troca de senha, JWT, TOTP/2FA e o endpoint `/auth`.

| Classe | Origem |
|--------|--------|
| `ServiceAuthentication` (interface) 	| `api/service/`			|
| `JwtFilter` 							| `internal/configuration/filter/`	|
| `CacheConfiguration`, `CacheConstants`, `CacheProperties` 		| `internal/configuration/`	|
| `JwtConfiguration`					| `internal/configuration/`	|
| `JwtProperties` 						| `internal/configuration/`	|
| `ControllerAuthentication` 			| `internal/controller/`	|
| `MapperToken` implements `Mapper`		| `internal/mapper/`			|
| `Token` extends `EntityCrud`, `CustomUserDetails` extends `User` implements `UserDetails`	| `internal/model/`	|
| `DTORequestToken`, `DTORequestUserAuth`, `DTOResponseToken` | `internal/payload/`	|
| `RepositoryToken`						| `internal/repository/`		|
| `ServiceAuthenticationImpl`, `ServiceCustomUserDetails` 			| `internal/service/`	|	

**Depende de:** `spring-boot-starter-test` escopo `test`, `forgepack-utils`, `forgepack-authorization`, `commons-codec`, `caffeine`, `spring-boot-starter-security`, `jjwt-api`, `jjwt-impl`, `jjwt-jackson`

## 📦 `forgepack-security`
**Papel:** Toda a infraestrutura de segurança HTTP — rate limiting, security headers, CORS e o `SecurityFilterChain`.

| Classe 														| Origem 							|
|---------------------------------------------------------------|-----------------------------------|
| `ConfigurationSecurity`										| `internal/configuration/` 		|
| `ConfigurationCors` + `PropertiesCors`						| `internal/configuration/` 		|
| `PropertiesSecurityEndpoints` 								| `internal/configuration/` 		|
| `FilterRateLimiting` + `PropertiesRateLimit` 					| `internal/configuration/filter/` 	|
| `FilterSecurityHeaders` + `PropertiesSecurityHeaders` 		| `internal/configuration/filter/` 	|

**Dependências Maven:** `forgepack-authentication`, `bucket4j_jdk17-core`, `spring-boot-starter-test` escopo `test`

---
### Necessidades de banco de dados:
- pedido: banco relacional
- busca por descrição: elasticsearch ou opensearch
- mensagem entre serviços: apache kafka ou rabbitmq