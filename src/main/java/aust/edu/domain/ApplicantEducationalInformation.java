package aust.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ApplicantEducationalInformation.
 */
@Entity
@Table(name = "edu_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApplicantEducationalInformation extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "institute_name", nullable = false)
    private String instituteName;

    @Column(name = "board")
    private String board;

    @Column(name = "total_marks_grade")
    private String totalMarksGrade;

    @Column(name = "division_class_grade")
    private String divisionClassGrade;

    @NotNull
    @Column(name = "passing_year", nullable = false)
    private Integer passingYear;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "applicantEducationalInformations", allowSetters = true)
    private ExamType examType;

    @ManyToOne
    @JsonIgnoreProperties(value = "applicantEducationalInformations", allowSetters = true)
    private Applicant applicant;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public ApplicantEducationalInformation instituteName(String instituteName) {
        this.instituteName = instituteName;
        return this;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getBoard() {
        return board;
    }

    public ApplicantEducationalInformation board(String board) {
        this.board = board;
        return this;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getTotalMarksGrade() {
        return totalMarksGrade;
    }

    public ApplicantEducationalInformation totalMarksGrade(String totalMarksGrade) {
        this.totalMarksGrade = totalMarksGrade;
        return this;
    }

    public void setTotalMarksGrade(String totalMarksGrade) {
        this.totalMarksGrade = totalMarksGrade;
    }

    public String getDivisionClassGrade() {
        return divisionClassGrade;
    }

    public ApplicantEducationalInformation divisionClassGrade(String divisionClassGrade) {
        this.divisionClassGrade = divisionClassGrade;
        return this;
    }

    public void setDivisionClassGrade(String divisionClassGrade) {
        this.divisionClassGrade = divisionClassGrade;
    }

    public Integer getPassingYear() {
        return passingYear;
    }

    public ApplicantEducationalInformation passingYear(Integer passingYear) {
        this.passingYear = passingYear;
        return this;
    }

    public void setPassingYear(Integer passingYear) {
        this.passingYear = passingYear;
    }

    public ExamType getExamType() {
        return examType;
    }

    public ApplicantEducationalInformation examType(ExamType examType) {
        this.examType = examType;
        return this;
    }

    public void setExamType(ExamType examType) {
        this.examType = examType;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public ApplicantEducationalInformation applicant(Applicant applicant) {
        this.applicant = applicant;
        return this;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicantEducationalInformation)) {
            return false;
        }
        return id != null && id.equals(((ApplicantEducationalInformation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantEducationalInformation{" +
            "id=" + getId() +
            ", instituteName='" + getInstituteName() + "'" +
            ", board='" + getBoard() + "'" +
            ", totalMarksGrade='" + getTotalMarksGrade() + "'" +
            ", divisionClassGrade='" + getDivisionClassGrade() + "'" +
            ", passingYear=" + getPassingYear() +
            "}";
    }
}
