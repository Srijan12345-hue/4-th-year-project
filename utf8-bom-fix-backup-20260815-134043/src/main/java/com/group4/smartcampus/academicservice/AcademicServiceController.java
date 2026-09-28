package com.group4.smartcampus.academicservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AcademicServiceController {
    @GetMapping("/status")
    public Map<String, String> status() {
        return Map.of(
                "service", "academic-service",
                "module", "students, faculty, courses, attendance, marks",
                "status", "running"
        );
    }
}
