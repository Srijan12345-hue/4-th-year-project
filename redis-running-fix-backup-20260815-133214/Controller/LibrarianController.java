package com.group4.th.year.project.smart.campus.Management.Controller;

import com.group4.th.year.project.smart.campus.Management.Librarian;
import com.group4.th.year.project.smart.campus.Management.Repo.LibrarianRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/librarians")
public class LibrarianController {
    private final LibrarianRepo librarianRepo;
    private final PasswordEncoder passwordEncoder;

    public LibrarianController(LibrarianRepo librarianRepo, PasswordEncoder passwordEncoder) {
        this.librarianRepo = librarianRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<Librarian> getLibrarians() {
        return librarianRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Librarian> getLibrarianById(@PathVariable Long id) {
        return librarianRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Librarian> addLibrarian(@RequestBody Librarian librarian) {
        if (librarian.getPassword() == null || librarian.getPassword().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        librarian.setId(null);
        librarian.setPassword(passwordEncoder.encode(librarian.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(librarianRepo.save(librarian));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Librarian> updateLibrarian(@PathVariable Long id, @RequestBody Librarian updatedLibrarian) {
        return librarianRepo.findById(id)
                .map(librarian -> {
                    librarian.setEmpId(updatedLibrarian.getEmpId());
                    librarian.setName(updatedLibrarian.getName());
                    librarian.setEmail(updatedLibrarian.getEmail());
                    if (updatedLibrarian.getPassword() != null && !updatedLibrarian.getPassword().isBlank()) {
                        librarian.setPassword(passwordEncoder.encode(updatedLibrarian.getPassword()));
                    }
                    librarian.setDepartment(updatedLibrarian.getDepartment());
                    return ResponseEntity.ok(librarianRepo.save(librarian));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibrarian(@PathVariable Long id) {
        if (!librarianRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        librarianRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
