CREATE TABLE IF NOT EXISTS notices (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    notice_title VARCHAR(150) NOT NULL,
    notice_body TEXT NOT NULL,
    target_role VARCHAR(20),
    published_date DATE NOT NULL,
    active BIT(1) NOT NULL DEFAULT b'1',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS grievances (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    subject_line VARCHAR(150) NOT NULL,
    description_text TEXT NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'OPEN',
    raised_by_user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_grievances_user FOREIGN KEY (raised_by_user_id) REFERENCES users(id)
);
