package br.com.bruno.reactiveFlashcards.domain.repository;

import br.com.bruno.reactiveFlashcards.domain.document.StudyDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface StudyRepository extends ReactiveMongoRepository<StudyDocument, String> {

    Mono<StudyDocument> findByUserIdAndCompleteFalseAndStudyDeck_DeckId(String userId, String deckId);
}
