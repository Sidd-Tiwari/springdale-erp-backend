CREATE TABLE IF NOT EXISTS timetable_entries (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    class_name VARCHAR(50) NOT NULL,
    section VARCHAR(10) NOT NULL,
    day_of_week VARCHAR(20) NOT NULL,
    subject_name VARCHAR(100) NOT NULL,
    faculty_name VARCHAR(120) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL
);
