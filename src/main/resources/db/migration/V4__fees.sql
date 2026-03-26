CREATE TABLE IF NOT EXISTS fee_structures (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    class_name VARCHAR(50) NOT NULL,
    academic_year VARCHAR(20) NOT NULL,
    tuition_fee DECIMAL(12,2) NOT NULL,
    transport_fee DECIMAL(12,2) DEFAULT 0,
    exam_fee DECIMAL(12,2) DEFAULT 0,
    misc_fee DECIMAL(12,2) DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_fee_structure_class_year UNIQUE (class_name, academic_year)
);

CREATE TABLE IF NOT EXISTS fee_payments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    fee_month VARCHAR(7) NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    payment_date DATE NULL,
    payment_mode VARCHAR(30),
    transaction_ref VARCHAR(100),
    remarks VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_fee_payments_student FOREIGN KEY (student_id) REFERENCES students(user_id)
);

CREATE TABLE IF NOT EXISTS fee_receipts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    receipt_no VARCHAR(50) NOT NULL UNIQUE,
    fee_payment_id BIGINT NOT NULL UNIQUE,
    generated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_fee_receipts_payment FOREIGN KEY (fee_payment_id) REFERENCES fee_payments(id)
);
