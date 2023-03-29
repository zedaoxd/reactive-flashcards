package br.com.bruno.reactiveFlashcards.domain.repository;

import br.com.bruno.reactiveFlashcards.domain.document.UserDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveMongoRepository<UserDocument, String> {
}
