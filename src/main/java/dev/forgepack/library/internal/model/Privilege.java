package dev.forgepack.library.internal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.envers.Audited;

/**
 * Entidade que representa um privilégio/permissão específica no sistema.
 * <p>
 * Esta entidade define as permissões granulares que podem ser atribuídas
 * a roles e, consequentemente, aos usuários que possuem essas roles.
 * 
 * Características:
 * <ul>
 *     <li>Nome único e obrigatório</li>
 *     <li>Auditoria completa herdada de GenericAuditEntity</li>
 *     <li>Indexação por nome para performance</li>
 *     <li>Integração com sistema de roles</li>
 * </ul>
 * 
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 * 
 * @see GenericAuditEntity
 * @see Role
 */

@Entity
@Audited
@Table(indexes = @Index(columnList = "name"), uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Privilege extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;

    public Privilege() {
    }
    public Privilege(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
