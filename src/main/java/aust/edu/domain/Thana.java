package aust.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Thana.
 */
@Entity
@Table(name = "mst_thana")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Thana extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "thana_name", nullable = false)
    private String thanaName;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "thanas", allowSetters = true)
    private District district;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThanaName() {
        return thanaName;
    }

    public Thana thanaName(String thanaName) {
        this.thanaName = thanaName;
        return this;
    }

    public void setThanaName(String thanaName) {
        this.thanaName = thanaName;
    }

    public District getDistrict() {
        return district;
    }

    public Thana district(District district) {
        this.district = district;
        return this;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Thana)) {
            return false;
        }
        return id != null && id.equals(((Thana) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Thana{" +
            "id=" + getId() +
            ", thanaName='" + getThanaName() + "'" +
            "}";
    }
}
