package dev.forgepack.library.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

/**
 * Interface genérica para serviços CRUD.
 * <p>
 * Esta interface define o contrato padrão para serviços que realizam operações
 * CRUD (Create, Read, Update, Delete) com suporte a paginação e filtragem.
 * 
 * @param <Entity> tipo da entidade de domínio
 * @param <DTORequest> tipo do DTO de entrada (request)
 * @param <DTOResponse> tipo do DTO de saída (response)
 * 
 * Operações suportadas:
 * <ul>
 *     <li>Criação de novos registros</li>
 *     <li>Consulta paginada com filtros</li>
 *     <li>Consulta por ID específico</li>
 *     <li>Atualização de registros existentes</li>
 *     <li>Exclusão de registros</li>
 * </ul>
 * 
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 * 
 * @see org.springframework.data.domain.Page
 * @see org.springframework.data.domain.Pageable
 */

public interface ServiceInterface<Entity, DTORequest, DTOResponse> {
    
    /**
     * Cria um novo registro baseado nos dados fornecidos.
     * 
     * @param created DTO contendo os dados para criação
     * @return DTO de resposta con o registro criado
     */
    DTOResponse create(DTORequest created);
    
    /**
     * Recupera uma página de registros com filtragem opcional.
     * 
     * @param pageable informações de paginação (tamanho da página, ordenação, etc.)
     * @param value valor para filtrar os resultados (opcional)
     * @param entityClass classe da entidade para referência de tipo
     * @return página contendo os registros encontrados
     */
    Page<DTOResponse> retrieve(Pageable pageable, String value, Class<Entity> entityClass);
    
    /**
     * Recupera um registro específico pelo seu identificador.
     * 
     * @param id identificador único do registro
     * @return DTO de resposta com o registro encontrado
     * @throws jakarta.persistence.EntityNotFoundException se o registro não for encontrado
     */
    DTOResponse retrieve(UUID id, Class<Entity> entityClass);
    
    /**
     * Atualiza um registro existente com os novos dados fornecidos.
     * 
     * @param updated DTO contendo os novos dados
     * @return DTO de resposta com o registro atualizado
     * @throws jakarta.persistence.EntityNotFoundException se o registro não for encontrado
     */
    DTOResponse update(DTORequest updated);
    
    /**
     * Exclui um registro pelo seu identificador.
     * <p>
     * Dependendo da implementação, pode realizar exclusão física ou lógica (soft delete).
     * 
     * @param id identificador do registro a ser excluído
     * @return DTO de resposta com o registro excluído
     * @throws jakarta.persistence.EntityNotFoundException se o registro não for encontrado
     */
    DTOResponse delete(UUID id);
}
