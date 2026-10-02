package com.group4.th.year.project.smart.campus.Management.Controller;

import com.group4.th.year.project.smart.campus.Management.Course;
import com.group4.th.year.project.smart.campus.Management.Repo.CoursesRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CoursesRepo coursesRepo;

    public CourseController(CoursesRepo coursesRepo) {
        this.coursesRepo = coursesRepo;
    }

    @Cacheable(value = "courses", key = "'all'")


    @GetMapping public List<Course> getCourses() {
        return coursesRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return coursesRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "courses", allEntries = true)
    @PostMapping
    public Course addCourse(@RequestBody Course course) {
        course.setId(null);
        return coursesRepo.save(course);
    }

    @CacheEvict(value = "courses", allEntries = true)
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
        return coursesRepo.findById(id)
                .map(course -> {
                    course.setName(updatedCourse.getName());
                    course.setCode(updatedCourse.getCode());
                    course.setFacultyId(updatedCourse.getFacultyId());
                    course.setSemester(updatedCourse.getSemester());
                    return ResponseEntity.ok(coursesRepo.save(course));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "courses", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        if (!coursesRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        coursesRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

