package org.bakanov.spring_tests.controller;

import org.bakanov.spring_tests.exceptions.ResourceNotFoundException;
import org.bakanov.spring_tests.model.Group;
import org.bakanov.spring_tests.model.Schedule;
import org.bakanov.spring_tests.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ScheduleController {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TestListRepository testListRepository;

    @GetMapping("/groups/{groupId}/testLists/{testListId}/schedules")
    public List<Schedule> getSchedules(@PathVariable Integer groupId,
                                       @PathVariable Integer testListId) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        return scheduleRepository.findByTestListId(testListId);
    }

    @PostMapping("/groups/{groupId}/testLists/{testListId}/schedules")
    public Schedule addSchedule(@PathVariable Integer groupId,
                                @PathVariable Integer testListId,
                                @Valid @RequestBody Schedule schedule) {
        Group group;
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        else { group = groupRepository.getOne(groupId); }
        return testListRepository.findById(testListId)
                .map(testList -> {
                    schedule.setGroup(group);
                    schedule.setTestList(testList);
                    return scheduleRepository.save(schedule);
                }).orElseThrow(() -> new ResourceNotFoundException("TestList not found with id " + testListId));
    }

    @PutMapping("/groups/{groupId}/testLists/{testListId}/schedules/{scheduleId}")
    public Schedule updateSchedule(@PathVariable Integer groupId,
                                   @PathVariable Integer testListId,
                                   @PathVariable Integer scheduleId,
                                   @Valid @RequestBody Schedule scheduleRequest){
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        if (!testListRepository.existsById(testListId)) {
            throw new ResourceNotFoundException("TestList not found with id " + testListId);
        }
        return scheduleRepository.findById(scheduleId)
                .map(schedule -> {
                    schedule.setActive(scheduleRequest.getActive());
                    schedule.setDuration(scheduleRequest.getDuration());
                    schedule.setStart_dt(scheduleRequest.getStart_dt());
                    schedule.setStart_time(scheduleRequest.getStart_time());
                    schedule.setEnd_dt(scheduleRequest.getEnd_dt());
                    schedule.setEnd_time(scheduleRequest.getEnd_time());
                    return scheduleRepository.save(schedule);
                }).orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id " + scheduleId));
    }

    @DeleteMapping("/groups/{groupId}/testLists/{testListId}/schedules/{scheduleId}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Integer groupId,
                                            @PathVariable Integer testListId,
                                            @PathVariable Integer scheduleId) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        if (!testListRepository.existsById(testListId)) {
            throw new ResourceNotFoundException("TestList not found with id " + testListId);
        }
        return scheduleRepository.findById(scheduleId)
                .map(schedule -> {
                    scheduleRepository.delete(schedule);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id " + scheduleId));
    }
}