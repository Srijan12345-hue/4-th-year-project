package com.group4.th.year.project.smart.campus.Management.Repo;

import com.group4.th.year.project.smart.campus.Management.CanteenOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanteenOrderRepo extends JpaRepository<CanteenOrder, Long> {
}
