package br.com.bruno.reactiveFlashcards.domain.service.query;

import br.com.bruno.reactiveFlashcards.api.controller.request.UserPageRequest;
import br.com.bruno.reactiveFlashcards.domain.document.UserDocument;
import br.com.bruno.reactiveFlashcards.domain.dto.UserPageDocument;
import br.com.bruno.reactiveFlashcards.domain.exception.EmailAlreadyExistsException;
import br.com.bruno.reactiveFlashcards.domain.exception.NotFoundException;
import br.com.bruno.reactiveFlashcards.domain.repository.UserRepository;
import br.com.bruno.reactiveFlashcards.domain.repository.UserRepositoryImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static br.com.bruno.reactiveFlashcards.domain.exception.BaseErrorMessage.EMAIL_ALREADY_EXISTS;
import static br.com.bruno.reactiveFlashcards.domain.exception.BaseErrorMessage.USER_NOT_FOUND;

@Service
@AllArgsConstructor
@Slf4j
public class UserQueryService {

    private final UserRepository userRepository;
    private final UserRepositoryImpl userRepositoryImpl;

    public Mono<UserDocument> findById(String id) {
        return userRepository.findById(id)
                .doFirst(() -> log.info("Finding user by id: {}", id))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(USER_NOT_FOUND.params("id",id).getMessage()))));
    }

    public Mono<UserDocument> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .doFirst(() -> log.info("Finding user by email: {}", email))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(USER_NOT_FOUND.params("email", email).getMessage()))));
    }

    public Mono<UserPageDocument> findOnDemand(UserPageRequest request){
        return userRepositoryImpl.findOnDemand(request)
                .collectList()
                .zipWhen(documents -> userRepositoryImpl.count(request))
                .map(tuple -> UserPageDocument.builder()
                        .limit(request.limit())
                        .currentPage(request.page())
                        .totalItems(tuple.getT2())
                        .content(tuple.getT1())
                        .build());
    }
}
