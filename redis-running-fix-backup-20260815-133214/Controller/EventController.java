package com.group4.th.year.project.smart.campus.Management.Controller;

import com.group4.th.year.project.smart.campus.Management.Event;
import com.group4.th.year.project.smart.campus.Management.Repo.EventRepo;
<<<<<<< HEAD
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
=======
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
>>>>>>> origin/master
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventRepo eventRepo;

    public EventController(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

<<<<<<< HEAD
    @Cacheable(value = "events", key = "'all'")
=======
    @Cacheable(value = "events", key = "'all'")
>>>>>>> origin/master


    @GetMapping public List<Event> getEvents() {
        return eventRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

<<<<<<< HEAD
    @CacheEvict(value = "events", allEntries = true)
=======
    @CacheEvict(value = "events", allEntries = true)
>>>>>>> origin/master
    @PostMapping
    public ResponseEntity<Event> addEvent(@RequestBody Event event) {
        event.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventRepo.save(event));
    }

<<<<<<< HEAD
    @CacheEvict(value = "events", allEntries = true)
=======
    @CacheEvict(value = "events", allEntries = true)
>>>>>>> origin/master
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

<<<<<<< HEAD
    @CacheEvict(value = "events", allEntries = true)
=======
    @CacheEvict(value = "events", allEntries = true)
>>>>>>> origin/master
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        if (!eventRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        eventRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
<<<<<<< HEAD

=======

>>>>>>> origin/master
