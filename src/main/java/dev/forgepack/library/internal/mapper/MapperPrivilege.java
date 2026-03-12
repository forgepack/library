package dev.forgepack.library.internal.mapper;

import dev.forgepack.library.api.mapper.Mapper;
import dev.forgepack.library.internal.model.Privilege;
import dev.forgepack.library.internal.payload.DTORequestPrivilege;
import dev.forgepack.library.internal.payload.DTOResponsePrivilege;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public final class MapperPrivilege implements Mapper<Privilege, DTORequestPrivilege, DTOResponsePrivilege> {

    private MapperPrivilege() {}

    @Override
    public Privilege toEntity(DTORequestPrivilege dto) {
        if (dto == null) return null;
        return new Privilege(dto.name());
    }

    @Override
    public DTOResponsePrivilege toResponse(Privilege entity) {
        if (entity == null) return null;
        return new DTOResponsePrivilege(
                entity.getId(),
                entity.getName()
        );
    }

    @Override
    public void updateEntity(DTORequestPrivilege dto, Privilege entity) {
        if (dto == null || entity == null) return;
        entity.setName(dto.name());
    }

    public Set<DTOResponsePrivilege> toResponseSet(Set<Privilege> entities) {
        if (entities == null) return Set.of();
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toSet());
    }

    public Set<Privilege> toEntitySet(Set<DTOResponsePrivilege> dtos) {
        if (dtos == null) return Set.of();
        return dtos.stream()
                .map(dto -> {
                    Privilege privilege = new Privilege();
                    privilege.setName(dto.getName());
                    return privilege;
                })
                .collect(Collectors.toSet());
    }
}
