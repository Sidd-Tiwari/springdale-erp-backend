package com.springdale.erp.faculty.service;

import com.springdale.erp.common.exception.DuplicateResourceException;
import com.springdale.erp.common.exception.ResourceNotFoundException;
import com.springdale.erp.faculty.dto.FacultyCreateRequest;
import com.springdale.erp.faculty.dto.FacultyDto;
import com.springdale.erp.faculty.entity.Faculty;
import com.springdale.erp.faculty.entity.FacultySubject;
import com.springdale.erp.faculty.repo.FacultyRepository;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final PasswordEncoder passwordEncoder;

    public FacultyService(FacultyRepository facultyRepository, PasswordEncoder passwordEncoder) {
        this.facultyRepository = facultyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public FacultyDto create(FacultyCreateRequest request) {
        if (facultyRepository.existsByEmployeeCode(request.employeeCode())) {
            throw new DuplicateResourceException("Employee code already exists: " + request.employeeCode());
        }
        if (facultyRepository.existsByEmailIgnoreCase(request.email())) {
            throw new DuplicateResourceException("Email already exists: " + request.email());
        }

        Faculty faculty = new Faculty();
        faculty.setFullName(request.fullName());
        faculty.setEmail(request.email());
        faculty.setPhone(request.phone());
        faculty.setPasswordHash(passwordEncoder.encode(request.password()));
        faculty.setEmployeeCode(request.employeeCode());
        faculty.setDepartment(request.department());
        faculty.setDesignation(request.designation());
        faculty.setQualification(request.qualification());
        faculty.setJoiningDate(request.joiningDate());

        if (request.subjects() != null) {
            request.subjects().forEach(subjectRequest -> {
                FacultySubject subject = new FacultySubject();
                subject.setFaculty(faculty);
                subject.setSubjectName(subjectRequest.subjectName());
                subject.setClassName(subjectRequest.className());
                faculty.getSubjects().add(subject);
            });
        }

        return toDto(facultyRepository.save(faculty));
    }

    @Transactional(readOnly = true)
    public List<FacultyDto> getAll() {
        return facultyRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public FacultyDto get(Long id) {
        return toDto(findEntity(id));
    }

    public void delete(Long id) {
        facultyRepository.delete(findEntity(id));
    }

    @Transactional(readOnly = true)
    public Faculty findEntity(Long id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id: " + id));
    }

    public FacultyDto toDto(Faculty faculty) {
        return new FacultyDto(
                faculty.getId(),
                faculty.getFullName(),
                faculty.getEmail(),
                faculty.getPhone(),
                faculty.getEmployeeCode(),
                faculty.getDepartment(),
                faculty.getDesignation(),
                faculty.getQualification(),
                faculty.getJoiningDate(),
                faculty.getSubjects().stream()
                        .map(subject -> new FacultyDto.FacultySubjectDto(subject.getSubjectName(), subject.getClassName()))
                        .toList()
        );
    }
}
