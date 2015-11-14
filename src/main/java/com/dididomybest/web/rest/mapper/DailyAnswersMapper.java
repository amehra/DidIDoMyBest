package com.dididomybest.web.rest.mapper;

import com.dididomybest.domain.*;
import com.dididomybest.web.rest.dto.DailyAnswersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DailyAnswers and its DTO DailyAnswersDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DailyAnswersMapper {

    @Mapping(source = "question.id", target = "questionId")
    @Mapping(source = "question.question", target = "questionQuestion")
    DailyAnswersDTO dailyAnswersToDailyAnswersDTO(DailyAnswers dailyAnswers);

    @Mapping(source = "questionId", target = "question")
    DailyAnswers dailyAnswersDTOToDailyAnswers(DailyAnswersDTO dailyAnswersDTO);

    default Question questionFromId(Long id) {
        if (id == null) {
            return null;
        }
        Question question = new Question();
        question.setId(id);
        return question;
    }
}
