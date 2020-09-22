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
 * Criteria class for the {@link aust.edu.domain.Program} entity. This class is used
 * in {@link aust.edu.web.rest.ProgramResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /programs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProgramCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter programNameShort;

    private StringFilter programName;

    public ProgramCriteria() {
    }

    public ProgramCriteria(ProgramCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.programNameShort = other.programNameShort == null ? null : other.programNameShort.copy();
        this.programName = other.programName == null ? null : other.programName.copy();
    }

    @Override
    public ProgramCriteria copy() {
        return new ProgramCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProgramNameShort() {
        return programNameShort;
    }

    public void setProgramNameShort(StringFilter programNameShort) {
        this.programNameShort = programNameShort;
    }

    public StringFilter getProgramName() {
        return programName;
    }

    public void setProgramName(StringFilter programName) {
        this.programName = programName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProgramCriteria that = (ProgramCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(programNameShort, that.programNameShort) &&
            Objects.equals(programName, that.programName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        programNameShort,
        programName
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProgramCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (programNameShort != null ? "programNameShort=" + programNameShort + ", " : "") +
                (programName != null ? "programName=" + programName + ", " : "") +
            "}";
    }

}
