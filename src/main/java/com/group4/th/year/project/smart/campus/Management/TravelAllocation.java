package com.group4.th.year.project.smart.campus.Management;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "travel_allocations")
public class TravelAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "request_id")
    private TravelRequest request;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    private LocalDateTime allocationDate;

    public TravelAllocation() {}

    public TravelAllocation(Long id, TravelRequest request, Vehicle vehicle, LocalDateTime allocationDate) {
        this.id = id;
        this.request = request;
        this.vehicle = vehicle;
        this.allocationDate = allocationDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TravelRequest getRequest() { return request; }
    public void setRequest(TravelRequest request) { this.request = request; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public LocalDateTime getAllocationDate() { return allocationDate; }
    public void setAllocationDate(LocalDateTime allocationDate) { this.allocationDate = allocationDate; }
}
