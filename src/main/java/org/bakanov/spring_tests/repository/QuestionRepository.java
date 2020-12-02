package org.bakanov.spring_tests.repository;

import org.bakanov.spring_tests.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> { }
