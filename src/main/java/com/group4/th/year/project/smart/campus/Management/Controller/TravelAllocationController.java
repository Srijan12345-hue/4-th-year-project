package com.group4.th.year.project.smart.campus.Management.Controller;

import com.group4.th.year.project.smart.campus.Management.TravelAllocation;
import com.group4.th.year.project.smart.campus.Management.Repo.TravelAllocationRepo;
import com.group4.th.year.project.smart.campus.Management.Service.TravelService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/travel-allocations")
public class TravelAllocationController {
    private final TravelAllocationRepo allocationRepo;
    private final TravelService travelService;

    public TravelAllocationController(TravelAllocationRepo allocationRepo, TravelService travelService) {
        this.allocationRepo = allocationRepo;
        this.travelService = travelService;
    }

    @Cacheable(value = "travelAllocations", key = "'all'")
    @GetMapping
    public List<TravelAllocation> getAllAllocations() {
        return allocationRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<TravelAllocation> allocateVehicle(@RequestBody Map<String, Long> request) {
        Long requestId = request.get("requestId");
        Long vehicleId = request.get("vehicleId");

        if (requestId == null || vehicleId == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            TravelAllocation allocation = travelService.allocateVehicle(requestId, vehicleId);
            return ResponseEntity.status(HttpStatus.CREATED).body(allocation);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @CacheEvict(value = "travelAllocations", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> releaseVehicle(@PathVariable Long id) {
        try {
            travelService.releaseVehicle(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
