package dev.forgepack.library.api.validator;

import java.util.UUID;

/**
 * Interface para implementação de verificação de unicidade de campos.
 * <p>
 * Esta interface define o contrato que os serviços devem implementar para
 * permitir a validação de unicidade de campos através da anotação {@code @Unique}.
 * É utilizada pelo {@link dev.forgepack.library.internal.validator.UniqueValidator}
 * para realizar verificações de unicidade em tempo de validação.
 * 
 * Casos de uso:
 * <ul>
 *     <li>Validação de unicidade em criação de entidades</li>
 *     <li>Validação de unicidade em atualização (excluindo o próprio registro)</li>
 *     <li>Verificação de campos únicos customizados</li>
 * </ul>
 * 
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 * 
 * @see dev.forgepack.library.api.annotation.Unique
 * @see dev.forgepack.library.internal.validator.UniqueValidator
 */

public interface UniqueCheckable {

    /**
     * Verifica se existe um registro com o valor especificado no campo informado.
     * 
     * @param field nome do campo a ser verificado
     * @param value valor a ser procurado
     * @return true se existe um registro com o valor especificado, false caso contrário
     */
    boolean existsByField(String field, Object value);
    
    /**
     * Verifica se existe um registro com o valor especificado no campo informado,
     * excluindo o registro com o ID fornecido.
     * <p>
     * Este método é utilizado durante atualizações para verificar se o novo valor
     * é único, excluindo o próprio registro que está sendo atualizado.
     * 
     * @param field nome do campo a ser verificado
     * @param value valor a ser procurado
     * @param id ID do registro a ser excluído da verificação
     * @return true se existe outro registro com o valor especificado, false caso contrário
     */
    boolean existsByFieldAndIdNot(String field, Object value, UUID id);
}
