package dev.forgepack.library.internal.service;

import dev.forgepack.library.api.mapper.Mapper;
import dev.forgepack.library.api.payload.DTORequestIdentifiable;
import dev.forgepack.library.api.repository.RepositoryInterface;
import dev.forgepack.library.api.service.ServiceInterface;
import dev.forgepack.library.internal.model.GenericAuditEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;
import static org.springframework.data.domain.ExampleMatcher.matching;

/**
 * Generic base service providing common CRUD operations for domain entities.
 *
 * <p>This abstract service implements the core business operations used by
 * application services, including creation, retrieval, update, deletion,
 * and paginated search.</p>
 *
 * <p>The class delegates persistence responsibilities to a
 * {@link RepositoryInterface} implementation and uses a {@link Mapper}
 * to convert between entity and DTO representations.</p>
 *
 * <p>Additionally, this service integrates with Spring HATEOAS to enrich
 * response DTOs with hypermedia links following the HATEOAS constraint.</p>
 *
 * <h3>Main responsibilities</h3>
 * <ul>
 *     <li>Provide reusable CRUD operations for entities</li>
 *     <li>Support paginated and filtered queries</li>
 *     <li>Convert entities to DTO representations</li>
 *     <li>Add HATEOAS self links to response models</li>
 * </ul>
 *
 * <h3>Search behavior</h3>
 * <p>The {@code retrieve(Pageable, String, Class)} method dynamically builds
 * search queries using Spring Data {@link Example} and {@link ExampleMatcher},
 * allowing case-insensitive and partial string matching.</p>
 *
 * @param <Entity> domain entity type extending {@link GenericAuditEntity}
 * @param <DTORequest> request DTO extending {@link DTORequestIdentifiable}, used for create and update operations
 * @param <DTOResponse> response DTO extending {@link RepresentationModel} , returned by service operations
 *
 * @author Marcelo Ribeiro Gadelha
 * @since 1.0
 * @see ServiceInterface
 * @see RepositoryInterface
 * @see Mapper
 * @see GenericAuditEntity
 * @see RepresentationModel
 */
public abstract class ServiceGeneric<Entity extends GenericAuditEntity, DTORequest extends DTORequestIdentifiable, DTOResponse extends RepresentationModel<DTOResponse>> implements ServiceInterface<Entity, DTORequest, DTOResponse> {

    private final Class<Entity> entityClass;
    private final RepositoryInterface<Entity> repositoryInterface;
    private final Mapper<Entity, DTORequest, DTOResponse> mapper;

    public ServiceGeneric(Class<Entity> entityClass, RepositoryInterface<Entity> repositoryInterface, Mapper<Entity, DTORequest, DTOResponse> mapper) {
        this.entityClass = entityClass;
        this.repositoryInterface = repositoryInterface;
        this.mapper = mapper;
    }

    /**
     * Creates and persists a new entity based on the provided {@link DTORequest}.
     *
     * <p>The {@link DTORequest} is converted into a domain entity using the configured
     * {@link Mapper}. The entity is then persisted through the
     * {@link RepositoryInterface}. After persistence, the entity is converted
     * back into a {@link DTOResponse} enriched with HATEOAS links.</p>
     *
     * @param created {@link DTORequest} containing the data required to create the entity
     * @return {@link DTOResponse} representing the persisted entity with HATEOAS links
     */
    @Transactional
    public DTOResponse create(DTORequest created){
//        log.info("{} creating a new resource", information.getCurrentUser().orElse("Unknown User"));
        return addHateoas(repositoryInterface.save(mapper.toEntity(created)));
    }

