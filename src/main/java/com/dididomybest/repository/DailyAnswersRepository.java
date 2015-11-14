package com.dididomybest.repository;

import com.dididomybest.domain.DailyAnswers;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DailyAnswers entity.
 */
public interface DailyAnswersRepository extends JpaRepository<DailyAnswers,Long> {

}
