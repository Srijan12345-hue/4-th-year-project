package com.group4.th.year.project.smart.campus.Management.Controller;

<<<<<<< HEAD

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
=======

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
>>>>>>> origin/master
import com.group4.th.year.project.smart.campus.Management.Issue;
import com.group4.th.year.project.smart.campus.Management.Repo.IssueRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {
    private final IssueRepo issueRepo;

    public IssueController(IssueRepo issueRepo) {
        this.issueRepo = issueRepo;
    }

<<<<<<< HEAD
    @Cacheable(value = "issues", key = "'all'")
    @GetMapping
=======
    @Cacheable(value = "issues", key = "'all'")
    @GetMapping
>>>>>>> origin/master
    public List<Issue> getIssues() {
        return issueRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long id) {
        return issueRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

<<<<<<< HEAD
    @CacheEvict(value = "issues", allEntries = true)
=======
    @CacheEvict(value = "issues", allEntries = true)
>>>>>>> origin/master
    @PostMapping
    public ResponseEntity<Issue> addIssue(@RequestBody Issue issue) {
        issue.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(issueRepo.save(issue));
    }

<<<<<<< HEAD
    @CacheEvict(value = "issues", allEntries = true)
=======
    @CacheEvict(value = "issues", allEntries = true)
>>>>>>> origin/master
    @PutMapping("/{id}")
    public ResponseEntity<Issue> updateIssue(@PathVariable Long id, @RequestBody Issue updatedIssue) {
        return issueRepo.findById(id)
                .map(issue -> {
                    issue.setBookId(updatedIssue.getBookId());
                    issue.setIssuedTo(updatedIssue.getIssuedTo());
                    issue.setIssueDate(updatedIssue.getIssueDate());
                    issue.setDueDate(updatedIssue.getDueDate());
                    return ResponseEntity.ok(issueRepo.save(issue));
                })
                .orElse(ResponseEntity.notFound().build());
    }

<<<<<<< HEAD
    @CacheEvict(value = "issues", allEntries = true)
=======
    @CacheEvict(value = "issues", allEntries = true)
>>>>>>> origin/master
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        if (!issueRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        issueRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
<<<<<<< HEAD


=======


>>>>>>> origin/master