    /**
     * Retrieves entities using pagination and dynamic filtering.
     *
     * <p>The filtering behavior is determined by the property specified in the
     * {@link Pageable} sort configuration. If the sorted property is {@code id}
     * and the provided value is a valid {@link UUID}, the search will be performed
     * directly by identifier.</p>
     *
     * <p>Otherwise, the method dynamically builds a query using Spring Data
     * {@link Example} and {@link ExampleMatcher}, performing case-insensitive
     * and partial string matching.</p>
     *
     * <p>If the property cannot be resolved or an error occurs during query
     * construction, the method falls back to returning all entities using the
     * provided pagination.</p>
     *
     * @param pageable pagination and sorting configuration
     * @param value value used as search filter
     * @param entityClass class of the entity being queried
     * @return a paginated list of response DTOs with HATEOAS links
     */
    @Transactional
    public Page<DTOResponse> retrieve(Pageable pageable, String value, Class<Entity> entityClass) {
        String propertyName = pageable.getSort().stream()
                .findFirst()
                .map(Sort.Order::getProperty)
                .orElse("id");
//        log.debug("Retrieving {} with property: {}, value: {}", entityClass.getSimpleName(), propertyName, value);
        if ("id".equalsIgnoreCase(propertyName) && StringUtils.hasText(value)) {
            try {
                return repositoryInterface.findById(UUID.fromString(value), pageable)
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
            return repositoryInterface.findAll(example, pageable).map(this::addHateoas);
        } catch (Exception exception) {
//            log.warn("Error searching {} by {}: {}", entityClass.getSimpleName(), propertyName, exception.getMessage());
            return repositoryInterface.findAll(pageable).map(this::addHateoas);
        }
    }

    /**
     * Retrieves a single entity by its unique identifier.
     *
     * <p>If no entity exists with the specified identifier, an
     * {@link EntityNotFoundException} is thrown.</p>
     *
     * @param id {@link UUID} unique identifier of the entity
     * @return {@link DTOResponse} representing the found entity with HATEOAS links
     * @throws EntityNotFoundException if the entity does not exist
     */
    @Transactional
    public DTOResponse retrieve(UUID id){
//        log.debug("Retrieving {} with ID: {}", entityClass.getSimpleName(), id);
        Entity entity = repositoryInterface.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("%s not found with ID: %s", entityClass.getSimpleName(), id)));
        return addHateoas(entity);
    }

    /**
     * Updates an existing entity using the provided request DTO.
     *
     * <p>The method first verifies if an entity with the specified identifier
     * exists. If the entity does not exist, an {@link EntityNotFoundException}
     * is thrown.</p>
     *
     * <p>The DTO is converted to an entity using the configured {@link Mapper},
     * persisted, and then returned as a {@link DTOResponse} enriched with HATEOAS links.</p>
     *
     * @param updated {@link DTORequest} containing updated entity data
     * @return {@link DTOResponse} representing the updated entity
     * @throws EntityNotFoundException if the entity does not exist
     */
    @Transactional
    public DTOResponse update(DTORequest updated){
//        log.info("{} updating entity with ID: {}", information.getCurrentUser().orElse("Unknown User"), updated.id());
        repositoryInterface.findById(updated.id())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Cannot update: %s not found with ID: %s", entityClass.getSimpleName(), updated.id())));
        Entity entity = mapper.toEntity(updated);
        entity.setId(updated.id());
        return addHateoas(repositoryInterface.save(entity));
    }

    /**
     * Deletes an entity identified by the specified identifier.
     *
     * <p>If the entity does not exist, an {@link EntityNotFoundException}
     * is thrown.</p>
     *
     * @param id {@link UUID} unique identifier of the entity to be deleted
     * @return {@link DTOResponse} representing the deleted entity with HATEOAS links
     * @throws EntityNotFoundException if the entity does not exist
     */
    @Transactional
    public DTOResponse delete(UUID id){
//        log.info("{} deleting entity with ID: {}", information.getCurrentUser().orElse("Unknown User"), id);
        Entity entity = repositoryInterface.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Cannot delete: %s not found with ID: %s", entityClass.getSimpleName(), id)));
        repositoryInterface.delete(entity);
        return addHateoas(entity);
    }

    /**
     * Converts an entity to a {@link DTOResponse} and enriches it with a HATEOAS self link.
     *
     * <p>The self link is automatically generated using the entity name and
     * its identifier, following the pattern:</p>
     *
     * <pre>
     * /{entityName}/{id}
     * </pre>
     *
     * <p>The link is added using the {@link IanaLinkRelations#SELF} relation.</p>
     *
     * @param object entity instance
     * @return {@link DTOResponse} containing the entity data and HATEOAS self link
     */
    public DTOResponse addHateoas(Entity object) {
        String entityName = Character.toLowerCase(entityClass.getSimpleName().charAt(0))
                + entityClass.getSimpleName().substring(1);
        String selfUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .pathSegment(entityName, String.valueOf(object.getId()))
                .toUriString();
        return mapper.toResponse(object).add(Link.of(selfUri, IanaLinkRelations.SELF));
    }
}
