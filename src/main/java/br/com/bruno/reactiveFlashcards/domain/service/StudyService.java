package br.com.bruno.reactiveFlashcards.domain.service;

import br.com.bruno.reactiveFlashcards.domain.document.Card;
import br.com.bruno.reactiveFlashcards.domain.document.Question;
import br.com.bruno.reactiveFlashcards.domain.document.StudyCard;
import br.com.bruno.reactiveFlashcards.domain.document.StudyDocument;
import br.com.bruno.reactiveFlashcards.domain.dto.QuestionDTO;
import br.com.bruno.reactiveFlashcards.domain.dto.StudyDTO;
import br.com.bruno.reactiveFlashcards.domain.exception.DeckInStudyException;
import br.com.bruno.reactiveFlashcards.domain.exception.NotFoundException;
import br.com.bruno.reactiveFlashcards.domain.mapper.StudyDomainMapper;
import br.com.bruno.reactiveFlashcards.domain.repository.StudyRepository;
import br.com.bruno.reactiveFlashcards.domain.service.query.DeckQueryService;
import br.com.bruno.reactiveFlashcards.domain.service.query.StudyQueryService;
import br.com.bruno.reactiveFlashcards.domain.service.query.UserQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static br.com.bruno.reactiveFlashcards.domain.exception.BaseErrorMessage.DECK_IN_STUDY;
import static br.com.bruno.reactiveFlashcards.domain.exception.BaseErrorMessage.STUDY_QUESTION_NOT_FOUND;

@Service
@Slf4j
@AllArgsConstructor
public class StudyService {

    private final DeckQueryService deckQueryService;
    private final UserQueryService userQueryService;
    private final StudyQueryService studyQueryService;
    private final StudyRepository studyRepository;
    private final StudyDomainMapper studyDomainMapper;

    public Mono<StudyDocument> start(StudyDocument document) {
        return verifyStudy(document).then(userQueryService.findById(document.userId()))
                .flatMap(user -> deckQueryService.findById(document.studyDeck().deckId())
                .flatMap(deck -> fillDeckStudyCards(document, deck.cards())))
                .map(study -> study.toBuilder()
                        .question(studyDomainMapper.generateRandomQuestion(study.studyDeck().cards()))
                        .build())
                .doFirst(() -> log.info("Starting study: {}", document))
                .flatMap(studyRepository::save)
                .doOnSuccess(study -> log.info("Save study: {}", study));
    }

    private Mono<StudyDocument> fillDeckStudyCards(StudyDocument document, Set<Card> cards) {
        return Flux.fromIterable(cards)
                .doFirst(() -> log.info("Getting cards for study: {}", document))
                .map(studyDomainMapper::toStudyCard)
                .collectList()
                .map(studyCards -> document.studyDeck().toBuilder().cards(Set.copyOf(studyCards)).build())
                .map(studyDeck -> document.toBuilder().studyDeck(studyDeck).build());
    }

    private Mono<Void> verifyStudy(StudyDocument document) {
        return studyQueryService.findPendingStudyByUserIdAndDeckId(document.userId(), document.studyDeck().deckId())
                .flatMap(study -> Mono.defer(() -> Mono.error(new DeckInStudyException(DECK_IN_STUDY
                        .params(document.userId(), document.studyDeck().deckId()).getMessage()))))
                .onErrorResume(NotFoundException.class, e -> Mono.empty())
                .then();
    }

    public Mono<StudyDocument> answer(String id, String answer) {
        return studyQueryService.findById(id)
                .flatMap(studyQueryService::verifyIfFinished)
                .map(study -> studyDomainMapper.answer(study, answer))
                .zipWhen(this::getNextPossibilities)
                .map(tuple -> studyDomainMapper.toDTO(tuple.getT1(), tuple.getT2()))
                .flatMap(this::setNewQuestion)
                .map(studyDomainMapper::toDocument)
                .flatMap(studyRepository::save);
    }

    private Mono<List<String>> getNextPossibilities(StudyDocument document) {
        return Flux.fromIterable(document.studyDeck().cards())
                .map(StudyCard::front)
                .filter(asks -> document.questions().stream()
                        .filter(Question::isCorrect)
                        .map(Question::asked)
                        .noneMatch(q -> q.equals(asks)))
                .collectList()
                .flatMap(asks -> removeLastAsks(asks, document.getLastAnswerdQuestion().asked()));
    }

    private Mono<StudyDTO> setNewQuestion(StudyDTO dto) {
        return Mono.just(dto.hasAnyAnswered())
                .filter(BooleanUtils::isTrue)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(STUDY_QUESTION_NOT_FOUND
                        .params(dto.id())
                        .getMessage()))))
                .flatMap(hasAnyAnswered -> generateNextQuestion(dto))
                .map(question -> dto.toBuilder().question(question).build())
                .onErrorResume(NotFoundException.class, e -> Mono.just(dto));
    }

    private Mono<List<String>> removeLastAsks(List<String> asks, String asked) {
        return Mono.just(asks)
                .filter(asksList -> asksList.size() == 1)
                .switchIfEmpty(Mono.defer(() -> Mono.just(asks.stream()
                        .filter(a -> a.equals(asked))
                        .collect(Collectors.toList()))));
    }

    private Mono<QuestionDTO> generateNextQuestion(StudyDTO dto) {
       return Mono.just(dto.remainAsks().get(new Random().nextInt(dto.remainAsks().size())))
               .map(ask -> dto.studyDeck()
                       .cards()
                       .stream()
                       .filter(card -> card.front().equals(ask))
                       .map(studyDomainMapper::toQuestion)
                       .findFirst()
                       .orElseThrow());

    }

}
