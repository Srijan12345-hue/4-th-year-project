package com.group4.th.year.project.smart.campus.Management.Repo;

import com.group4.th.year.project.smart.campus.Management.TravelAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TravelAllocationRepo extends JpaRepository<TravelAllocation, Long> {
    List<TravelAllocation> findByVehicleId(Long vehicleId);
}
