package com.epam.converter;

public interface DtoConverter<Entity, Dto> {

    Dto fromEntityToDto(Entity entity);

    Entity fromDtoToEntity(Dto dto);
}
