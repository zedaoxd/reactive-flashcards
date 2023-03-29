package br.com.bruno.reactiveFlashcards.domain.repository;

import br.com.bruno.reactiveFlashcards.domain.document.DeckDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckRepository extends ReactiveMongoRepository<DeckDocument, String> {
}
