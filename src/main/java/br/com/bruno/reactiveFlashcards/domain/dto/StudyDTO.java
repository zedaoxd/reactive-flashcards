package br.com.bruno.reactiveFlashcards.domain.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public record StudyDTO(String id,
                       String userId,
                       Boolean complete,
                       StudyDeckDTO studyDeck,
                       List<QuestionDTO> questions,
                       List<String> remainAsks,
                       OffsetDateTime createdAt,
                       OffsetDateTime updatedAt) {

    public static StudyDocumentBuilder builder() {
        return new StudyDocumentBuilder();
    }

    public StudyDocumentBuilder toBuilder() {
        return new StudyDocumentBuilder(id, userId, studyDeck, questions, remainAsks, createdAt, updatedAt);
    }

    public Boolean hasAnyAnswered() {
        return CollectionUtils.isNotEmpty(remainAsks);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class StudyDocumentBuilder {
        private String id;
        private String userId;
        private StudyDeckDTO studyDeck;
        private List<QuestionDTO> questions = new ArrayList<>();
        private List<String> remainAsks = new ArrayList<>();
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        public StudyDocumentBuilder id(String id) {
            this.id = id;
            return this;
        }

        public StudyDocumentBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public StudyDocumentBuilder studyDeck(StudyDeckDTO studyDeck) {
            this.studyDeck = studyDeck;
            return this;
        }

        public StudyDocumentBuilder questions(List<QuestionDTO> questions) {
            this.questions = questions;
            return this;
        }

        public StudyDocumentBuilder question(QuestionDTO question) {
            this.questions.add(question);
            return this;
        }

        public StudyDocumentBuilder remainAsks(List<String> remainAsks) {
            this.remainAsks = remainAsks;
            return this;
        }

        public StudyDocumentBuilder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public StudyDocumentBuilder updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public StudyDTO build() {
            var rightQuestions = questions.stream().filter(QuestionDTO::isCorrect).toList();
            var complete = rightQuestions.size() == studyDeck.cards().size();
            return new StudyDTO(id, userId, complete, studyDeck, questions, remainAsks, createdAt, updatedAt);
        }
    }
}
