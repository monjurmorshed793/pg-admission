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

/**
 * Criteria class for the {@link aust.edu.domain.Semester} entity. This class is used
 * in {@link aust.edu.web.rest.SemesterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /semesters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SemesterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter semesterName;

    private BooleanFilter isActive;

    public SemesterCriteria() {
    }

    public SemesterCriteria(SemesterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.semesterName = other.semesterName == null ? null : other.semesterName.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
    }

    @Override
    public SemesterCriteria copy() {
        return new SemesterCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(StringFilter semesterName) {
        this.semesterName = semesterName;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SemesterCriteria that = (SemesterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(semesterName, that.semesterName) &&
            Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        semesterName,
        isActive
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SemesterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (semesterName != null ? "semesterName=" + semesterName + ", " : "") +
                (isActive != null ? "isActive=" + isActive + ", " : "") +
            "}";
    }

}
