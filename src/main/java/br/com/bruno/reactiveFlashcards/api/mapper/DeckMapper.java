package br.com.bruno.reactiveFlashcards.api.mapper;

import br.com.bruno.reactiveFlashcards.api.controller.request.DeckRequest;
import br.com.bruno.reactiveFlashcards.api.controller.response.DeckResponse;
import br.com.bruno.reactiveFlashcards.domain.document.DeckDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeckMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DeckDocument toDocument(final DeckRequest deckRequest);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DeckDocument toDocument(final DeckRequest deckRequest, final String id);

    DeckResponse toResponse(final DeckDocument deckDocument);
}
