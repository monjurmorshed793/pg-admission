package aust.edu.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import aust.edu.domain.enumeration.ApplicationStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link aust.edu.domain.Applicant} entity. This class is used
 * in {@link aust.edu.web.rest.ApplicantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /applicants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApplicantCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ApplicationStatus
     */
    public static class ApplicationStatusFilter extends Filter<ApplicationStatus> {

        public ApplicationStatusFilter() {
        }

        public ApplicationStatusFilter(ApplicationStatusFilter filter) {
            super(filter);
        }

        @Override
        public ApplicationStatusFilter copy() {
            return new ApplicationStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter applicationSerial;

    private ApplicationStatusFilter status;

    private InstantFilter appliedOn;

    private InstantFilter applicationFeePaidOn;

    private InstantFilter selectedRejectedOn;

    private LongFilter applicantEducationalInformationId;

    private LongFilter jobExperienceId;

    private LongFilter applicantAddressId;

    private LongFilter semesterId;

    private LongFilter programId;

    private LongFilter applicantPersonalInformationId;

    public ApplicantCriteria() {
    }

    public ApplicantCriteria(ApplicantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.applicationSerial = other.applicationSerial == null ? null : other.applicationSerial.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.appliedOn = other.appliedOn == null ? null : other.appliedOn.copy();
        this.applicationFeePaidOn = other.applicationFeePaidOn == null ? null : other.applicationFeePaidOn.copy();
        this.selectedRejectedOn = other.selectedRejectedOn == null ? null : other.selectedRejectedOn.copy();
        this.applicantEducationalInformationId = other.applicantEducationalInformationId == null ? null : other.applicantEducationalInformationId.copy();
        this.jobExperienceId = other.jobExperienceId == null ? null : other.jobExperienceId.copy();
        this.applicantAddressId = other.applicantAddressId == null ? null : other.applicantAddressId.copy();
        this.semesterId = other.semesterId == null ? null : other.semesterId.copy();
        this.programId = other.programId == null ? null : other.programId.copy();
        this.applicantPersonalInformationId = other.applicantPersonalInformationId == null ? null : other.applicantPersonalInformationId.copy();
    }

    @Override
    public ApplicantCriteria copy() {
        return new ApplicantCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getApplicationSerial() {
        return applicationSerial;
    }

    public void setApplicationSerial(IntegerFilter applicationSerial) {
        this.applicationSerial = applicationSerial;
    }

    public ApplicationStatusFilter getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatusFilter status) {
        this.status = status;
    }

    public InstantFilter getAppliedOn() {
        return appliedOn;
    }

    public void setAppliedOn(InstantFilter appliedOn) {
        this.appliedOn = appliedOn;
    }

    public InstantFilter getApplicationFeePaidOn() {
        return applicationFeePaidOn;
    }

    public void setApplicationFeePaidOn(InstantFilter applicationFeePaidOn) {
        this.applicationFeePaidOn = applicationFeePaidOn;
    }

    public InstantFilter getSelectedRejectedOn() {
        return selectedRejectedOn;
    }

    public void setSelectedRejectedOn(InstantFilter selectedRejectedOn) {
        this.selectedRejectedOn = selectedRejectedOn;
    }

    public LongFilter getApplicantEducationalInformationId() {
        return applicantEducationalInformationId;
    }

    public void setApplicantEducationalInformationId(LongFilter applicantEducationalInformationId) {
        this.applicantEducationalInformationId = applicantEducationalInformationId;
    }

    public LongFilter getJobExperienceId() {
        return jobExperienceId;
    }

    public void setJobExperienceId(LongFilter jobExperienceId) {
        this.jobExperienceId = jobExperienceId;
    }

    public LongFilter getApplicantAddressId() {
        return applicantAddressId;
    }

    public void setApplicantAddressId(LongFilter applicantAddressId) {
        this.applicantAddressId = applicantAddressId;
    }

    public LongFilter getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(LongFilter semesterId) {
        this.semesterId = semesterId;
    }

    public LongFilter getProgramId() {
        return programId;
    }

    public void setProgramId(LongFilter programId) {
        this.programId = programId;
    }

    public LongFilter getApplicantPersonalInformationId() {
        return applicantPersonalInformationId;
    }

    public void setApplicantPersonalInformationId(LongFilter applicantPersonalInformationId) {
        this.applicantPersonalInformationId = applicantPersonalInformationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ApplicantCriteria that = (ApplicantCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(applicationSerial, that.applicationSerial) &&
            Objects.equals(status, that.status) &&
            Objects.equals(appliedOn, that.appliedOn) &&
            Objects.equals(applicationFeePaidOn, that.applicationFeePaidOn) &&
            Objects.equals(selectedRejectedOn, that.selectedRejectedOn) &&
            Objects.equals(applicantEducationalInformationId, that.applicantEducationalInformationId) &&
            Objects.equals(jobExperienceId, that.jobExperienceId) &&
            Objects.equals(applicantAddressId, that.applicantAddressId) &&
            Objects.equals(semesterId, that.semesterId) &&
            Objects.equals(programId, that.programId) &&
            Objects.equals(applicantPersonalInformationId, that.applicantPersonalInformationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        applicationSerial,
        status,
        appliedOn,
        applicationFeePaidOn,
        selectedRejectedOn,
        applicantEducationalInformationId,
        jobExperienceId,
        applicantAddressId,
        semesterId,
        programId,
        applicantPersonalInformationId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (applicationSerial != null ? "applicationSerial=" + applicationSerial + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (appliedOn != null ? "appliedOn=" + appliedOn + ", " : "") +
                (applicationFeePaidOn != null ? "applicationFeePaidOn=" + applicationFeePaidOn + ", " : "") +
                (selectedRejectedOn != null ? "selectedRejectedOn=" + selectedRejectedOn + ", " : "") +
                (applicantEducationalInformationId != null ? "applicantEducationalInformationId=" + applicantEducationalInformationId + ", " : "") +
                (jobExperienceId != null ? "jobExperienceId=" + jobExperienceId + ", " : "") +
                (applicantAddressId != null ? "applicantAddressId=" + applicantAddressId + ", " : "") +
                (semesterId != null ? "semesterId=" + semesterId + ", " : "") +
                (programId != null ? "programId=" + programId + ", " : "") +
                (applicantPersonalInformationId != null ? "applicantPersonalInformationId=" + applicantPersonalInformationId + ", " : "") +
            "}";
    }

}
