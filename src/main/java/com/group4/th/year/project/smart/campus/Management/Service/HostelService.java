package com.group4.th.year.project.smart.campus.Management.Service;

import com.group4.th.year.project.smart.campus.Management.Room;
import com.group4.th.year.project.smart.campus.Management.RoomAllocation;
import com.group4.th.year.project.smart.campus.Management.Repo.RoomAllocationRepo;
import com.group4.th.year.project.smart.campus.Management.Repo.RoomRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HostelService {
    private final RoomRepo roomRepo;
    private final RoomAllocationRepo allocationRepo;

    public HostelService(RoomRepo roomRepo, RoomAllocationRepo allocationRepo) {
        this.roomRepo = roomRepo;
        this.allocationRepo = allocationRepo;
    }

    @Transactional
    public RoomAllocation allocateRoom(RoomAllocation allocation) {
        Room room = allocation.getRoom();
        if (room == null) {
            throw new IllegalArgumentException("Room must be specified for allocation.");
        }

        // Check room capacity
        if (room.getCurrentOccupancy() >= room.getCapacity()) {
            throw new IllegalStateException("Room is full.");
        }

        // Check if student already has an active allocation
        if (allocation.getStudent() != null) {
            List<RoomAllocation> activeAllocations = allocationRepo.findByStudentAndStatus(allocation.getStudent(), "ACTIVE");
            if (!activeAllocations.isEmpty()) {
                throw new IllegalStateException("Student already has an active room allocation.");
            }
        }

        // Increment room occupancy
        room.setCurrentOccupancy(room.getCurrentOccupancy() + 1);
        roomRepo.save(room);

        return allocationRepo.save(allocation);
    }

    @Transactional
    public void deallocateRoom(Long allocationId) {
        RoomAllocation allocation = allocationRepo.findById(allocationId)
                .orElseThrow(() -> new IllegalArgumentException("Allocation not found."));

        if ("INACTIVE".equals(allocation.getStatus())) {
            return;
        }

        Room room = allocation.getRoom();
        if (room != null) {
            room.setCurrentOccupancy(Math.max(0, room.getCurrentOccupancy() - 1));
            roomRepo.save(room);
        }

        allocation.setStatus("INACTIVE");
        allocationRepo.save(allocation);
    }
}
