package com.group4.th.year.project.smart.campus.Management;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "notices")
    public class Notice {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String title;
        private String body;
        private String issuedForRoles;
        private LocalDateTime validFrom;
        private LocalDateTime validTo;
        private String attachmentPath;
        private String attachmentName;

        public Notice() {
        }

        public Notice(Long id, String title, String body, String issuedForRoles, LocalDateTime validFrom, LocalDateTime validTo) {
            this.id = id;
            this.title = title;
            this.body = body;
            this.issuedForRoles = issuedForRoles;
            this.validFrom = validFrom;
            this.validTo = validTo;
        }

        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getBody() {
            return body;
        }

        public String getIssuedForRoles() {
            return issuedForRoles;
        }

        public LocalDateTime getValidFrom() {
            return validFrom;
        }

        public LocalDateTime getValidTo() {
            return validTo;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public void setIssuedForRoles(String issuedForRoles) {
            this.issuedForRoles = issuedForRoles;
        }

        public void setValidFrom(LocalDateTime validFrom) {
            this.validFrom = validFrom;
        }

        public void setValidTo(LocalDateTime validTo) {
            this.validTo = validTo;
        }

        public String getAttachmentPath() { return attachmentPath; }
        public void setAttachmentPath(String attachmentPath) { this.attachmentPath = attachmentPath; }
        public String getAttachmentName() { return attachmentName; }
        public void setAttachmentName(String attachmentName) { this.attachmentName = attachmentName; }
    }
