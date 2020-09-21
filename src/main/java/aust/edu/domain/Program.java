package aust.edu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Program.
 */
@Entity
@Table(name = "mst_program")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Program extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "program_name_short", nullable = false)
    private String programNameShort;

    @NotNull
    @Size(max = 500)
    @Column(name = "program_name", length = 500, nullable = false)
    private String programName;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProgramNameShort() {
        return programNameShort;
    }

    public Program programNameShort(String programNameShort) {
        this.programNameShort = programNameShort;
        return this;
    }

    public void setProgramNameShort(String programNameShort) {
        this.programNameShort = programNameShort;
    }

    public String getProgramName() {
        return programName;
    }

    public Program programName(String programName) {
        this.programName = programName;
        return this;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Program)) {
            return false;
        }
        return id != null && id.equals(((Program) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Program{" +
            "id=" + getId() +
            ", programNameShort='" + getProgramNameShort() + "'" +
            ", programName='" + getProgramName() + "'" +
            "}";
    }
}
