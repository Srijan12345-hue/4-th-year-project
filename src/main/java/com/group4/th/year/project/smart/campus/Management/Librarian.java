package com.group4.th.year.project.smart.campus.Management;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "librarians")
public class Librarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String empId;
    private String name;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String department;

    public Librarian() {
    }

    public Librarian(Long id, String empId, String name, String email, String password, String department) {
        this.id = id;
        this.empId = empId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public String getEmpId() {
        return empId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
