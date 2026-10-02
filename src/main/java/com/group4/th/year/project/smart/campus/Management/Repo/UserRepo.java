package com.group4.th.year.project.smart.campus.Management.Repo;

import com.group4.th.year.project.smart.campus.Management.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
