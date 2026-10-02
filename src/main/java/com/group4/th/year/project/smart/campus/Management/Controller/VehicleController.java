package com.group4.th.year.project.smart.campus.Management.Controller;

import com.group4.th.year.project.smart.campus.Management.Vehicle;
import com.group4.th.year.project.smart.campus.Management.Repo.VehicleRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleRepo vehicleRepo;

    public VehicleController(VehicleRepo vehicleRepo) {
        this.vehicleRepo = vehicleRepo;
    }

    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleRepo.save(vehicle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @RequestBody Vehicle updatedVehicle) {
        return vehicleRepo.findById(id)
                .map(existing -> {
                    updatedVehicle.setId(id);
                    return ResponseEntity.ok(vehicleRepo.save(updatedVehicle));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        if (!vehicleRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        vehicleRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
