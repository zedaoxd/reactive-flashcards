package br.com.bruno.reactiveFlashcards.domain.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Objects;

public record QuestionDTO(String asked,
                          OffsetDateTime askedIn,
                          String answered,
                          OffsetDateTime answeredIn,
                          String expected) {

    public Boolean isAnswered() {
        return Objects.nonNull(answeredIn);
    }

    public Boolean isNotAnswered() {
        return Objects.isNull(answeredIn);
    }

    public Boolean isCorrect() {
        return isAnswered() && expected.equals(answered);
    }

    public static QuestionBuilder builder() {
        return new QuestionBuilder();
    }

    public QuestionBuilder toBuilder() {
        return new QuestionBuilder(asked, askedIn, answered, answeredIn, expected);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuestionBuilder {
        private String asked;
        private OffsetDateTime askedIn;
        private String answered;
        private OffsetDateTime answeredIn;
        private String expected;

        public QuestionBuilder asked(String asked) {
            this.asked = asked;
            this.askedIn = OffsetDateTime.now();
            return this;
        }

        public QuestionBuilder answered(String answered) {
            this.answered = answered;
            this.answeredIn = OffsetDateTime.now();
            return this;
        }

        public QuestionBuilder expected(String expected) {
            this.expected = expected;
            return this;
        }

        public QuestionDTO build() {
            return new QuestionDTO(asked, askedIn, answered, answeredIn, expected);
        }
    }
}
