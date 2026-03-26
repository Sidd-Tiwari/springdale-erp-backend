package com.springdale.erp.faculty.repo;

import com.springdale.erp.faculty.entity.Faculty;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    boolean existsByEmployeeCode(String employeeCode);

    boolean existsByEmailIgnoreCase(String email);

    Optional<Faculty> findByEmployeeCode(String employeeCode);
}
