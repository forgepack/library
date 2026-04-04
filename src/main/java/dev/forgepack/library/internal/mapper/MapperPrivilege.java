package dev.forgepack.library.internal.mapper;

import dev.forgepack.library.api.mapper.Mapper;
import dev.forgepack.library.internal.model.Privilege;
import dev.forgepack.library.internal.payload.DTORequestPrivilege;
import dev.forgepack.library.internal.payload.DTOResponsePrivilege;
import dev.forgepack.library.internal.utils.Information;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public final class MapperPrivilege implements Mapper<Privilege, DTORequestPrivilege, DTOResponsePrivilege> {

    private static final Logger log = LoggerFactory.getLogger(Information.class);
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
        log.info(dto == null ? "DTO: sim" : "DTO: não" );
        log.info(dto == null ? "Entity: sim" : "Entity: não" );
        if (dto == null || entity == null) return;
        log.info("0 DTO: " + dto.toString());
        log.info("0 Entity: " + entity.toString());
        entity.setName(dto.name());
        log.info("1 DTO: " + dto.toString());
        log.info("1 Entity: " + entity.toString());
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
