package com.group4.th.year.project.smart.campus.Management.Service;

import com.group4.th.year.project.smart.campus.Management.*;
import com.group4.th.year.project.smart.campus.Management.Repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TravelService {
    private final VehicleRepo vehicleRepo;
    private final TravelRequestRepo requestRepo;
    private final TravelAllocationRepo allocationRepo;

    public TravelService(VehicleRepo vehicleRepo, TravelRequestRepo requestRepo, TravelAllocationRepo allocationRepo) {
        this.vehicleRepo = vehicleRepo;
        this.requestRepo = requestRepo;
        this.allocationRepo = allocationRepo;
    }

    @Transactional
    public TravelAllocation allocateVehicle(Long requestId, Long vehicleId) {
        TravelRequest request = requestRepo.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Travel request not found."));
        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found."));

        if (!"APPROVED".equals(request.getStatus())) {
            throw new IllegalStateException("Vehicle can only be allocated to APPROVED requests.");
        }

        if (!"AVAILABLE".equals(vehicle.getStatus())) {
            throw new IllegalStateException("Vehicle is currently not available.");
        }

        if (hasConflict(vehicleId, request.getStartDate(), request.getEndDate())) {
            throw new IllegalStateException("Vehicle is already allocated for this time period.");
        }

        TravelAllocation allocation = new TravelAllocation();
        allocation.setRequest(request);
        allocation.setVehicle(vehicle);
        allocation.setAllocationDate(LocalDateTime.now());

        vehicle.setStatus("IN_USE");
        vehicleRepo.save(vehicle);

        return allocationRepo.save(allocation);
    }

    private boolean hasConflict(Long vehicleId, LocalDateTime start, LocalDateTime end) {
        List<TravelAllocation> allocations = allocationRepo.findByVehicleId(vehicleId);
        for (TravelAllocation alloc : allocations) {
            TravelRequest req = alloc.getRequest();
            if (req != null) {
                // Overlap logic: (start1 < end2) && (end1 > start2)
                if (start.isBefore(req.getEndDate()) && end.isAfter(req.getStartDate())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Transactional
    public void releaseVehicle(Long allocationId) {
        TravelAllocation allocation = allocationRepo.findById(allocationId)
                .orElseThrow(() -> new IllegalArgumentException("Allocation not found."));

        Vehicle vehicle = allocation.getVehicle();
        if (vehicle != null) {
            vehicle.setStatus("AVAILABLE");
            vehicleRepo.save(vehicle);
        }

        allocationRepo.delete(allocation);
    }
}
