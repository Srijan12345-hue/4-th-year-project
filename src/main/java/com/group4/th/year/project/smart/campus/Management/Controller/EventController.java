package com.group4.th.year.project.smart.campus.Management.Controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import com.group4.th.year.project.smart.campus.Management.Event;
import com.group4.th.year.project.smart.campus.Management.Repo.EventRepo;
import com.group4.th.year.project.smart.campus.Management.Service.FileStorageService;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventRepo eventRepo;
    private final FileStorageService fileStorageService;

    public EventController(EventRepo eventRepo, FileStorageService fileStorageService) {
        this.eventRepo = eventRepo;
        this.fileStorageService = fileStorageService;
    }

    @Cacheable(value = "events", key = "'all'")
    @GetMapping
    public List<Event> getEvents() {
        return eventRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "events", allEntries = true)
    @PostMapping
    public ResponseEntity<Event> addEvent(@RequestBody Event event) {
        event.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventRepo.save(event));
    }

    @CacheEvict(value = "events", allEntries = true)
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event updatedEvent) {
        return eventRepo.findById(id)
                .map(event -> {
                    event.setTitle(updatedEvent.getTitle());
                    event.setStartTime(updatedEvent.getStartTime());
                    event.setEndTime(updatedEvent.getEndTime());
                    event.setVenue(updatedEvent.getVenue());
                    return ResponseEntity.ok(eventRepo.save(event));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/{id}/attachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAttachment(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return eventRepo.findById(id).map(event -> {
            try {
                event.setAttachmentPath(fileStorageService.storePdf(file));
                event.setAttachmentName(file.getOriginalFilename());
                return ResponseEntity.ok(eventRepo.save(event));
            } catch (Exception exception) {
                return ResponseEntity.badRequest().body(exception.getMessage());
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/attachment")
    public ResponseEntity<?> downloadAttachment(@PathVariable Long id) {
        return eventRepo.findById(id).filter(event -> event.getAttachmentPath() != null).map(event -> {
            try {
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + event.getAttachmentName() + "\"")
                        .body(new UrlResource(fileStorageService.getFile(event.getAttachmentPath()).toUri()));
            } catch (Exception exception) {
                return ResponseEntity.notFound().build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "events", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        if (!eventRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        eventRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
