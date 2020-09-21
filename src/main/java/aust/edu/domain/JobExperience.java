package aust.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A JobExperience.
 */
@Entity
@Table(name = "job_experience")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JobExperience implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "organization_name", nullable = false)
    private String organizationName;

    @NotNull
    @Column(name = "designation", nullable = false)
    private String designation;

    
    @Lob
    @Column(name = "job_responsibility", nullable = false)
    private String jobResponsibility;

    @NotNull
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "currently_working")
    private Boolean currentlyWorking;

    @ManyToOne
    @JsonIgnoreProperties(value = "jobExperiences", allowSetters = true)
    private Applicant applicant;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public JobExperience organizationName(String organizationName) {
        this.organizationName = organizationName;
        return this;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getDesignation() {
        return designation;
    }

    public JobExperience designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getJobResponsibility() {
        return jobResponsibility;
    }

    public JobExperience jobResponsibility(String jobResponsibility) {
        this.jobResponsibility = jobResponsibility;
        return this;
    }

    public void setJobResponsibility(String jobResponsibility) {
        this.jobResponsibility = jobResponsibility;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public JobExperience fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public JobExperience toDate(LocalDate toDate) {
        this.toDate = toDate;
        return this;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Boolean isCurrentlyWorking() {
        return currentlyWorking;
    }

    public JobExperience currentlyWorking(Boolean currentlyWorking) {
        this.currentlyWorking = currentlyWorking;
        return this;
    }

    public void setCurrentlyWorking(Boolean currentlyWorking) {
        this.currentlyWorking = currentlyWorking;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public JobExperience applicant(Applicant applicant) {
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
        if (!(o instanceof JobExperience)) {
            return false;
        }
        return id != null && id.equals(((JobExperience) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobExperience{" +
            "id=" + getId() +
            ", organizationName='" + getOrganizationName() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", jobResponsibility='" + getJobResponsibility() + "'" +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", currentlyWorking='" + isCurrentlyWorking() + "'" +
            "}";
    }
}
