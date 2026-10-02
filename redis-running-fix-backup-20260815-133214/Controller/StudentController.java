package com.group4.th.year.project.smart.campus.Management.Controller;

import com.group4.th.year.project.smart.campus.Management.Student;
import com.group4.th.year.project.smart.campus.Management.Repo.StudentRepo;
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
@RequestMapping("/students")
public class StudentController {
    private final StudentRepo studentRepo;

    public StudentController(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Cacheable(value = "students", key = "'all'")


    @GetMapping public List<Student> getStudents() {
        return studentRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "students", allEntries = true)
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        student.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentRepo.save(student));
    }

    @CacheEvict(value = "students", allEntries = true)
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return studentRepo.findById(id)
                .map(student -> {
                    student.setRollNo(updatedStudent.getRollNo());
                    student.setCourseId(updatedStudent.getCourseId());
                    student.setSemester(updatedStudent.getSemester());
                    return ResponseEntity.ok(studentRepo.save(student));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "students", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (!studentRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        studentRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

