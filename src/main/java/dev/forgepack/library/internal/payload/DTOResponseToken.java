package dev.forgepack.library.internal.payload;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import java.util.Set;
import java.util.UUID;

/**
 * @author	Marcelo Ribeiro Gadelha
 * Website:	www.gadelha.eti.br
 **/

public class DTOResponseToken extends RepresentationModel<DTOResponseToken> {

    private final String tokenType = "Bearer ";
    private String accessToken;
    private UUID refreshToken;
    private Set<String> role;

    public DTOResponseToken(String accessToken, UUID refreshToken, Set<String> role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.role = role;
    }
    public DTOResponseToken(Link initialLink, String accessToken, UUID refreshToken, Set<String> role) {
        super(initialLink);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.role = role;
    }
    public DTOResponseToken(Iterable<Link> initialLinks, String accessToken, UUID refreshToken, Set<String> role) {
        super(initialLinks);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.role = role;
    }

    public String getTokenType() {
        return tokenType;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public UUID getRefreshToken() {
        return refreshToken;
    }
    public Set<String> getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "DTOResponseToken{" +
                "tokenType='" + tokenType + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken=" + refreshToken +
                ", role=" + role +
                '}';
    }
}
