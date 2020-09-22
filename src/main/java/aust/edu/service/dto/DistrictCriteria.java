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
 * Criteria class for the {@link aust.edu.domain.District} entity. This class is used
 * in {@link aust.edu.web.rest.DistrictResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /districts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DistrictCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter districtName;

    private LongFilter divisionId;

    public DistrictCriteria() {
    }

    public DistrictCriteria(DistrictCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.districtName = other.districtName == null ? null : other.districtName.copy();
        this.divisionId = other.divisionId == null ? null : other.divisionId.copy();
    }

    @Override
    public DistrictCriteria copy() {
        return new DistrictCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDistrictName() {
        return districtName;
    }

    public void setDistrictName(StringFilter districtName) {
        this.districtName = districtName;
    }

    public LongFilter getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(LongFilter divisionId) {
        this.divisionId = divisionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DistrictCriteria that = (DistrictCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(districtName, that.districtName) &&
            Objects.equals(divisionId, that.divisionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        districtName,
        divisionId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DistrictCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (districtName != null ? "districtName=" + districtName + ", " : "") +
                (divisionId != null ? "divisionId=" + divisionId + ", " : "") +
            "}";
    }

}
