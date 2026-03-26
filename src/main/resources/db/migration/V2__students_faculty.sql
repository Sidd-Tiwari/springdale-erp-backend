CREATE TABLE IF NOT EXISTS guardians (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    guardian_name VARCHAR(120) NOT NULL,
    relationship VARCHAR(20) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(120),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS students (
    user_id BIGINT PRIMARY KEY,
    admission_no VARCHAR(50) NOT NULL UNIQUE,
    class_name VARCHAR(50) NOT NULL,
    section VARCHAR(10) NOT NULL,
    roll_number INT NOT NULL,
    date_of_birth DATE NULL,
    gender VARCHAR(20),
    address VARCHAR(255),
    academic_year VARCHAR(20) NOT NULL,
    guardian_id BIGINT,
    CONSTRAINT fk_students_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_students_guardian FOREIGN KEY (guardian_id) REFERENCES guardians(id)
);

CREATE TABLE IF NOT EXISTS student_documents (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    document_name VARCHAR(100) NOT NULL,
    document_url VARCHAR(255) NOT NULL,
    student_id BIGINT NOT NULL,
    uploaded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_student_documents_student FOREIGN KEY (student_id) REFERENCES students(user_id)
);

CREATE TABLE IF NOT EXISTS faculty (
    user_id BIGINT PRIMARY KEY,
    employee_code VARCHAR(50) NOT NULL UNIQUE,
    department VARCHAR(100) NOT NULL,
    designation VARCHAR(120),
    qualification VARCHAR(120),
    joining_date DATE NULL,
    CONSTRAINT fk_faculty_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS faculty_subjects (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    subject_name VARCHAR(100) NOT NULL,
    class_name VARCHAR(50) NOT NULL,
    faculty_id BIGINT NOT NULL,
    CONSTRAINT fk_faculty_subjects_faculty FOREIGN KEY (faculty_id) REFERENCES faculty(user_id)
);
