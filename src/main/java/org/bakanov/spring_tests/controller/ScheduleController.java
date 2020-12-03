package org.bakanov.spring_tests.controller;

import org.bakanov.spring_tests.exceptions.ResourceNotFoundException;
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

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/groups/{groupId}/schedules")
    public List<Schedule> getScheduleByGroupId(@PathVariable Integer groupId) {
        return scheduleRepository.findByGroupId(groupId);
    }

    @GetMapping("/subject/{subjectId}/testLists/{testListId}/schedules")
    public List<Schedule> getScheduleByTestListIdAndSubject(@PathVariable Integer subjectId,
                                                            @PathVariable Integer testListId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found with id " + subjectId);
        }
        return scheduleRepository.findByTestListId(testListId);
    }

    @GetMapping("/roles/{roleId}/users/{userId}/testLists/{testListId}/schedules")
    public List<Schedule> getScheduleByTestListIdAndUserWithRole(@PathVariable Integer roleId,
                                                                 @PathVariable Integer userId,
                                                                 @PathVariable Integer testListId) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return scheduleRepository.findByTestListId(testListId);
    }

    @GetMapping("/groups/{groupId}/users/{userId}/testLists/{testListId}/schedules")
    public List<Schedule> getScheduleByTestListIdAndUserWithGroup(@PathVariable Integer groupId,
                                                                  @PathVariable Integer userId,
                                                                  @PathVariable Integer testListId) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return scheduleRepository.findByTestListId(testListId);
    }

    @PostMapping("groups/{groupId}/schedules")
    public Schedule addGroupToSchedule(@PathVariable Integer groupId,
                                       @Valid @RequestBody Schedule schedule) {
        return groupRepository.findById(groupId)
                .map(group -> {
                    schedule.setGroup(group);
                    return scheduleRepository.save(schedule);
                }).orElseThrow(() -> new ResourceNotFoundException("Group not found with id " + groupId));
    }

    @PostMapping("/subject/{subjectId}/testLists/{testListId}/schedules")
    public Schedule addTestListBySubjectToSchedule(@PathVariable Integer subjectId,
                                                   @PathVariable Integer testListId,
                                                   @Valid @RequestBody Schedule schedule) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found with id " + subjectId);
        }
        return testListRepository.findById(testListId)
                .map(testList -> {
                    schedule.setTestList(testList);
                    return scheduleRepository.save(schedule);
                }).orElseThrow(() -> new ResourceNotFoundException("TestList not found with id " + testListId));
    }

    @PostMapping("/roles/{roleId}/users/{userId}/testLists/{testListId}/schedules")
    public Schedule addTestListByUserAndRoleToSchedule(@PathVariable Integer roleId,
                                                       @PathVariable Integer userId,
                                                       @PathVariable Integer testListId,
                                                       @Valid @RequestBody Schedule schedule) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return testListRepository.findById(testListId)
                .map(testList -> {
                    schedule.setTestList(testList);
                    return scheduleRepository.save(schedule);
                }).orElseThrow(() -> new ResourceNotFoundException("TestList not found with id " + testListId));
    }

    @PostMapping("/groups/{groupId}/users/{userId}/testLists/{testListId}/schedules")
    public Schedule addTestListByUserAndGroupToSchedule(@PathVariable Integer groupId,
                                                        @PathVariable Integer userId,
                                                        @PathVariable Integer testListId,
                                                        @Valid @RequestBody Schedule schedule) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return testListRepository.findById(testListId)
                .map(testList -> {
                    schedule.setTestList(testList);
                    return scheduleRepository.save(schedule);
                }).orElseThrow(() -> new ResourceNotFoundException("TestList not found with id " + testListId));
    }

    @PutMapping("/groups/{groupId}/schedules/{scheduleId}")
    public Schedule updateScheduleAndGroup(@PathVariable Integer groupId,
                                           @PathVariable Integer scheduleId,
                                           @Valid @RequestBody Schedule scheduleRequest){
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        return scheduleRepository.findById(scheduleId)
                .map(schedule -> {
                    schedule.setActive(scheduleRequest.getActive());
                    schedule.setDuration(scheduleRequest.getDuration());
                    schedule.setStart_dt(scheduleRequest.getStart_dt());
                    schedule.setStart_time(scheduleRequest.getStart_time());
                    schedule.setEnd_dt(scheduleRequest.getEnd_dt());
                    schedule.setEnd_time(scheduleRequest.getEnd_time());
                    schedule.setGroup(scheduleRequest.getGroup());
                    schedule.setTestList(scheduleRequest.getTestList());
                    return scheduleRepository.save(schedule);
                }).orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id " + scheduleId));
    }

    @PutMapping("/subject/{subjectId}/testLists/{testListId}/schedules/{scheduleId}")
    public Schedule updateScheduleAndTestListBySubject(@PathVariable Integer subjectId,
                                                       @PathVariable Integer testListId,
                                                       @PathVariable Integer scheduleId,
                                                       @Valid @RequestBody Schedule scheduleRequest){
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found with id " + subjectId);
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
                    schedule.setTestList(scheduleRequest.getTestList());
                    schedule.setGroup(scheduleRequest.getGroup());
                    return scheduleRepository.save(schedule);
                }).orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id " + scheduleId));
    }

    @PutMapping("/roles/{roleId}/users/{userId}/testLists/{testListId}/schedules/{scheduleId}")
    public Schedule updateScheduleAndTestListByUserAndRole(@PathVariable Integer roleId,
                                                           @PathVariable Integer userId,
                                                           @PathVariable Integer testListId,
                                                           @PathVariable Integer scheduleId,
                                                           @Valid @RequestBody Schedule scheduleRequest){
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
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
                    schedule.setTestList(scheduleRequest.getTestList());
                    schedule.setGroup(scheduleRequest.getGroup());
                    return scheduleRepository.save(schedule);
                }).orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id " + scheduleId));
    }

    @PutMapping("/groups/{groupId}/users/{userId}/testLists/{testListId}/schedules/{scheduleId}")
    public Schedule updateScheduleAndTestListByUserAndGroup(@PathVariable Integer groupId,
                                                            @PathVariable Integer userId,
                                                            @PathVariable Integer testListId,
                                                            @PathVariable Integer scheduleId,
                                                            @Valid @RequestBody Schedule scheduleRequest){
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
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
                    schedule.setTestList(scheduleRequest.getTestList());
                    schedule.setGroup(scheduleRequest.getGroup());
                    return scheduleRepository.save(schedule);
                }).orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id " + scheduleId));
    }

    @DeleteMapping("/groups/{groupId}/schedules/{scheduleId}")
    public ResponseEntity<?> deleteScheduleByGroup(@PathVariable Integer groupId,
                                                   @PathVariable Integer scheduleId) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        return scheduleRepository.findById(scheduleId)
                .map(schedule -> {
                    scheduleRepository.delete(schedule);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id " + scheduleId));
    }

    @DeleteMapping("/subject/{subjectId}/testLists/{testListId}/schedules/{scheduleId}")
    public ResponseEntity<?> deleteScheduleByTestListAndSubject(@PathVariable Integer subjectId,
                                                                @PathVariable Integer testListId,
                                                                @PathVariable Integer scheduleId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found with id " + subjectId);
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

    @DeleteMapping("/roles/{roleId}/users/{userId}/testLists/{testListId}/schedules/{scheduleId}")
    public ResponseEntity<?> deleteScheduleByTestListWithUserAndRole(@PathVariable Integer roleId,
                                                                     @PathVariable Integer userId,
                                                                     @PathVariable Integer testListId,
                                                                     @PathVariable Integer scheduleId) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
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

    @DeleteMapping("/groups/{groupId}/users/{userId}/testLists/{testListId}/schedules/{scheduleId}")
    public ResponseEntity<?> deleteScheduleByTestListWithUserAndGroup(@PathVariable Integer groupId,
                                                                      @PathVariable Integer userId,
                                                                      @PathVariable Integer testListId,
                                                                      @PathVariable Integer scheduleId) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
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