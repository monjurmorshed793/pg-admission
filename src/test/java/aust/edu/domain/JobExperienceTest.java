package aust.edu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import aust.edu.web.rest.TestUtil;

public class JobExperienceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobExperience.class);
        JobExperience jobExperience1 = new JobExperience();
        jobExperience1.setId(1L);
        JobExperience jobExperience2 = new JobExperience();
        jobExperience2.setId(jobExperience1.getId());
        assertThat(jobExperience1).isEqualTo(jobExperience2);
        jobExperience2.setId(2L);
        assertThat(jobExperience1).isNotEqualTo(jobExperience2);
        jobExperience1.setId(null);
        assertThat(jobExperience1).isNotEqualTo(jobExperience2);
    }
}
