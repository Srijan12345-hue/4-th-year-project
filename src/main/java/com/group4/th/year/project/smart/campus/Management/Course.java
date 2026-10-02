package com.group4.th.year.project.smart.campus.Management;

import jakarta.persistence.*;


@Entity
    @Table(name = "courses")
    public class Course {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String code;
        private Long facultyId;
        private int semester;

        public Course() {
        }

        public Course(Long id, String name, String code, Long facultyId, int semester) {
            this.id = id;
            this.name = name;
            this.code = code;
            this.facultyId = facultyId;
            this.semester = semester;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public Long getFacultyId() {
            return facultyId;
        }

        public int getSemester() {
            return semester;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setFacultyId(Long facultyId) {
            this.facultyId = facultyId;
        }

        public void setSemester(int semester) {
            this.semester = semester;
        }
}
