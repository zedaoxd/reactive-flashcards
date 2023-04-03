package br.com.bruno.reactiveFlashcards.domain.mapper;

import br.com.bruno.reactiveFlashcards.domain.document.Card;
import br.com.bruno.reactiveFlashcards.domain.document.Question;
import br.com.bruno.reactiveFlashcards.domain.document.StudyCard;
import br.com.bruno.reactiveFlashcards.domain.document.StudyDocument;
import br.com.bruno.reactiveFlashcards.domain.dto.QuestionDTO;
import br.com.bruno.reactiveFlashcards.domain.dto.StudyCardDTO;
import br.com.bruno.reactiveFlashcards.domain.dto.StudyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface StudyDomainMapper {

    StudyCard toStudyCard(Card cards);

    default Question generateRandomQuestion(Set<StudyCard> cards) {
        var values = new ArrayList<>(cards);
        var random = new Random();
        var index  = random.nextInt(values.size());
        return toQuestion(values.get(index));
    }

    @Mapping(target = "asked", source = "front")
    @Mapping(target = "expected", source = "back")
    @Mapping(target = "answered", ignore = true)
    @Mapping(target = "answeredIn", ignore = true)
    Question toQuestion(StudyCard card);

    @Mapping(target = "asked", source = "front")
    @Mapping(target = "expected", source = "back")
    @Mapping(target = "answered", ignore = true)
    @Mapping(target = "answeredIn", ignore = true)
    QuestionDTO toQuestion(StudyCardDTO card);

    default StudyDocument answer(StudyDocument document, String answer) {
        var currentQuestion     = document.getLastPendingQuestion();
        var questions           = document.questions();
        var curIndexQuestion    = questions.indexOf(currentQuestion);
        currentQuestion = currentQuestion.toBuilder().answered(answer).build();
        questions.set(curIndexQuestion, currentQuestion);
        return document.toBuilder().questions(questions).build();
    }

    StudyDTO toDTO(StudyDocument document, List<String> remainAsks);

    StudyDocument toDocument(StudyDTO studyDTO);
}
