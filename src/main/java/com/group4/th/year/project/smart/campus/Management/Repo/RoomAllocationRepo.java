package com.group4.th.year.project.smart.campus.Management.Repo;

import com.group4.th.year.project.smart.campus.Management.RoomAllocation;
import com.group4.th.year.project.smart.campus.Management.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoomAllocationRepo extends JpaRepository<RoomAllocation, Long> {
    List<RoomAllocation> findByStudentAndStatus(Student student, String status);
}
