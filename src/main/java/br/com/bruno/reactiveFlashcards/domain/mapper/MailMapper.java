package br.com.bruno.reactiveFlashcards.domain.mapper;

import br.com.bruno.reactiveFlashcards.domain.document.DeckDocument;
import br.com.bruno.reactiveFlashcards.domain.document.UserDocument;
import br.com.bruno.reactiveFlashcards.domain.dto.MailMessageDTO;
import br.com.bruno.reactiveFlashcards.domain.dto.StudyDTO;
import org.mapstruct.*;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(MailMapperDecorator.class)
public interface MailMapper {

    @Mapping(target = "username", source = "user.name")
    @Mapping(target = "destination", source = "user.email")
    @Mapping(target = "subject", constant = "Relatório de estudos")
    @Mapping(target = "questions", source = "study.questions")
    MailMessageDTO toDTO(StudyDTO study, DeckDocument deck, UserDocument user);

    @Mapping(target = "to", expression = "java(new String[]{mailMessageDTO.destination()})")
    @Mapping(target = "from", source = "sender")
    @Mapping(target = "subject", source = "mailMessageDTO.subject")
    @Mapping(target = "fileTypeMap", ignore = true)
    @Mapping(target = "encodeFilenames", ignore = true)
    @Mapping(target = "validateAddresses", ignore = true)
    @Mapping(target = "replyTo", ignore = true)
    @Mapping(target = "cc", ignore = true)
    @Mapping(target = "bcc", ignore = true)
    @Mapping(target = "priority", ignore = true)
    @Mapping(target = "sentDate", ignore = true)
    @Mapping(target = "text", ignore = true)
    MimeMessageHelper toMimeMessageHelper(@MappingTarget MimeMessageHelper helper,
                                          MailMessageDTO mailMessageDTO,
                                          String sender,
                                          String body) throws MessagingException;

}
