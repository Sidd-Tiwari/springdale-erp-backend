package com.springdale.erp.grievances.repo;

import com.springdale.erp.grievances.entity.Grievance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrievanceRepository extends JpaRepository<Grievance, Long> {
    List<Grievance> findByRaisedById(Long raisedById);
}
