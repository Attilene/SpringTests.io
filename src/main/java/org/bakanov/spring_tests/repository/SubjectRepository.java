package org.bakanov.spring_tests.repository;

import org.bakanov.spring_tests.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer> { }
