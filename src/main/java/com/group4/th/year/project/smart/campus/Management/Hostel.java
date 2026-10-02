package com.group4.th.year.project.smart.campus.Management;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "hostels")
public class Hostel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private String type; // BOYS, GIRLS

    @OneToMany(mappedBy = "hostel", cascade = CascadeType.ALL)
    private List<Room> rooms;

    public Hostel() {}

    public Hostel(Long id, String name, String location, String type) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.type = type;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public List<Room> getRooms() { return rooms; }
    public void setRooms(List<Room> rooms) { this.rooms = rooms; }
}
