package dev.forgepack.library.internal.service;

import dev.forgepack.library.api.mapper.Mapper;
import dev.forgepack.library.api.payload.DTORequestIdentifiable;
import dev.forgepack.library.api.repository.Repository;
import dev.forgepack.library.api.service.ServiceInterface;
import dev.forgepack.library.internal.model.GenericAuditEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;
import static org.springframework.data.domain.ExampleMatcher.matching;

@Service
public abstract class ServiceGeneric<Entity extends GenericAuditEntity, DTORequest extends DTORequestIdentifiable, DTOResponse extends RepresentationModel<DTOResponse>> implements ServiceInterface<Entity, DTORequest, DTOResponse> {

    private final Class<Entity> entityClass;
    private final Repository<Entity> repository;
    private final Mapper<Entity, DTORequest, DTOResponse> mapper;

    public ServiceGeneric(Class<Entity> entityClass, Repository<Entity> repository, Mapper<Entity, DTORequest, DTOResponse> mapper) {
        this.entityClass = entityClass;
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public DTOResponse create(DTORequest created){
        return addHateoas(repository.save(mapper.toEntity(created)));
    }
    @Transactional
    public Page<DTOResponse> retrieve(Pageable pageable, String value, Class<Entity> entityClass) {
        String propertyName = pageable.getSort().stream()
                .findFirst()
                .map(Sort.Order::getProperty)
                .orElse("id");
//        log.debug("Retrieving {} with property: {}, value: {}", entityClass.getSimpleName(), propertyName, value);
        if ("id".equalsIgnoreCase(propertyName) && StringUtils.hasText(value)) {
            try {
                return repository.findById(UUID.fromString(value), pageable)
                        .map(this::addHateoas);
            } catch (IllegalArgumentException e){
//                log.debug("Value '{}' is not a valid UUID, falling back to property search", value);
            }
        }
        try {
            Entity object = entityClass.getDeclaredConstructor().newInstance();
            ExampleMatcher exampleMatcher = matching()
                    .withIgnoreNullValues()
                    .withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
            Field field = ReflectionUtils.findField(entityClass, propertyName);
            String setterName = "set" + StringUtils.capitalize(propertyName);
            Method setter = object.getClass().getDeclaredMethod(setterName, field.getType());
            Object convertedValue = ConvertUtils.convert(value, field.getType());
            setter.invoke(object, convertedValue);
            Example<Entity> example = Example.of(object, exampleMatcher);
            return repository.findAll(example, pageable).map(this::addHateoas);
        } catch (Exception exception) {
//            log.warn("Error searching {} by {}: {}", entityClass.getSimpleName(), propertyName, exception.getMessage());
            return repository.findAll(pageable).map(this::addHateoas);
        }
    }
    @Transactional
    public DTOResponse retrieve(UUID id){
//        log.debug("Retrieving {} with ID: {}", entityClass.getSimpleName(), id);
        Entity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("%s not found with ID: %s", entityClass.getSimpleName(), id)));
        return addHateoas(entity);
    }
    @Transactional
    public DTOResponse update(DTORequest updated){
//        log.info("{} updating entity with ID: {}", information.getCurrentUser().orElse("Unknown User"), updated.id());
        Entity entity = repository.save(mapper.toEntity(updated));
        return addHateoas(entity);
    }
    @Transactional
    public DTOResponse delete(UUID id){
//        log.info("{} deleting entity with ID: {}", information.getCurrentUser().orElse("Unknown User"), id);
        Entity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Cannot delete: %s not found with ID: %s", entityClass.getSimpleName(), id)));
        repository.delete(entity);
        return addHateoas(entity);
    }
    @Transactional
    public DTOResponse addHateoas(Entity object) {
        String entityName = Character.toLowerCase(entityClass.getSimpleName().charAt(0))
                + entityClass.getSimpleName().substring(1);
        String selfUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .pathSegment(entityName, String.valueOf(object.getId()))
                .toUriString();
        return mapper.toResponse(object).add(Link.of(selfUri, IanaLinkRelations.SELF));
    }
}
