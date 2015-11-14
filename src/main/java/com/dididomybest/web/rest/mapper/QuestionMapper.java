package com.dididomybest.web.rest.mapper;

import com.dididomybest.domain.*;
import com.dididomybest.web.rest.dto.QuestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Question and its DTO QuestionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuestionMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    QuestionDTO questionToQuestionDTO(Question question);

    @Mapping(source = "userId", target = "user")
    Question questionDTOToQuestion(QuestionDTO questionDTO);

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
