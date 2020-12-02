package org.bakanov.spring_tests.repository;

import org.bakanov.spring_tests.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> { }
