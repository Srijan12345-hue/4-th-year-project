package com.group4.th.year.project.smart.campus.Management.Repo;

import com.group4.th.year.project.smart.campus.Management.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibrarianRepo extends JpaRepository<Librarian, Long> {
    Optional<Librarian> findByEmail(String email);
}
