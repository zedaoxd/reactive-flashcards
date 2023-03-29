package br.com.bruno.reactiveFlashcards.domain.repository;

import br.com.bruno.reactiveFlashcards.domain.document.StudyDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRepository extends ReactiveMongoRepository<StudyDocument, String> {
}
