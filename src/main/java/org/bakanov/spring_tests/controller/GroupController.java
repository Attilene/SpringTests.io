package org.bakanov.spring_tests.controller;

import org.bakanov.spring_tests.exceptions.ResourceNotFoundException;
import org.bakanov.spring_tests.model.Group;
import org.bakanov.spring_tests.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class GroupController {
    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("/groups")
    public Page<Group> getGroups(Pageable pageable) { return groupRepository.findAll(pageable); }

    @PostMapping("/groups")
    public Group createdGroup(@Valid @RequestBody Group group) { return groupRepository.save(group); }

    @PutMapping("/groups/{groupId}")
    public Group updateGroup(@PathVariable Integer groupId,
                             @Valid @RequestBody Group groupRequest) {
        return groupRepository.findById(groupId)
                .map(group -> {
                    group.setName(groupRequest.getName());
                    group.setYear(groupRequest.getYear());
                    group.setSemester(groupRequest.getSemester());
                    return groupRepository.save(group);
                }).orElseThrow(() -> new ResourceNotFoundException("Group not found with id " + groupId));
    }

    @DeleteMapping("groups/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable Integer groupId) {
        return groupRepository.findById(groupId)
                .map(group -> {
                    groupRepository.delete(group);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Group not found with id " + groupId));
    }
}
