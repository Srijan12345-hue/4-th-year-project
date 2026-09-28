package com.group4.th.year.project.smart.campus.Management;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomNumber;
    private int capacity;
    private int currentOccupancy;
    private String type; // AC, NON_AC

    @ManyToOne
    @JoinColumn(name = "hostel_id")
    private Hostel hostel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RoomAllocation> allocations;

    public Room() {}

    public Room(Long id, String roomNumber, int capacity, int currentOccupancy, String type, Hostel hostel) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.currentOccupancy = currentOccupancy;
        this.type = type;
        this.hostel = hostel;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getCurrentOccupancy() { return currentOccupancy; }
    public void setCurrentOccupancy(int currentOccupancy) { this.currentOccupancy = currentOccupancy; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Hostel getHostel() { return hostel; }
    public void setHostel(Hostel hostel) { this.hostel = hostel; }

    public List<RoomAllocation> getAllocations() { return allocations; }
    public void setAllocations(List<RoomAllocation> allocations) { this.allocations = allocations; }
}
