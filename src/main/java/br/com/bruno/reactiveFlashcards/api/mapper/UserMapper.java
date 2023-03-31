package br.com.bruno.reactiveFlashcards.api.mapper;

import br.com.bruno.reactiveFlashcards.api.controller.request.UserRequest;
import br.com.bruno.reactiveFlashcards.api.controller.response.UserResponse;
import br.com.bruno.reactiveFlashcards.domain.document.UserDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserDocument toDocument(final UserRequest userRequest);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserDocument toDocument(final UserRequest userRequest, final String id);

    UserResponse toResponse(final UserDocument userDocument);
}
