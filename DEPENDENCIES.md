```
└─ [x] forgepack-authentication				0.0.0 (uma dependencia interna: forgepack-core, forgepack-security, forgepack-authorization)
	└─ [x] forgepack-auditor				0.0.1 (dependencia de: forgepack-authorization)
		└─ [x] forgepack-authorization 			0.0.2 (depende de: forgepack-core, forgepack-validation, forgepack-security)
			└─ [x] forgepack-security 			0.0.2 (depende de: forgepack-core)
				└─ [x] forgepack-core			0.0.6 (duas dependencias internas: forgepack-validation, forgepack-utils)
					
					└─ [x] forgepack-validation	0.0.6 (somente dependencia externa: spring-boot-starter-validation)
					└─ [x] forgepack-utils		0.0.4 (somente dependencias externas: spring-boot-starter-mail, core de com.google.zxing)
```
