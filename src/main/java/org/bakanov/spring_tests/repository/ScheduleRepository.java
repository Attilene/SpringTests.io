package org.bakanov.spring_tests.repository;

import org.bakanov.spring_tests.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findByTestListId(Integer testListId);
    List<Schedule> findByGroupId(Integer groupId);
}
