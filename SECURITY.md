# _Security Overview_

The security in this Forgepack Projects is managed by Spring Security and follows a __Stateless__ authentication model based on __JWT (JSON Web Tokens)__.

## 1. AUTHENTICATION (JWT)

*   __Fluxo:__ The user sends credentials to the authentication endpoint. If successful, a JWT is generated and returned.
*   __Uso:__ The client must include the JWT in the header of all subsequent requests. `Authorization: Bearer <token>`.

## 2. AUTHORIZATION (Access Control)

Access control is implemented at two levels:

1.  __Nível de Endpoint:__ Configured in `SecurityConfig` for specific routes..
2.  __Nível de Método:__ Use the `@PreAuthorize` annotation on the `ControllerGeneric` to check the user's *Roles* (`ADMIN`, `MODERATOR`, `USER`) and *Authorities* (`user:create`, `user:retrieve`, etc.) before executing the method.

## 3. ADDITIONAL PROTECTION

*   __Rate Limiting:__ Implemented to protect against brute-force and overload attacks.
*   __CSRF:__ Enabled for requests that are not API requests (e.g., Thymeleaf forms).
*   __Security Headers:__ Configuring HSTS, X-Content-Type-Options, and X-Frame-Options to mitigate common attacks.