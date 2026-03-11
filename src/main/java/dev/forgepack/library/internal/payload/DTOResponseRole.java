package dev.forgepack.library.internal.payload;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Response DTO for Role entity
 *
 * @author	Marcelo Ribeiro Gadelha
 * Website:	www.forgepack.dev
 **/

public class DTOResponseRole extends RepresentationModel<DTOResponseRole> {

    private UUID id;
    private String name;
    private Set<DTOResponsePrivilege> privilege = new HashSet<>();

    public DTOResponseRole(UUID id, String name, Set<DTOResponsePrivilege> privilege) {
        this.id = id;
        this.name = name;
        this.privilege = privilege;
    }
    public DTOResponseRole(Link initialLink, UUID id, String name, Set<DTOResponsePrivilege> privilege) {
        super(initialLink);
        this.id = id;
        this.name = name;
        this.privilege = privilege;
    }
    public DTOResponseRole(Iterable<Link> initialLinks, UUID id, String name, Set<DTOResponsePrivilege> privilege) {
        super(initialLinks);
        this.id = id;
        this.name = name;
        this.privilege = privilege;
    }

    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Set<DTOResponsePrivilege> getDTOResponsePrivilege() {
        return privilege;
    }
}
