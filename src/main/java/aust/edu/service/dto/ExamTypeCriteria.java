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
 * Criteria class for the {@link aust.edu.domain.ExamType} entity. This class is used
 * in {@link aust.edu.web.rest.ExamTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /exam-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExamTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter examTypeCode;

    private StringFilter examTypeName;

    public ExamTypeCriteria() {
    }

    public ExamTypeCriteria(ExamTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.examTypeCode = other.examTypeCode == null ? null : other.examTypeCode.copy();
        this.examTypeName = other.examTypeName == null ? null : other.examTypeName.copy();
    }

    @Override
    public ExamTypeCriteria copy() {
        return new ExamTypeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getExamTypeCode() {
        return examTypeCode;
    }

    public void setExamTypeCode(StringFilter examTypeCode) {
        this.examTypeCode = examTypeCode;
    }

    public StringFilter getExamTypeName() {
        return examTypeName;
    }

    public void setExamTypeName(StringFilter examTypeName) {
        this.examTypeName = examTypeName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ExamTypeCriteria that = (ExamTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(examTypeCode, that.examTypeCode) &&
            Objects.equals(examTypeName, that.examTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        examTypeCode,
        examTypeName
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (examTypeCode != null ? "examTypeCode=" + examTypeCode + ", " : "") +
                (examTypeName != null ? "examTypeName=" + examTypeName + ", " : "") +
            "}";
    }

}
