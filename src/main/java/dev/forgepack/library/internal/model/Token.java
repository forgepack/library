package dev.forgepack.library.internal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.envers.Audited;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * Website	www.gadelha.eti.br
 **/

@Entity
@Audited
@Table
public class Token extends GenericAuditEntity {

    private UUID refreshToken;
    private boolean active;

    public Token() {
    }
    public Token(UUID refreshToken) {
        this.refreshToken = refreshToken;
    }
    public Token(UUID refreshToken, boolean active) {
        this.refreshToken = refreshToken;
        this.active = active;
    }

    public void setRefreshToken(UUID refreshToken) {
        this.refreshToken = refreshToken;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public UUID getRefreshToken() {
        return refreshToken;
    }
    public boolean isActive() {
        return active;
    }
}
