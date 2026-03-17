package dev.forgepack.library.internal.payload;

import dev.forgepack.library.api.payload.DTORequestIdentifiable;
import dev.forgepack.library.api.annotation.HasLength;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import java.util.Set;
import java.util.UUID;

/**
 * DTO para requisições envolvendo dados de usuário.
 * <p>
 * Este record representa os dados necessários para criação e atualização de usuários,
 * incluindo validações de integridade e unicidade.
 * </p>
 *
 * <h3>Validações aplicadas:</h3>
 * <ul>
 *     <li>Username: obrigatório, não em branco, com comprimento mínimo</li>
 *     <li>Email: formato válido, tamanho máximo de 50 caracteres, único no sistema</li>
 *     <li>Roles: conjunto opcional de roles associadas</li>
 * </ul>
 * 
 * @param id identificador único do usuário (opcional para criação)
 * @param username nome de usuário único e obrigatório
 * @param email endereço de email válido e único
 * @param role conjunto de roles associadas ao usuário
 * 
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 * Website: www.forgepack.dev
 * 
 * @see DTORequestIdentifiable
 * @see DTORequestRole
 */

//@Unique(service = ServiceUser.class, field = "email")
//@Unique(service = ServiceUser.class, field = "username")
public record DTORequestUser (

    UUID id,
    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}") @HasLength
    String username,
    @NotBlank(message = "{not.blank}") @Size(max = 50) @Email
    String email,

    Set<DTOResponseRole> role
) implements DTORequestIdentifiable {}
