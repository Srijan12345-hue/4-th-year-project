package com.group4.th.year.project.smart.campus.Management;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "app_users")
public class User {



        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String email;
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private String password;
        @Enumerated(EnumType.STRING)
        private ROLE role;
        private LocalDateTime lastLogin;

        public User() {
        }

        public User(Long id, String name, String email, String password, ROLE role, LocalDateTime lastLogin) {
            this.id = id;
            this.name = name;
            this.email = email;


            this.password = password;
            this.role = role;
            this.lastLogin = lastLogin;
        }

        public Long getId() {
            return id;
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

        public ROLE getRole() {
            return role;
        }

        public LocalDateTime getLastLogin() {
            return lastLogin;
        }

        public void setId(Long id) {
            this.id = id;
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

        public void setRole(ROLE role) {
            this.role = role;
        }

        public void setLastLogin(LocalDateTime lastLogin) {
            this.lastLogin = lastLogin;
        }
    }
