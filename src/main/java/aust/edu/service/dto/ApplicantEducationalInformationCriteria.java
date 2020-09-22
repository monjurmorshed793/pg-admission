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
 * Criteria class for the {@link aust.edu.domain.ApplicantEducationalInformation} entity. This class is used
 * in {@link aust.edu.web.rest.ApplicantEducationalInformationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /applicant-educational-informations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApplicantEducationalInformationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter instituteName;

    private StringFilter board;

    private StringFilter totalMarksGrade;

    private StringFilter divisionClassGrade;

    private IntegerFilter passingYear;

    private LongFilter examTypeId;

    private LongFilter applicantId;

    public ApplicantEducationalInformationCriteria() {
    }

    public ApplicantEducationalInformationCriteria(ApplicantEducationalInformationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.instituteName = other.instituteName == null ? null : other.instituteName.copy();
        this.board = other.board == null ? null : other.board.copy();
        this.totalMarksGrade = other.totalMarksGrade == null ? null : other.totalMarksGrade.copy();
        this.divisionClassGrade = other.divisionClassGrade == null ? null : other.divisionClassGrade.copy();
        this.passingYear = other.passingYear == null ? null : other.passingYear.copy();
        this.examTypeId = other.examTypeId == null ? null : other.examTypeId.copy();
        this.applicantId = other.applicantId == null ? null : other.applicantId.copy();
    }

    @Override
    public ApplicantEducationalInformationCriteria copy() {
        return new ApplicantEducationalInformationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(StringFilter instituteName) {
        this.instituteName = instituteName;
    }

    public StringFilter getBoard() {
        return board;
    }

    public void setBoard(StringFilter board) {
        this.board = board;
    }

    public StringFilter getTotalMarksGrade() {
        return totalMarksGrade;
    }

    public void setTotalMarksGrade(StringFilter totalMarksGrade) {
        this.totalMarksGrade = totalMarksGrade;
    }

    public StringFilter getDivisionClassGrade() {
        return divisionClassGrade;
    }

    public void setDivisionClassGrade(StringFilter divisionClassGrade) {
        this.divisionClassGrade = divisionClassGrade;
    }

    public IntegerFilter getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(IntegerFilter passingYear) {
        this.passingYear = passingYear;
    }

    public LongFilter getExamTypeId() {
        return examTypeId;
    }

    public void setExamTypeId(LongFilter examTypeId) {
        this.examTypeId = examTypeId;
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
        final ApplicantEducationalInformationCriteria that = (ApplicantEducationalInformationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(instituteName, that.instituteName) &&
            Objects.equals(board, that.board) &&
            Objects.equals(totalMarksGrade, that.totalMarksGrade) &&
            Objects.equals(divisionClassGrade, that.divisionClassGrade) &&
            Objects.equals(passingYear, that.passingYear) &&
            Objects.equals(examTypeId, that.examTypeId) &&
            Objects.equals(applicantId, that.applicantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        instituteName,
        board,
        totalMarksGrade,
        divisionClassGrade,
        passingYear,
        examTypeId,
        applicantId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantEducationalInformationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (instituteName != null ? "instituteName=" + instituteName + ", " : "") +
                (board != null ? "board=" + board + ", " : "") +
                (totalMarksGrade != null ? "totalMarksGrade=" + totalMarksGrade + ", " : "") +
                (divisionClassGrade != null ? "divisionClassGrade=" + divisionClassGrade + ", " : "") +
                (passingYear != null ? "passingYear=" + passingYear + ", " : "") +
                (examTypeId != null ? "examTypeId=" + examTypeId + ", " : "") +
                (applicantId != null ? "applicantId=" + applicantId + ", " : "") +
            "}";
    }

}
