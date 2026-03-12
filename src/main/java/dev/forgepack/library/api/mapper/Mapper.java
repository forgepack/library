package dev.forgepack.library.api.mapper;

/**
 * Interface Repository
 *
 * @author	Marcelo Ribeiro Gadelha
 * Website:	www.forgepack.dev
 **/

public interface Mapper<Entity, Request, Response> {
    public Entity toEntity(Request dto);
    public Response toResponse(Entity entity);
    public void updateEntity(Request dto, Entity entity);
}
