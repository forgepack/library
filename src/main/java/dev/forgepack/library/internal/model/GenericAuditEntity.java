package dev.forgepack.library.internal.model;

import jakarta.persistence.*;

import jakarta.persistence.CascadeType;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade base abstrata com funcionalidades de auditoria e soft delete.
 * <p>
 * Esta classe fornece campos comuns para auditoria (criação, atualização, exclusão lógica)
 * e deve ser estendida por todas as entidades que necessitam de rastreamento de alterações.
 * Implementa soft delete através da anotação {@code @SQLDelete} e filtro {@code deletedFilter}.
 * 
 * Funcionalidades:
 * <ul>
 *     <li>ID único baseado em UUID</li>
 *     <li>Timestamp automático de criação e atualização</li>
 *     <li>Rastreamento de usuário criador e modificador</li>
 *     <li>Soft delete com campo deleted_at</li>
 *     <li>Filtro automático para exclusão lógica</li>
 * </ul>
 * 
 * @author Marcelo Ribeiro Gadelha
 * @version 1.0
 * @since 1.0
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class GenericAuditEntity implements Serializable {

    /**
     * Identificador único da entidade.
     * <p>
     * Utiliza UUID para garantir unicidade global e evitar conflitos
     * em ambientes distribuídos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;
    
    /**
     * Data e hora de criação do registro.
     * <p>
     * Preenchido automaticamente na persistência através da anotação
     * {@code @CreationTimestamp}.
     */
    @CreationTimestamp @Column(updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * Data e hora da última atualização do registro.
     * <p>
     * Atualizado automaticamente a cada modificação através da anotação
     * {@code @UpdateTimestamp}.
     */
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    /**
     * Usuário responsável pela criação do registro.
     * <p>
     * Preenchido automaticamente através da anotação {@code @CreatedBy}
     * e configuração de auditoria do Spring Data.
     */
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private User createdBy;
    
    /**
     * Usuário responsável pela última modificação do registro.
     * <p>
     * Atualizado automaticamente através da anotação {@code @LastModifiedBy}
     * e configuração de auditoria do Spring Data.
     */
    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private User modifiedBy;
    
    /**
     * Data e hora da exclusão lógica do registro.
     * <p>
     * Quando preenchido, indica que o registro foi "excluído" logicamente.
     * O filtro {@code deletedFilter} oculta automaticamente registros com
     * este campo preenchido nas consultas.
     */
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public void setId(UUID id) {
        this.id = id;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
    public void setModifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public UUID getId() {
        return id;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public User getCreatedBy() {
        return createdBy;
    }
    public User getModifiedBy() {
        return modifiedBy;
    }
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenericAuditEntity that)) return false;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
