package br.com.bruno.reactiveFlashcards.domain.dto;

import br.com.bruno.reactiveFlashcards.domain.document.DeckDocument;
import br.com.bruno.reactiveFlashcards.domain.document.Question;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record MailMessageDTO(String destination,
                             String subject,
                             String template,
                             Map<String, Object> variables) {

    public static MailMessageDTOBuilder builder(){
        return new MailMessageDTOBuilder();
    }

    public static class MailMessageDTOBuilder{
        private String destination;
        private String subject;
        private Map<String, Object> variables = new HashMap<>();

        public MailMessageDTOBuilder destination(String destination){
            this.destination = destination;
            return this;
        }

        public MailMessageDTOBuilder subject(String subject){
            this.subject = subject;
            return this;
        }

        private MailMessageDTOBuilder variables(String key, Object value){
            this.variables.put(key, value);
            return this;
        }

        public MailMessageDTOBuilder username(String username){
            return variables("username", username);
        }

        public MailMessageDTOBuilder deck(DeckDocument deck){
            return variables("deck", deck);
        }

        public MailMessageDTOBuilder questions(List<Question> questions){
            questions.sort(Comparator.comparing(Question::answeredIn));
            return variables("questions", questions);
        }

        public MailMessageDTO build(){
            return new MailMessageDTO(destination, subject, "mail/studyResult", variables);
        }

    }

}

