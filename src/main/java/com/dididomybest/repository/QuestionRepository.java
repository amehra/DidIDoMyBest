package com.dididomybest.repository;

import com.dididomybest.domain.Question;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Question entity.
 */
public interface QuestionRepository extends JpaRepository<Question,Long> {

    @Query("select question from Question question where question.user.login = ?#{principal.username}")
    List<Question> findByUserIsCurrentUser();

}
