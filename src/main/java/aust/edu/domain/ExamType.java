package aust.edu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ExamType.
 */
@Entity
@Table(name = "mst_exam_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExamType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "exam_type_code", nullable = false)
    private String examTypeCode;

    @NotNull
    @Column(name = "exam_type_name", nullable = false)
    private String examTypeName;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExamTypeCode() {
        return examTypeCode;
    }

    public ExamType examTypeCode(String examTypeCode) {
        this.examTypeCode = examTypeCode;
        return this;
    }

    public void setExamTypeCode(String examTypeCode) {
        this.examTypeCode = examTypeCode;
    }

    public String getExamTypeName() {
        return examTypeName;
    }

    public ExamType examTypeName(String examTypeName) {
        this.examTypeName = examTypeName;
        return this;
    }

    public void setExamTypeName(String examTypeName) {
        this.examTypeName = examTypeName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamType)) {
            return false;
        }
        return id != null && id.equals(((ExamType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamType{" +
            "id=" + getId() +
            ", examTypeCode='" + getExamTypeCode() + "'" +
            ", examTypeName='" + getExamTypeName() + "'" +
            "}";
    }
}
