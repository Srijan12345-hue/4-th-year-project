package com.group4.th.year.project.smart.campus.Management;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "room_allocations")
public class RoomAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime allocationDate;
    private String status; // ACTIVE, INACTIVE

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public RoomAllocation() {}

    public RoomAllocation(Long id, LocalDateTime allocationDate, String status, Student student, Room room) {
        this.id = id;
        this.allocationDate = allocationDate;
        this.status = status;
        this.student = student;
        this.room = room;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getAllocationDate() { return allocationDate; }
    public void setAllocationDate(LocalDateTime allocationDate) { this.allocationDate = allocationDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
}
