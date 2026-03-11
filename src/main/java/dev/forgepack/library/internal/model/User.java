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
import jakarta.persistence.Column;
import org.hibernate.envers.Audited;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidade que representa um usuário do sistema.
 * <p>
 * Esta entidade armazena informações básicas do usuário incluindo credenciais,
 * status de atividade, controle de tentativas de login e associações com roles.
 * 
 * Características:
 * <ul>
 *     <li>Username único e obrigatório</li>
 *     <li>Email opcional</li>
 *     <li>Controle de tentativas de login com falhadas</li>
 *     <li>Flag de ativação/desativação</li>
 *     <li>Secret para autenticação 2FA (opcional)</li>
 *     <li>Associação many-to-many com roles</li>
 *     <li>Auditoria completa herdada de GenericAuditEntity</li>
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
@Table(name = "users", indexes = @Index(columnList = "username"), uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
public class User extends GenericAuditEntity {

    @NotNull(message = "{not.null}") @NotBlank(message = "{not.blank}")
    private String username;
    private String email;
    private String password;
    @Column(columnDefinition = "integer default 0")
    private Integer attempt;
    private Boolean active;
    private String secret;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> role = new HashSet<>();

    public User() {
    }
    public User(String username, String email, String password, Integer attempt, Boolean active, String secret, Set<Role> role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.attempt = attempt;
        this.active = active;
        this.secret = secret;
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
    public void setRole(Set<Role> role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public Integer getAttempt() {
        return attempt;
    }
    public Boolean getActive() {
        return active;
    }
    public String getSecret() {
        return secret;
    }
    public Set<Role> getRole() {
        return role;
    }
}
