package com.group4.th.year.project.smart.campus.Management;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "resource_allotments")
public class ResourceAllotment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String department;
    private String section;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public ResourceAllotment() {
    }

    public ResourceAllotment(Long id, Resource resource, Course course, String department, String section, String dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.resource = resource;
        this.course = course;
        this.department = department;
        this.section = section;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
