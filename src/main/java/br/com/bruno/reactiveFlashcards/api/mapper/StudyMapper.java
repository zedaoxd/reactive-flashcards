package br.com.bruno.reactiveFlashcards.api.mapper;

import br.com.bruno.reactiveFlashcards.api.controller.request.StudyRequest;
import br.com.bruno.reactiveFlashcards.api.controller.response.AnswerQuestionResponse;
import br.com.bruno.reactiveFlashcards.api.controller.response.QuestionResponse;
import br.com.bruno.reactiveFlashcards.domain.document.Question;
import br.com.bruno.reactiveFlashcards.domain.document.StudyDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudyMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "studyDeck.deckId", source = "deckId")
    @Mapping(target = "studyDeck.cards", ignore = true)
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "question", ignore = true)
    StudyDocument toDocument(final StudyRequest request);

    QuestionResponse toResponse(Question question, String id);

    AnswerQuestionResponse toResponse(Question question);
}
