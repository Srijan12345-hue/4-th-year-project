package com.group4.th.year.project.smart.campus.Management;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "resources")
    public class Resource {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String type;
        private int capacity;

        public Resource() {
        }

        public Resource(Long id, String name, String type, int capacity) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.capacity = capacity;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public int getCapacity() {
            return capacity;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }
    }

