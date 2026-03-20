package dev.forgepack.library.internal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.envers.Audited;

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
    @Column(name = "user_id")
    private User user;

    public Log() {
    }
    public Log(String action, User user) {
        this.action = action;
        this.user = user;
    }

    public void setAction(String action) {
        this.action = action;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }
    public User getUser() {
        return user;
    }
}
