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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link aust.edu.domain.ApplicationDeadline} entity. This class is used
 * in {@link aust.edu.web.rest.ApplicationDeadlineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /application-deadlines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApplicationDeadlineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter fromDate;

    private InstantFilter toDate;

    private LongFilter semesterId;

    private LongFilter programId;

    public ApplicationDeadlineCriteria() {
    }

    public ApplicationDeadlineCriteria(ApplicationDeadlineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fromDate = other.fromDate == null ? null : other.fromDate.copy();
        this.toDate = other.toDate == null ? null : other.toDate.copy();
        this.semesterId = other.semesterId == null ? null : other.semesterId.copy();
        this.programId = other.programId == null ? null : other.programId.copy();
    }

    @Override
    public ApplicationDeadlineCriteria copy() {
        return new ApplicationDeadlineCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getFromDate() {
        return fromDate;
    }

    public void setFromDate(InstantFilter fromDate) {
        this.fromDate = fromDate;
    }

    public InstantFilter getToDate() {
        return toDate;
    }

    public void setToDate(InstantFilter toDate) {
        this.toDate = toDate;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ApplicationDeadlineCriteria that = (ApplicationDeadlineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fromDate, that.fromDate) &&
            Objects.equals(toDate, that.toDate) &&
            Objects.equals(semesterId, that.semesterId) &&
            Objects.equals(programId, that.programId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fromDate,
        toDate,
        semesterId,
        programId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationDeadlineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fromDate != null ? "fromDate=" + fromDate + ", " : "") +
                (toDate != null ? "toDate=" + toDate + ", " : "") +
                (semesterId != null ? "semesterId=" + semesterId + ", " : "") +
                (programId != null ? "programId=" + programId + ", " : "") +
            "}";
    }

}
