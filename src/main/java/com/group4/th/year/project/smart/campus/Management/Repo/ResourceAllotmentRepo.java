package com.group4.th.year.project.smart.campus.Management.Repo;

import com.group4.th.year.project.smart.campus.Management.Resource;
import com.group4.th.year.project.smart.campus.Management.ResourceAllotment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResourceAllotmentRepo extends JpaRepository<ResourceAllotment, Long> {
    List<ResourceAllotment> findByResourceAndDayOfWeek(Resource resource, String dayOfWeek);
}
