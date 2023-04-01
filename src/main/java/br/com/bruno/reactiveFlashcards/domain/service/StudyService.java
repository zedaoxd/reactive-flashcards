package br.com.bruno.reactiveFlashcards.domain.service;

import br.com.bruno.reactiveFlashcards.domain.document.Card;
import br.com.bruno.reactiveFlashcards.domain.document.StudyDocument;
import br.com.bruno.reactiveFlashcards.domain.mapper.StudyDomainMapper;
import br.com.bruno.reactiveFlashcards.domain.repository.StudyRepository;
import br.com.bruno.reactiveFlashcards.domain.service.query.DeckQueryService;
import br.com.bruno.reactiveFlashcards.domain.service.query.UserQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class StudyService {

    private final DeckQueryService deckQueryService;
    private final UserQueryService userQueryService;
    private final StudyRepository studyRepository;
    private final StudyDomainMapper studyDomainMapper;

    public Mono<StudyDocument> start(StudyDocument document) {
        return userQueryService.findById(document.userId())
                .flatMap(user -> deckQueryService.findById(document.studyDeck().deckId())
                .flatMap(deck -> getCards(document, deck.cards())))
                .map(study -> study.toBuilder()
                        .question(studyDomainMapper.generateRandomQuestion(study.studyDeck().cards()))
                        .build())
                .doFirst(() -> log.info("Starting study: {}", document))
                .flatMap(studyRepository::save)
                .doOnSuccess(study -> log.info("Save study: {}", study));
    }

    public Mono<StudyDocument> getCards(StudyDocument document, Set<Card> cards) {
        return Flux.fromIterable(cards)
                .doFirst(() -> log.info("Getting cards for study: {}", document))
                .map(studyDomainMapper::toStudyCard)
                .collectList()
                .map(studyCards -> document.studyDeck().toBuilder().cards(Set.copyOf(studyCards)).build())
                .map(studyDeck -> document.toBuilder().studyDeck(studyDeck).build());
    }

}
