package br.com.bruno.reactiveFlashcards.domain.service.query;

import br.com.bruno.reactiveFlashcards.domain.document.DeckDocument;
import br.com.bruno.reactiveFlashcards.domain.exception.NotFoundException;
import br.com.bruno.reactiveFlashcards.domain.repository.DeckRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static br.com.bruno.reactiveFlashcards.domain.exception.BaseErrorMessage.DECK_NOT_FOUND;

@Service
@AllArgsConstructor
@Slf4j
public class DeckQueryService {

    private final DeckRepository deckRepository;

    public Mono<DeckDocument> findById(final String id) {
        return deckRepository.findById(id)
                .doFirst(() -> log.info("Finding deck by id: {}", id))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(DECK_NOT_FOUND.params(id).getMessage()))));
    }
}
