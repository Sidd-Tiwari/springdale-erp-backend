package com.springdale.erp.students;

import static org.assertj.core.api.Assertions.assertThat;
import com.springdale.erp.students.dto.StudentSearchRequest;
import org.junit.jupiter.api.Test;

class StudentServiceTest {

    @Test
    void defaultsShouldNormalizePageAndSort() {
        StudentSearchRequest input = new StudentSearchRequest(null, null, null, null, null, -1, 0, null, null);
        StudentSearchRequest normalized = StudentSearchRequest.defaults(input);
        assertThat(normalized.page()).isZero();
        assertThat(normalized.size()).isEqualTo(10);
        assertThat(normalized.sortBy()).isEqualTo("createdAt");
    }
}
