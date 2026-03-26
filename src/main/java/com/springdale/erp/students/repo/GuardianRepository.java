package com.springdale.erp.students.repo;

import com.springdale.erp.students.entity.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuardianRepository extends JpaRepository<Guardian, Long> {
}
