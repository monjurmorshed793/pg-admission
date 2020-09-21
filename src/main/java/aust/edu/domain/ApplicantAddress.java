package aust.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import aust.edu.domain.enumeration.AddressType;

/**
 * A ApplicantAddress.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApplicantAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "application_serial")
    private Integer applicationSerial;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false)
    private AddressType addressType;

    @Column(name = "thana_other")
    private String thanaOther;

    @NotNull
    @Size(max = 100)
    @Column(name = "line_1", length = 100, nullable = false)
    private String line1;

    @Size(max = 100)
    @Column(name = "line_2", length = 100)
    private String line2;

    @ManyToOne
    @JsonIgnoreProperties(value = "applicantAddresses", allowSetters = true)
    private Applicant applicant;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getApplicationSerial() {
        return applicationSerial;
    }

    public ApplicantAddress applicationSerial(Integer applicationSerial) {
        this.applicationSerial = applicationSerial;
        return this;
    }

    public void setApplicationSerial(Integer applicationSerial) {
        this.applicationSerial = applicationSerial;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public ApplicantAddress addressType(AddressType addressType) {
        this.addressType = addressType;
        return this;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public String getThanaOther() {
        return thanaOther;
    }

    public ApplicantAddress thanaOther(String thanaOther) {
        this.thanaOther = thanaOther;
        return this;
    }

    public void setThanaOther(String thanaOther) {
        this.thanaOther = thanaOther;
    }

    public String getLine1() {
        return line1;
    }

    public ApplicantAddress line1(String line1) {
        this.line1 = line1;
        return this;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public ApplicantAddress line2(String line2) {
        this.line2 = line2;
        return this;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public ApplicantAddress applicant(Applicant applicant) {
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
        if (!(o instanceof ApplicantAddress)) {
            return false;
        }
        return id != null && id.equals(((ApplicantAddress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantAddress{" +
            "id=" + getId() +
            ", applicationSerial=" + getApplicationSerial() +
            ", addressType='" + getAddressType() + "'" +
            ", thanaOther='" + getThanaOther() + "'" +
            ", line1='" + getLine1() + "'" +
            ", line2='" + getLine2() + "'" +
            "}";
    }
}
