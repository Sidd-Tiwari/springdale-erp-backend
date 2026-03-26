package com.springdale.erp.students.service;

import com.springdale.erp.common.api.PageResponse;
import com.springdale.erp.common.exception.DuplicateResourceException;
import com.springdale.erp.common.exception.ResourceNotFoundException;
import com.springdale.erp.students.dto.*;
import com.springdale.erp.students.entity.Guardian;
import com.springdale.erp.students.entity.Student;
import com.springdale.erp.students.repo.GuardianRepository;
import com.springdale.erp.students.repo.StudentRepository;
import com.springdale.erp.users.enums.Role;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Student management business logic.
 */
@Service
@Transactional
public class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;
    private final GuardianRepository guardianRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository,
                          GuardianRepository guardianRepository,
                          PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.guardianRepository = guardianRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public StudentDto create(StudentCreateRequest request) {
        validateUniqueness(request.email(), request.admissionNo(), null);

        Guardian guardian = new Guardian();
        guardian.setGuardianName(request.guardianName());
        guardian.setRelationship(request.guardianRelationship());
        guardian.setPhone(request.guardianPhone());
        guardian.setEmail(request.guardianEmail());
        guardianRepository.save(guardian);

        Student student = new Student();
        student.setFullName(request.fullName());
        student.setEmail(request.email());
        student.setPhone(request.phone());
        student.setPasswordHash(passwordEncoder.encode(request.password()));
        student.setAdmissionNo(request.admissionNo());
        student.setClassName(request.className());
        student.setSection(request.section());
        student.setRollNumber(request.rollNumber());
        student.setDateOfBirth(request.dateOfBirth());
        student.setGender(request.gender());
        student.setAddress(request.address());
        student.setAcademicYear(request.academicYear());
        student.setRole(Role.STUDENT);
        student.setGuardian(guardian);

        Student saved = studentRepository.save(student);
        log.info("Created student id={} admissionNo={}", saved.getId(), saved.getAdmissionNo());
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public StudentDto get(Long id) {
        return toDto(findEntity(id));
    }

    public StudentDto update(Long id, StudentUpdateRequest request) {
        Student student = findEntity(id);
        validateUniqueness(request.email(), student.getAdmissionNo(), id);

        student.setFullName(request.fullName());
        student.setEmail(request.email());
        student.setPhone(request.phone());
        student.setClassName(request.className());
        student.setSection(request.section());
        student.setRollNumber(request.rollNumber());
        student.setDateOfBirth(request.dateOfBirth());
        student.setGender(request.gender());
        student.setAddress(request.address());
        student.setAcademicYear(request.academicYear());
        student.setActive(request.active());

        Guardian guardian = student.getGuardian();
        if (guardian == null) {
            guardian = new Guardian();
            student.setGuardian(guardian);
        }
        guardian.setGuardianName(request.guardianName());
        guardian.setRelationship(request.guardianRelationship());
        guardian.setPhone(request.guardianPhone());
        guardian.setEmail(request.guardianEmail());

        return toDto(student);
    }

    @Transactional(readOnly = true)
    public PageResponse<StudentDto> search(StudentSearchRequest incoming) {
        StudentSearchRequest request = StudentSearchRequest.defaults(incoming);
        Sort sort = Sort.by(Sort.Direction.fromString(request.sortDir()), request.sortBy());
        Pageable pageable = PageRequest.of(request.page(), request.size(), sort);

        Specification<Student> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.keyword() != null && !request.keyword().isBlank()) {
                String like = "%" + request.keyword().trim().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("fullName")), like),
                        cb.like(cb.lower(root.get("email")), like),
                        cb.like(cb.lower(root.get("admissionNo")), like)
                ));
            }
            if (request.className() != null && !request.className().isBlank()) {
                predicates.add(cb.equal(root.get("className"), request.className()));
            }
            if (request.section() != null && !request.section().isBlank()) {
                predicates.add(cb.equal(root.get("section"), request.section()));
            }
            if (request.academicYear() != null && !request.academicYear().isBlank()) {
                predicates.add(cb.equal(root.get("academicYear"), request.academicYear()));
            }
            if (request.active() != null) {
                predicates.add(cb.equal(root.get("active"), request.active()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<StudentDto> page = studentRepository.findAll(specification, pageable).map(this::toDto);
        return PageResponse.from(page, request.sortBy() + "," + request.sortDir());
    }

    public void delete(Long id) {
        Student student = findEntity(id);
        studentRepository.delete(student);
        log.info("Deleted student id={}", id);
    }

    @Transactional(readOnly = true)
    public Student findEntity(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    private void validateUniqueness(String email, String admissionNo, Long ignoreId) {
        studentRepository.findByAdmissionNo(admissionNo)
                .filter(student -> !student.getId().equals(ignoreId))
                .ifPresent(student -> {
                    throw new DuplicateResourceException("Admission number already exists: " + admissionNo);
                });

        studentRepository.findAll((root, query, cb) -> cb.equal(cb.lower(root.get("email")), email.toLowerCase()))
                .stream()
                .filter(student -> !student.getId().equals(ignoreId))
                .findAny()
                .ifPresent(student -> {
                    throw new DuplicateResourceException("Email already exists: " + email);
                });
    }

    public StudentDto toDto(Student student) {
        StudentDto.GuardianDto guardianDto = null;
        if (student.getGuardian() != null) {
            guardianDto = new StudentDto.GuardianDto(
                    student.getGuardian().getId(),
                    student.getGuardian().getGuardianName(),
                    student.getGuardian().getRelationship(),
                    student.getGuardian().getPhone(),
                    student.getGuardian().getEmail()
            );
        }
        return new StudentDto(
                student.getId(),
                student.getFullName(),
                student.getEmail(),
                student.getPhone(),
                student.getAdmissionNo(),
                student.getClassName(),
                student.getSection(),
                student.getRollNumber(),
                student.getDateOfBirth(),
                student.getGender(),
                student.getAddress(),
                student.getAcademicYear(),
                guardianDto
        );
    }
}
