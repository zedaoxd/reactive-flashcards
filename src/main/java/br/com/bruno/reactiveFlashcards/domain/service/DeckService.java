package br.com.bruno.reactiveFlashcards.domain.service;

import br.com.bruno.reactiveFlashcards.domain.document.DeckDocument;
import br.com.bruno.reactiveFlashcards.domain.repository.DeckRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class DeckService {

    private final DeckRepository deckRepository;

    public Mono<DeckDocument> save(final DeckDocument deckDocument) {
        return deckRepository.save(deckDocument)
                .doFirst(() -> log.info("Saving deck: {}", deckDocument));
    }
}
