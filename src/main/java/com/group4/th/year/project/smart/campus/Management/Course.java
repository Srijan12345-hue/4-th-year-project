package com.group4.th.year.project.smart.campus.Management;

import jakarta.persistence.*;
<<<<<<< HEAD


@Entity
    @Table(name = "courses")
    public class Course {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
=======


@Entity
    @Table(name = "courses")
    public class Course {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
>>>>>>> origin/master
        private Long id;
        private String name;
        private String code;
        private Long facultyId;
        private int semester;
<<<<<<< HEAD

        public Course() {
        }

=======

        public Course() {
        }

>>>>>>> origin/master
        public Course(Long id, String name, String code, Long facultyId, int semester) {
            this.id = id;
            this.name = name;
            this.code = code;
            this.facultyId = facultyId;
            this.semester = semester;
        }
<<<<<<< HEAD

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

=======

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

>>>>>>> origin/master
        public String getCode() {
            return code;
        }

        public Long getFacultyId() {
            return facultyId;
        }
<<<<<<< HEAD

        public int getSemester() {
            return semester;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

=======

        public int getSemester() {
            return semester;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

>>>>>>> origin/master
        public void setCode(String code) {
            this.code = code;
        }

        public void setFacultyId(Long facultyId) {
            this.facultyId = facultyId;
        }
<<<<<<< HEAD

        public void setSemester(int semester) {
            this.semester = semester;
        }
=======

        public void setSemester(int semester) {
            this.semester = semester;
        }
>>>>>>> origin/master
}
