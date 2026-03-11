package dev.forgepack.library.api.payload;

import java.util.UUID;

/**
 * Interface marcadora para DTOs de requisição que possuem identificação.
 * <p>
 * Esta interface deve ser implementada por todos os DTOs de requisição que
 * representam entidades identificáveis por um UUID. Facilita o processamento
 * genérico de requisições que necessitam acessar o identificador da entidade.
 * 
 * Casos de uso:
 * <ul>
 *     <li>Operações de atualização que precisam do ID da entidade</li>
 *     <li>Validações de unicidade que excluem o próprio registro</li>
 *     <li>Processamento genérico de DTOs com identificação</li>
 * </ul>
 * 
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 * 
 * @see java.util.UUID
 */

public interface DTORequestIdentifiable {

    UUID id();
}
