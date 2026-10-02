package com.group4.th.year.project.smart.campus.Management;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "travel_requests")
public class TravelRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String destination;
    private String purpose;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status; // PENDING, APPROVED, REJECTED
    private Double estimatedCost;

    public TravelRequest() {}

    public TravelRequest(Long id, User user, String destination, String purpose, LocalDateTime startDate, LocalDateTime endDate, String status, Double estimatedCost) {
        this.id = id;
        this.user = user;
        this.destination = destination;
        this.purpose = purpose;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.estimatedCost = estimatedCost;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getEstimatedCost() { return estimatedCost; }
    public void setEstimatedCost(Double estimatedCost) { this.estimatedCost = estimatedCost; }
}
