package dev.forgepack.library.internal.model;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.UUID;

/**
 * Base superclass for persistent entities providing auditing and soft delete support.
 *
 * <p>This class defines a standardized set of cross-cutting concerns for domain entities,
 * including identity management, automatic auditing, and logical deletion. It is designed
 * as a {@link MappedSuperclass}, allowing subclasses to inherit mappings without requiring
 * a dedicated table.</p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *     <li>Unique identification using {@link UUID}</li>
 *     <li>Automatic temporal auditing (creation and last update timestamps)</li>
 *     <li>Author tracking (created by / modified by)</li>
 *     <li>Logical deletion (soft delete)</li>
 *     <li>Compatibility with Hibernate Envers auditing via {@link Audited}</li>
 * </ul>
 *
 * <h2>Auditing</h2>
 * <p>Auditing is integrated with Spring Data JPA through
 * {@link org.springframework.data.jpa.domain.support.AuditingEntityListener}.
 * An {@code AuditorAware} implementation must be configured in the application
 * context to automatically populate {@code createdBy} and {@code modifiedBy}.</p>
 *
 * <h2>Soft Delete</h2>
 * <p>Logical deletion is represented by the {@code deletedAt} field. When non-null,
 * the entity is considered deleted.</p>
 *
 * <h2>Equality</h2>
 * <p>The {@code equals} and {@code hashCode} implementations rely exclusively on the
 * entity identifier ({@code id}), assuming stable identity after persistence.</p>
 *
 * <h2>Considerations</h2>
 * <ul>
 *     <li>Avoid accessing {@code LAZY} associations outside of a transactional context</li>
 *     <li>Using {@code UUID} improves portability and supports distributed systems</li>
 *     <li>Soft delete does not enforce referential integrity at the database level</li>
 * </ul>
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 */
@Audited
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class GenericAuditEntity extends GenericBaseEntity {

    /**
     * User responsible for creating the entity.
     *
     * <p>Automatically populated via {@link CreatedBy}, depending on the configured
     * {@code AuditorAware} implementation.</p>
     */
    @CreatedBy
    @JoinColumn(updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private User createdBy;
    /**
     * User responsible for the last modification of the entity.
     *
     * <p>Automatically updated via {@link LastModifiedBy}.</p>
     */
    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private User modifiedBy;

    public User getCreatedBy() {
        return createdBy;
    }
    public User getModifiedBy() {
        return modifiedBy;
    }
}
