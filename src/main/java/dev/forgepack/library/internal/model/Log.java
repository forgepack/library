package dev.forgepack.library.internal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.envers.Audited;

import java.util.Optional;

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
    private Optional<User> user;

    public Log() {
    }
    public Log(String action, Optional<User> user) {
        this.action = action;
        this.user = user;
    }

    public void setAction(String action) {
        this.action = action;
    }
    public void setUser(Optional<User> user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }
    public Optional<User> getUser() {
        return user;
    }
}
