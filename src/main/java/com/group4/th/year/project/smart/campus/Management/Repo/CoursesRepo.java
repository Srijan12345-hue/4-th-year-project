package com.group4.th.year.project.smart.campus.Management.Repo;

import com.group4.th.year.project.smart.campus.Management.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursesRepo extends JpaRepository<Course,  Long> {
}
