package com.group4.th.year.project.smart.campus.Management.Controller;

import com.group4.th.year.project.smart.campus.Management.Faculty;
import com.group4.th.year.project.smart.campus.Management.Repo.FacultyRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculties")
public class FacultyController {
    private final FacultyRepo facultyRepo;

    public FacultyController(FacultyRepo facultyRepo) {
        this.facultyRepo = facultyRepo;
    }

    @Cacheable(value = "faculties", key = "'all'")


    @GetMapping public List<Faculty> getFaculties() {
        return facultyRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Long id) {
        return facultyRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "faculties", allEntries = true)
    @PostMapping
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        faculty.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(facultyRepo.save(faculty));
    }

    @CacheEvict(value = "faculties", allEntries = true)
    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id, @RequestBody Faculty updatedFaculty) {
        return facultyRepo.findById(id)
                .map(faculty -> {
                    faculty.setEmpId(updatedFaculty.getEmpId());
                    faculty.setDepartment(updatedFaculty.getDepartment());
                    return ResponseEntity.ok(facultyRepo.save(faculty));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "faculties", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        if (!facultyRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        facultyRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

