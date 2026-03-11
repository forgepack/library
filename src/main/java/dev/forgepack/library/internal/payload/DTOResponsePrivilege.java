package dev.forgepack.library.internal.payload;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import java.util.UUID;

/**
 * Response DTO for Privilege entity
 *
 * @author	Marcelo Ribeiro Gadelha
 * Website:	www.forgepack.dev
 **/

public class DTOResponsePrivilege extends RepresentationModel<DTOResponsePrivilege> {

    private UUID id;
    private String name;

    public DTOResponsePrivilege(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
    public DTOResponsePrivilege(Link initialLink, UUID id, String name) {
        super(initialLink);
        this.id = id;
        this.name = name;
    }
    public DTOResponsePrivilege(Iterable<Link> initialLinks, UUID id, String name) {
        super(initialLinks);
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
