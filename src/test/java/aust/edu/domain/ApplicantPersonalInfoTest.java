package aust.edu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import aust.edu.web.rest.TestUtil;

public class ApplicantPersonalInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantPersonalInfo.class);
        ApplicantPersonalInfo applicantPersonalInfo1 = new ApplicantPersonalInfo();
        applicantPersonalInfo1.setId(1L);
        ApplicantPersonalInfo applicantPersonalInfo2 = new ApplicantPersonalInfo();
        applicantPersonalInfo2.setId(applicantPersonalInfo1.getId());
        assertThat(applicantPersonalInfo1).isEqualTo(applicantPersonalInfo2);
        applicantPersonalInfo2.setId(2L);
        assertThat(applicantPersonalInfo1).isNotEqualTo(applicantPersonalInfo2);
        applicantPersonalInfo1.setId(null);
        assertThat(applicantPersonalInfo1).isNotEqualTo(applicantPersonalInfo2);
    }
}
