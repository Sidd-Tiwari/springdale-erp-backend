package com.springdale.erp.students.repo;

import com.springdale.erp.students.entity.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    boolean existsByAdmissionNo(String admissionNo);

    boolean existsByEmailIgnoreCase(String email);

    Optional<Student> findByAdmissionNo(String admissionNo);

    long countByClassNameAndSectionAndActiveTrue(String className, String section);
}
