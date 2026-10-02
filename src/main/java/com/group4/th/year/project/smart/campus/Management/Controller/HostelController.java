package com.group4.th.year.project.smart.campus.Management.Controller;

import com.group4.th.year.project.smart.campus.Management.Hostel;
import com.group4.th.year.project.smart.campus.Management.Repo.HostelRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hostels")
public class HostelController {
    private final HostelRepo hostelRepo;

    public HostelController(HostelRepo hostelRepo) {
        this.hostelRepo = hostelRepo;
    }

    @GetMapping
    public List<Hostel> getAllHostels() {
        return hostelRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hostel> getHostelById(@PathVariable Long id) {
        return hostelRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Hostel> createHostel(@RequestBody Hostel hostel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hostelRepo.save(hostel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hostel> updateHostel(@PathVariable Long id, @RequestBody Hostel updatedHostel) {
        return hostelRepo.findById(id)
                .map(existing -> {
                    updatedHostel.setId(id);
                    return ResponseEntity.ok(hostelRepo.save(updatedHostel));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHostel(@PathVariable Long id) {
        if (!hostelRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        hostelRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
