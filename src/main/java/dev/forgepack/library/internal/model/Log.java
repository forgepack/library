package dev.forgepack.library.internal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.envers.Audited;

import java.util.UUID;

/**
 * Implements Log Repository
 *
 * @author	Marcelo Ribeiro Gadelha
 * Website:	www.forgepack.dev
 **/
@Entity
@Audited
@Table
public class Log extends GenericAuditEntity {

    private String action;
    private UUID entityId;
    private String entityName;
    @Column(name = "user_id")
    private User user;

    public Log() {
    }
    public Log(String action, UUID entityId, String entityName, User user) {
        this.action = action;
        this.entityId = entityId;
        this.entityName = entityName;
        this.user = user;
    }

    public void setAction(String action) {
        this.action = action;
    }
    public void setEntityId(UUID entityId) {
        this.entityId = entityId;
    }
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }
    public UUID getEntityId() {
        return entityId;
    }
    public String getEntityName() {
        return entityName;
    }
    public User getUser() {
        return user;
    }
}
