package org.bakanov.spring_tests.repository;

import org.bakanov.spring_tests.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> { }