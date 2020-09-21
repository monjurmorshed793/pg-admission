package aust.edu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import aust.edu.web.rest.TestUtil;

public class ApplicantAddressTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantAddress.class);
        ApplicantAddress applicantAddress1 = new ApplicantAddress();
        applicantAddress1.setId(1L);
        ApplicantAddress applicantAddress2 = new ApplicantAddress();
        applicantAddress2.setId(applicantAddress1.getId());
        assertThat(applicantAddress1).isEqualTo(applicantAddress2);
        applicantAddress2.setId(2L);
        assertThat(applicantAddress1).isNotEqualTo(applicantAddress2);
        applicantAddress1.setId(null);
        assertThat(applicantAddress1).isNotEqualTo(applicantAddress2);
    }
}
