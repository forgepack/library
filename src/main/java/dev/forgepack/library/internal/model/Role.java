package dev.forgepack.library.internal.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import org.hibernate.envers.Audited;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidade que representa um papel/função no sistema de autorização.
 * <p>
 * Esta entidade define os diferentes papéis que os usuários podem assumir,
 * agrupando privilégios relacionados para facilitar o gerenciamento de permissões.
 * 
 * Características:
 * <ul>
 *     <li>Nome único e obrigatório</li>
 *     <li>Associação many-to-many com privilégios</li>
 *     <li>Auditoria completa herdada de GenericAuditEntity</li>
 *     <li>Indexação por nome para performance</li>
 * </ul>
 * 
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 * 
 * @see GenericAuditEntity
 * @see Privilege
 * @see User
 */

@Entity
@Audited
@Table(indexes = @Index(columnList = "name"), uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Role extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.PERSIST)
    @JoinTable(name = "role_privileges", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Set<Privilege> privilege = new HashSet<>();

    public Role() {
    }
    public Role(String name, Set<Privilege> privilege) {
        this.name = name;
        this.privilege = privilege;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPrivilege(Set<Privilege> privilege) {
        this.privilege = privilege;
    }

    public String getName() {
        return name;
    }
    public Set<Privilege> getPrivilege() {
        return privilege;
    }
}
