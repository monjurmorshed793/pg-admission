package aust.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import aust.edu.domain.enumeration.ApplicationStatus;

/**
 * A Applicant.
 */
@Entity
@Table(name = "mst_applicant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Applicant extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "application_serial", nullable = false)
    private Integer applicationSerial;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ApplicationStatus status;

    @Column(name = "applied_on")
    private Instant appliedOn;

    @Column(name = "application_fee_paid_on")
    private Instant applicationFeePaidOn;

    @Column(name = "selected_rejected_on")
    private Instant selectedRejectedOn;

    @OneToMany(mappedBy = "applicant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ApplicantEducationalInformation> applicantEducationalInformations = new HashSet<>();

    @OneToMany(mappedBy = "applicant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<JobExperience> jobExperiences = new HashSet<>();

    @OneToMany(mappedBy = "applicant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ApplicantAddress> applicantAddresses = new HashSet<>();

    @OneToOne(mappedBy = "applicant")
    @JsonIgnore
    private ApplicantPersonalInfo applicantPersonalInformation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getApplicationSerial() {
        return applicationSerial;
    }

    public Applicant applicationSerial(Integer applicationSerial) {
        this.applicationSerial = applicationSerial;
        return this;
    }

    public void setApplicationSerial(Integer applicationSerial) {
        this.applicationSerial = applicationSerial;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public Applicant status(ApplicationStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public Instant getAppliedOn() {
        return appliedOn;
    }

    public Applicant appliedOn(Instant appliedOn) {
        this.appliedOn = appliedOn;
        return this;
    }

    public void setAppliedOn(Instant appliedOn) {
        this.appliedOn = appliedOn;
    }

    public Instant getApplicationFeePaidOn() {
        return applicationFeePaidOn;
    }

    public Applicant applicationFeePaidOn(Instant applicationFeePaidOn) {
        this.applicationFeePaidOn = applicationFeePaidOn;
        return this;
    }

    public void setApplicationFeePaidOn(Instant applicationFeePaidOn) {
        this.applicationFeePaidOn = applicationFeePaidOn;
    }

    public Instant getSelectedRejectedOn() {
        return selectedRejectedOn;
    }

    public Applicant selectedRejectedOn(Instant selectedRejectedOn) {
        this.selectedRejectedOn = selectedRejectedOn;
        return this;
    }

    public void setSelectedRejectedOn(Instant selectedRejectedOn) {
        this.selectedRejectedOn = selectedRejectedOn;
    }

    public Set<ApplicantEducationalInformation> getApplicantEducationalInformations() {
        return applicantEducationalInformations;
    }

    public Applicant applicantEducationalInformations(Set<ApplicantEducationalInformation> applicantEducationalInformations) {
        this.applicantEducationalInformations = applicantEducationalInformations;
        return this;
    }

    public Applicant addApplicantEducationalInformation(ApplicantEducationalInformation applicantEducationalInformation) {
        this.applicantEducationalInformations.add(applicantEducationalInformation);
        applicantEducationalInformation.setApplicant(this);
        return this;
    }

    public Applicant removeApplicantEducationalInformation(ApplicantEducationalInformation applicantEducationalInformation) {
        this.applicantEducationalInformations.remove(applicantEducationalInformation);
        applicantEducationalInformation.setApplicant(null);
        return this;
    }

    public void setApplicantEducationalInformations(Set<ApplicantEducationalInformation> applicantEducationalInformations) {
        this.applicantEducationalInformations = applicantEducationalInformations;
    }

    public Set<JobExperience> getJobExperiences() {
        return jobExperiences;
    }

    public Applicant jobExperiences(Set<JobExperience> jobExperiences) {
        this.jobExperiences = jobExperiences;
        return this;
    }

    public Applicant addJobExperience(JobExperience jobExperience) {
        this.jobExperiences.add(jobExperience);
        jobExperience.setApplicant(this);
        return this;
    }

    public Applicant removeJobExperience(JobExperience jobExperience) {
        this.jobExperiences.remove(jobExperience);
        jobExperience.setApplicant(null);
        return this;
    }

    public void setJobExperiences(Set<JobExperience> jobExperiences) {
        this.jobExperiences = jobExperiences;
    }

    public Set<ApplicantAddress> getApplicantAddresses() {
        return applicantAddresses;
    }

    public Applicant applicantAddresses(Set<ApplicantAddress> applicantAddresses) {
        this.applicantAddresses = applicantAddresses;
        return this;
    }

    public Applicant addApplicantAddress(ApplicantAddress applicantAddress) {
        this.applicantAddresses.add(applicantAddress);
        applicantAddress.setApplicant(this);
        return this;
    }

    public Applicant removeApplicantAddress(ApplicantAddress applicantAddress) {
        this.applicantAddresses.remove(applicantAddress);
        applicantAddress.setApplicant(null);
        return this;
    }

    public void setApplicantAddresses(Set<ApplicantAddress> applicantAddresses) {
        this.applicantAddresses = applicantAddresses;
    }

    public ApplicantPersonalInfo getApplicantPersonalInformation() {
        return applicantPersonalInformation;
    }

    public Applicant applicantPersonalInformation(ApplicantPersonalInfo applicantPersonalInfo) {
        this.applicantPersonalInformation = applicantPersonalInfo;
        return this;
    }

    public void setApplicantPersonalInformation(ApplicantPersonalInfo applicantPersonalInfo) {
        this.applicantPersonalInformation = applicantPersonalInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Applicant)) {
            return false;
        }
        return id != null && id.equals(((Applicant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Applicant{" +
            "id=" + getId() +
            ", applicationSerial=" + getApplicationSerial() +
            ", status='" + getStatus() + "'" +
            ", appliedOn='" + getAppliedOn() + "'" +
            ", applicationFeePaidOn='" + getApplicationFeePaidOn() + "'" +
            ", selectedRejectedOn='" + getSelectedRejectedOn() + "'" +
            "}";
    }
}
