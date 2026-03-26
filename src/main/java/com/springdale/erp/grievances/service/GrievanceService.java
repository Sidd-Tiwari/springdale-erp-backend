package com.springdale.erp.grievances.service;

import com.springdale.erp.common.exception.ResourceNotFoundException;
import com.springdale.erp.grievances.dto.GrievanceDto;
import com.springdale.erp.grievances.entity.Grievance;
import com.springdale.erp.grievances.repo.GrievanceRepository;
import com.springdale.erp.users.entity.User;
import com.springdale.erp.users.repo.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GrievanceService {

    private final GrievanceRepository grievanceRepository;
    private final UserRepository userRepository;

    public GrievanceService(GrievanceRepository grievanceRepository, UserRepository userRepository) {
        this.grievanceRepository = grievanceRepository;
        this.userRepository = userRepository;
    }

    public GrievanceDto create(Long raisedByUserId, GrievanceDto request) {
        User user = userRepository.findById(raisedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + raisedByUserId));
        Grievance grievance = new Grievance();
        grievance.setSubjectLine(request.subjectLine());
        grievance.setDescriptionText(request.descriptionText());
        grievance.setStatus("OPEN");
        grievance.setRaisedBy(user);
        return toDto(grievanceRepository.save(grievance));
    }

    @Transactional(readOnly = true)
    public List<GrievanceDto> getAll() {
        return grievanceRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<GrievanceDto> getMine(Long userId) {
        return grievanceRepository.findByRaisedById(userId).stream().map(this::toDto).toList();
    }

    public GrievanceDto updateStatus(Long id, String status) {
        Grievance grievance = grievanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grievance not found with id: " + id));
        grievance.setStatus(status);
        return toDto(grievance);
    }

    private GrievanceDto toDto(Grievance grievance) {
        return new GrievanceDto(
                grievance.getId(),
                grievance.getSubjectLine(),
                grievance.getDescriptionText(),
                grievance.getStatus(),
                grievance.getRaisedBy().getId(),
                grievance.getRaisedBy().getFullName()
        );
    }
}
