package dev.forgepack.library.internal.service;

import dev.forgepack.library.internal.model.Privilege;
import dev.forgepack.library.internal.model.Role;
import dev.forgepack.library.internal.model.User;
import dev.forgepack.library.internal.repository.RepositoryUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class ServiceCustomUserDetails implements UserDetailsService {

    private final RepositoryUser repositoryUser;

    public ServiceCustomUserDetails(RepositoryUser repositoryUser) {
        this.repositoryUser = repositoryUser;
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = repositoryUser.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Resource not found"));
        return new org.springframework.security.core.userdetails.User(Objects.requireNonNull(user).getUsername(), user.getPassword(), getAuthorities(user.getRole()));
    }
    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> role) {
        return getGrantedAuthorities(getPrivilege(role));
    }
    private Set<GrantedAuthority> getGrantedAuthorities(Set<String> privileges) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
    private Set<String> getPrivilege(Collection<Role> roles) {
        Set<String> permissions = new HashSet<>();
        Set<Privilege> collection = new HashSet<>();
        for (Role role : roles) {
            permissions.add(role.getName());
            collection.addAll(role.getPrivilege());
        }
        for (Privilege item : collection) {
            permissions.add(item.getName());
        }
        return permissions;
    }
}
