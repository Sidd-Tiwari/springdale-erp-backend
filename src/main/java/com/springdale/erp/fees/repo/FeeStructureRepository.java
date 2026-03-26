package com.springdale.erp.fees.repo;

import com.springdale.erp.fees.entity.FeeStructure;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeeStructureRepository extends JpaRepository<FeeStructure, Long> {
    Optional<FeeStructure> findByClassNameAndAcademicYear(String className, String academicYear);
}
