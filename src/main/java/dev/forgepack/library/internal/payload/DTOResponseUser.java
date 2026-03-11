package dev.forgepack.library.internal.payload;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;
import java.util.UUID;

/**
 * Response DTO for User entity
 *
 * @author	Marcelo Ribeiro Gadelha
 * Website:	www.forgepack.dev
 **/

public class DTOResponseUser extends RepresentationModel<DTOResponseUser> {

    private UUID id;
    private String username;
    private String email;
    private Integer attempt;
    private Boolean active;
    private Set<DTOResponseRole> role;

    public DTOResponseUser(UUID id, String username, String email, Integer attempt, Boolean active, Set<DTOResponseRole> role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.attempt = attempt;
        this.active = active;
        this.role = role;
    }
    public DTOResponseUser(Link initialLink, UUID id, String username, String email, Integer attempt, Boolean active, Set<DTOResponseRole> role) {
        super(initialLink);
        this.id = id;
        this.username = username;
        this.email = email;
        this.attempt = attempt;
        this.active = active;
        this.role = role;
    }
    public DTOResponseUser(Iterable<Link> initialLinks, UUID id, String username, String email, Integer attempt, Boolean active, Set<DTOResponseRole> role) {
        super(initialLinks);
        this.id = id;
        this.username = username;
        this.email = email;
        this.attempt = attempt;
        this.active = active;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public Integer getAttempt() {
        return attempt;
    }
    public Boolean getActive() {
        return active;
    }
    public Set<DTOResponseRole> getRole() {
        return role;
    }
}
