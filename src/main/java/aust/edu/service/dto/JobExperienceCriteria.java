package aust.edu.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link aust.edu.domain.JobExperience} entity. This class is used
 * in {@link aust.edu.web.rest.JobExperienceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /job-experiences?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class JobExperienceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter organizationName;

    private StringFilter designation;

    private LocalDateFilter fromDate;

    private LocalDateFilter toDate;

    private BooleanFilter currentlyWorking;

    private LongFilter applicantId;

    public JobExperienceCriteria() {
    }

    public JobExperienceCriteria(JobExperienceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.organizationName = other.organizationName == null ? null : other.organizationName.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.fromDate = other.fromDate == null ? null : other.fromDate.copy();
        this.toDate = other.toDate == null ? null : other.toDate.copy();
        this.currentlyWorking = other.currentlyWorking == null ? null : other.currentlyWorking.copy();
        this.applicantId = other.applicantId == null ? null : other.applicantId.copy();
    }

    @Override
    public JobExperienceCriteria copy() {
        return new JobExperienceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(StringFilter organizationName) {
        this.organizationName = organizationName;
    }

    public StringFilter getDesignation() {
        return designation;
    }

    public void setDesignation(StringFilter designation) {
        this.designation = designation;
    }

    public LocalDateFilter getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateFilter fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateFilter getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateFilter toDate) {
        this.toDate = toDate;
    }

    public BooleanFilter getCurrentlyWorking() {
        return currentlyWorking;
    }

    public void setCurrentlyWorking(BooleanFilter currentlyWorking) {
        this.currentlyWorking = currentlyWorking;
    }

    public LongFilter getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(LongFilter applicantId) {
        this.applicantId = applicantId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final JobExperienceCriteria that = (JobExperienceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(organizationName, that.organizationName) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(fromDate, that.fromDate) &&
            Objects.equals(toDate, that.toDate) &&
            Objects.equals(currentlyWorking, that.currentlyWorking) &&
            Objects.equals(applicantId, that.applicantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        organizationName,
        designation,
        fromDate,
        toDate,
        currentlyWorking,
        applicantId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobExperienceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (organizationName != null ? "organizationName=" + organizationName + ", " : "") +
                (designation != null ? "designation=" + designation + ", " : "") +
                (fromDate != null ? "fromDate=" + fromDate + ", " : "") +
                (toDate != null ? "toDate=" + toDate + ", " : "") +
                (currentlyWorking != null ? "currentlyWorking=" + currentlyWorking + ", " : "") +
                (applicantId != null ? "applicantId=" + applicantId + ", " : "") +
            "}";
    }

}
