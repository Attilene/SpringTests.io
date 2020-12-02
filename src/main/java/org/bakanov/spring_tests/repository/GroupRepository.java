package org.bakanov.spring_tests.repository;

import org.bakanov.spring_tests.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> { }
