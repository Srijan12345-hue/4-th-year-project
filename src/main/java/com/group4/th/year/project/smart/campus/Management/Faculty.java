package com.group4.th.year.project.smart.campus.Management;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "faculties")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String empId;
    private String department;
    private String name;
    private String subject;

    public Faculty() {
    }

    public Faculty(Long id, String empId, String department,String name, String  subject) {
        this.id = id;
        this.empId = empId;
        this.department = department;
        this.name=name;
        this.subject= subject;
    }

    public Long getId() {
        return id;
    }

    public String getEmpId() {
        return empId;
    }

    public String getDepartment() {
        return department;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
