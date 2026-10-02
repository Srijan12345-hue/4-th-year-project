package com.group4.th.year.project.smart.campus.Management.Controller;

import com.group4.th.year.project.smart.campus.Management.RoomAllocation;
import com.group4.th.year.project.smart.campus.Management.Repo.RoomAllocationRepo;
import com.group4.th.year.project.smart.campus.Management.Service.HostelService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room-allocations")
public class RoomAllocationController {
    private final RoomAllocationRepo allocationRepo;
    private final HostelService hostelService;

    public RoomAllocationController(RoomAllocationRepo allocationRepo, HostelService hostelService) {
        this.allocationRepo = allocationRepo;
        this.hostelService = hostelService;
    }

    @Cacheable(value = "roomAllocations", key = "'all'")
    @GetMapping
    public List<RoomAllocation> getAllAllocations() {
        return allocationRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomAllocation> getAllocationById(@PathVariable Long id) {
        return allocationRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "roomAllocations", allEntries = true)
    @PostMapping
    public ResponseEntity<RoomAllocation> createAllocation(@RequestBody RoomAllocation allocation) {
        try {
            RoomAllocation saved = hostelService.allocateRoom(allocation);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @CacheEvict(value = "roomAllocations", allEntries = true)
    @PutMapping("/{id}")
    public ResponseEntity<RoomAllocation> updateAllocation(@PathVariable Long id, @RequestBody RoomAllocation updatedAllocation) {
        return allocationRepo.findById(id)
                .map(existing -> {
                    updatedAllocation.setId(id);
                    return ResponseEntity.ok(allocationRepo.save(updatedAllocation));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "roomAllocations", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAllocation(@PathVariable Long id) {
        try {
            hostelService.deallocateRoom(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
