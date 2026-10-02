package com.group4.th.year.project.smart.campus.Management.Controller;

import com.group4.th.year.project.smart.campus.Management.LogEntry;
import com.group4.th.year.project.smart.campus.Management.Repo.LogEntryRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogEntryController {
    private final LogEntryRepo logEntryRepo;

    public LogEntryController(LogEntryRepo logEntryRepo) {
        this.logEntryRepo = logEntryRepo;
    }

    @Cacheable(value = "logs", key = "'all'")


    @GetMapping public List<LogEntry> getLogs() {
        return logEntryRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogEntry> getLogById(@PathVariable Long id) {
        return logEntryRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "logs", allEntries = true)
    @PostMapping
    public ResponseEntity<LogEntry> addLog(@RequestBody LogEntry logEntry) {
        logEntry.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(logEntryRepo.save(logEntry));
    }

    @CacheEvict(value = "logs", allEntries = true)
    @PutMapping("/{id}")
    public ResponseEntity<LogEntry> updateLog(@PathVariable Long id, @RequestBody LogEntry updatedLogEntry) {
        return logEntryRepo.findById(id)
                .map(logEntry -> {
                    logEntry.setUserId(updatedLogEntry.getUserId());
                    logEntry.setAction(updatedLogEntry.getAction());
                    logEntry.setModule(updatedLogEntry.getModule());
                    logEntry.setTimestamp(updatedLogEntry.getTimestamp());
                    logEntry.setIpAddress(updatedLogEntry.getIpAddress());
                    return ResponseEntity.ok(logEntryRepo.save(logEntry));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "logs", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable Long id) {
        if (!logEntryRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        logEntryRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

