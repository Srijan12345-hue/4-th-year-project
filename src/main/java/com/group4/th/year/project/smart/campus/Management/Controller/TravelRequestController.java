package com.group4.th.year.project.smart.campus.Management.Controller;

import com.group4.th.year.project.smart.campus.Management.TravelRequest;
import com.group4.th.year.project.smart.campus.Management.Repo.TravelRequestRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/travel-requests")
public class TravelRequestController {
    private final TravelRequestRepo requestRepo;

    public TravelRequestController(TravelRequestRepo requestRepo) {
        this.requestRepo = requestRepo;
    }

    @GetMapping
    public List<TravelRequest> getAllRequests() {
        return requestRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<TravelRequest> createRequest(@RequestBody TravelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(requestRepo.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TravelRequest> updateRequest(@PathVariable Long id, @RequestBody TravelRequest updatedRequest) {
        return requestRepo.findById(id)
                .map(existing -> {
                    updatedRequest.setId(id);
                    return ResponseEntity.ok(requestRepo.save(updatedRequest));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> statusUpdate) {
        String status = statusUpdate.get("status");
        if (status == null) return ResponseEntity.badRequest().build();

        return requestRepo.findById(id)
                .map(req -> {
                    req.setStatus(status);
                    requestRepo.save(req);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        if (!requestRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        requestRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
