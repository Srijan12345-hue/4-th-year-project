package com.group4.th.year.project.smart.campus.Management.Repo;

import com.group4.th.year.project.smart.campus.Management.TravelRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRequestRepo extends JpaRepository<TravelRequest, Long> {
}
