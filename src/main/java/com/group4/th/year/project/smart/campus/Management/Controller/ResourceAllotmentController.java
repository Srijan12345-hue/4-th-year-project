package com.group4.th.year.project.smart.campus.Management.Controller;

import com.group4.th.year.project.smart.campus.Management.ResourceAllotment;
import com.group4.th.year.project.smart.campus.Management.Repo.ResourceAllotmentRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/allotments")
public class ResourceAllotmentController {
    private final ResourceAllotmentRepo allotmentRepo;

    public ResourceAllotmentController(ResourceAllotmentRepo allotmentRepo) {
        this.allotmentRepo = allotmentRepo;
    }

    @GetMapping
    public List<ResourceAllotment> getAllAllotments() {
        return allotmentRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceAllotment> getAllotmentById(@PathVariable Long id) {
        return allotmentRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ResourceAllotment> createAllotment(@RequestBody ResourceAllotment allotment) {
        if (hasConflict(allotment)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(allotmentRepo.save(allotment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceAllotment> updateAllotment(@PathVariable Long id, @RequestBody ResourceAllotment updatedAllotment) {
        return allotmentRepo.findById(id)
                .map(existing -> {
                    updatedAllotment.setId(id);
                    if (hasConflict(updatedAllotment, id)) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).<ResourceAllotment>build();
                    }
                    return ResponseEntity.ok(allotmentRepo.save(updatedAllotment));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAllotment(@PathVariable Long id) {
        if (!allotmentRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        allotmentRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private boolean hasConflict(ResourceAllotment newAllotment) {
        if (newAllotment.getResource() == null || newAllotment.getDayOfWeek() == null) {
            return false;
        }
        List<ResourceAllotment> existing = allotmentRepo.findByResourceAndDayOfWeek(newAllotment.getResource(), newAllotment.getDayOfWeek());
        for (ResourceAllotment existingAllotment : existing) {
            if (isOverlapping(newAllotment.getStartTime(), newAllotment.getEndTime(),
                               existingAllotment.getStartTime(), existingAllotment.getEndTime())) {
                return true;
            }
        }
        return false;
    }

    private boolean hasConflict(ResourceAllotment newAllotment, Long currentId) {
        if (newAllotment.getResource() == null || newAllotment.getDayOfWeek() == null) {
            return false;
        }
        List<ResourceAllotment> existing = allotmentRepo.findByResourceAndDayOfWeek(newAllotment.getResource(), newAllotment.getDayOfWeek());
        for (ResourceAllotment existingAllotment : existing) {
            if (!existingAllotment.getId().equals(currentId)) {
                if (isOverlapping(newAllotment.getStartTime(), newAllotment.getEndTime(),
                                   existingAllotment.getStartTime(), existingAllotment.getEndTime())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isOverlapping(java.time.LocalTime start1, java.time.LocalTime end1, java.time.LocalTime start2, java.time.LocalTime end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }
}
