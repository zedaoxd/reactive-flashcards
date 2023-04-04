package br.com.bruno.reactiveFlashcards.domain.mapper;

import br.com.bruno.reactiveFlashcards.domain.document.Card;
import br.com.bruno.reactiveFlashcards.domain.document.DeckDocument;
import br.com.bruno.reactiveFlashcards.domain.dto.CardDTO;
import br.com.bruno.reactiveFlashcards.domain.dto.DeckDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;

@Mapper(injectionStrategy = CONSTRUCTOR, componentModel = "spring" )
public interface DeckDomainMapper {

    @Mapping(target = "description", source = "info")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DeckDocument toDocument(final DeckDTO dto);

    @Mapping(target = "back", source = "answer")
    @Mapping(target = "front", source = "ask")
    Card toDocument(final CardDTO dto);

}