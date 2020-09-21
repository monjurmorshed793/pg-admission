package aust.edu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import aust.edu.web.rest.TestUtil;

public class ExamTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamType.class);
        ExamType examType1 = new ExamType();
        examType1.setId(1L);
        ExamType examType2 = new ExamType();
        examType2.setId(examType1.getId());
        assertThat(examType1).isEqualTo(examType2);
        examType2.setId(2L);
        assertThat(examType1).isNotEqualTo(examType2);
        examType1.setId(null);
        assertThat(examType1).isNotEqualTo(examType2);
    }
}
