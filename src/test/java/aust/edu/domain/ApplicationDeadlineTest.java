package aust.edu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import aust.edu.web.rest.TestUtil;

public class ApplicationDeadlineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationDeadline.class);
        ApplicationDeadline applicationDeadline1 = new ApplicationDeadline();
        applicationDeadline1.setId(1L);
        ApplicationDeadline applicationDeadline2 = new ApplicationDeadline();
        applicationDeadline2.setId(applicationDeadline1.getId());
        assertThat(applicationDeadline1).isEqualTo(applicationDeadline2);
        applicationDeadline2.setId(2L);
        assertThat(applicationDeadline1).isNotEqualTo(applicationDeadline2);
        applicationDeadline1.setId(null);
        assertThat(applicationDeadline1).isNotEqualTo(applicationDeadline2);
    }
}
