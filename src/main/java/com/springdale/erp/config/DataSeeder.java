package com.springdale.erp.config;

import com.springdale.erp.faculty.dto.FacultyCreateRequest;
import com.springdale.erp.faculty.service.FacultyService;
import com.springdale.erp.fees.dto.FeeStructureDto;
import com.springdale.erp.fees.service.FeeService;
import com.springdale.erp.notices.dto.NoticeDto;
import com.springdale.erp.notices.service.NoticeService;
import com.springdale.erp.students.dto.StudentCreateRequest;
import com.springdale.erp.students.service.StudentService;
import com.springdale.erp.users.entity.User;
import com.springdale.erp.users.enums.Role;
import com.springdale.erp.users.repo.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Seeds basic local development data.
 */
@Configuration
public class DataSeeder {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    @Bean
    CommandLineRunner seed(AppProperties appProperties,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           StudentService studentService,
                           FacultyService facultyService,
                           FeeService feeService,
                           NoticeService noticeService) {
        return args -> {
            if (!appProperties.getBootstrap().isSeedDemoData() || userRepository.count() > 0) {
                return;
            }

            User admin = new User();
            admin.setFullName("System Administrator");
            admin.setEmail("admin@school.local");
            admin.setPhone("9999999999");
            admin.setPasswordHash(passwordEncoder.encode("Admin@123"));
            admin.setRole(Role.ADMIN);
            admin.setActive(true);
            userRepository.save(admin);

            facultyService.create(new FacultyCreateRequest(
                    "Anita Sharma",
                    "faculty1@school.local",
                    "8888888888",
                    "Faculty@123",
                    "FAC-001",
                    "Science",
                    "Teacher",
                    "M.Sc",
                    LocalDate.now().minusYears(3),
                    List.of(new FacultyCreateRequest.FacultySubjectRequest("Mathematics", "10"))
            ));

            studentService.create(new StudentCreateRequest(
                    "Rahul Verma",
                    "student1@school.local",
                    "7777777777",
                    "Student@123",
                    "ADM-001",
                    "10",
                    "A",
                    1,
                    LocalDate.of(2010, 5, 10),
                    "Male",
                    "Varanasi",
                    "2025-26",
                    "Suresh Verma",
                    "Father",
                    "6666666666",
                    "guardian1@school.local"
            ));

            feeService.upsertStructure(new FeeStructureDto(
                    null, "10", "2025-26",
                    new BigDecimal("2500"), new BigDecimal("500"), new BigDecimal("300"), new BigDecimal("200"), null
            ));

            noticeService.save(new NoticeDto(
                    null,
                    "Welcome to School ERP",
                    "This is the initial notice seeded for local development.",
                    null,
                    LocalDate.now(),
                    true
            ));

            log.info("Seeded demo data. Admin login: admin@school.local / Admin@123");
        };
    }
}
