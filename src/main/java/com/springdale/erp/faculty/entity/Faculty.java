package com.springdale.erp.faculty.entity;

import com.springdale.erp.users.entity.User;
import com.springdale.erp.users.enums.Role;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "faculty", indexes = {
        @Index(name = "idx_faculty_employee_code", columnList = "employee_code", unique = true),
        @Index(name = "idx_faculty_department", columnList = "department")
})
@PrimaryKeyJoinColumn(name = "user_id")
public class Faculty extends User {

    @Column(name = "employee_code", nullable = false, unique = true, length = 50)
    private String employeeCode;

    @Column(nullable = false, length = 100)
    private String department;

    @Column(length = 120)
    private String designation;

    @Column(length = 120)
    private String qualification;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FacultySubject> subjects = new ArrayList<>();

    public Faculty() {
        setRole(Role.FACULTY);
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public List<FacultySubject> getSubjects() {
        return subjects;
    }
}
