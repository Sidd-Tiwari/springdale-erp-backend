CREATE TABLE IF NOT EXISTS exams (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    exam_name VARCHAR(100) NOT NULL,
    class_name VARCHAR(50) NOT NULL,
    academic_year VARCHAR(20) NOT NULL,
    start_date DATE NULL,
    end_date DATE NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS exam_schedules (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    subject_name VARCHAR(100) NOT NULL,
    exam_date DATE NOT NULL,
    start_time TIME NULL,
    end_time TIME NULL,
    exam_id BIGINT NOT NULL,
    CONSTRAINT fk_exam_schedules_exam FOREIGN KEY (exam_id) REFERENCES exams(id)
);

CREATE TABLE IF NOT EXISTS exam_results (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    exam_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    subject_name VARCHAR(100) NOT NULL,
    max_marks DECIMAL(10,2) NOT NULL,
    obtained_marks DECIMAL(10,2) NOT NULL,
    CONSTRAINT uk_exam_result_exam_student_subject UNIQUE (exam_id, student_id, subject_name),
    CONSTRAINT fk_exam_results_exam FOREIGN KEY (exam_id) REFERENCES exams(id),
    CONSTRAINT fk_exam_results_student FOREIGN KEY (student_id) REFERENCES students(user_id)
);
