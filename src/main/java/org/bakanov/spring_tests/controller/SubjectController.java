package org.bakanov.spring_tests.controller;

import org.bakanov.spring_tests.exceptions.ResourceNotFoundException;
import org.bakanov.spring_tests.model.Subject;
import org.bakanov.spring_tests.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class SubjectController {
    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/subjects")
    public Page<Subject> getSubjects(Pageable pageable) { return subjectRepository.findAll(pageable); }

    @PostMapping("/subjects")
    public Subject createdSubject(@Valid @RequestBody Subject subject) { return subjectRepository.save(subject); }

    @PutMapping("/subjects/{subjectId}")
    public Subject updateSubject(@PathVariable Integer subjectId, @Valid @RequestBody Subject subjectRequest) {
        return subjectRepository.findById(subjectId)
                .map(subject -> {
                    subject.setName(subjectRequest.getName());
                    return subjectRepository.save(subject);
                }).orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + subjectId));
    }

    @DeleteMapping("subjects/{subjectId}")
    public ResponseEntity<?> deleteSubject(@PathVariable Integer subjectId) {
        return subjectRepository.findById(subjectId)
                .map(subject -> {
                    subjectRepository.delete(subject);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + subjectId));
    }
}
