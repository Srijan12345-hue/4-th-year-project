package com.group4.th.year.project.smart.campus.Management;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rollNo;
    private Long courseId;
    private int semester;

    public Student() {
    }

    public Student(Long id, String rollNo, Long courseId, int semester) {
        this.id = id;
        this.rollNo = rollNo;
        this.courseId = courseId;
        this.semester = semester;
    }

    public Long getId() {
        return id;
    }

    public String getRollNo() {
        return rollNo;
    }

    public Long getCourseId() {
        return courseId;
    }

    public int getSemester() {
        return semester;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}
