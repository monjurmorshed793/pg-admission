package aust.edu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import aust.edu.web.rest.TestUtil;

public class ApplicantEducationalInformationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantEducationalInformation.class);
        ApplicantEducationalInformation applicantEducationalInformation1 = new ApplicantEducationalInformation();
        applicantEducationalInformation1.setId(1L);
        ApplicantEducationalInformation applicantEducationalInformation2 = new ApplicantEducationalInformation();
        applicantEducationalInformation2.setId(applicantEducationalInformation1.getId());
        assertThat(applicantEducationalInformation1).isEqualTo(applicantEducationalInformation2);
        applicantEducationalInformation2.setId(2L);
        assertThat(applicantEducationalInformation1).isNotEqualTo(applicantEducationalInformation2);
        applicantEducationalInformation1.setId(null);
        assertThat(applicantEducationalInformation1).isNotEqualTo(applicantEducationalInformation2);
    }
}
