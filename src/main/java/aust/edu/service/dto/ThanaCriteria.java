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
 * Criteria class for the {@link aust.edu.domain.Thana} entity. This class is used
 * in {@link aust.edu.web.rest.ThanaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /thanas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ThanaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter thanaName;

    private LongFilter districtId;

    public ThanaCriteria() {
    }

    public ThanaCriteria(ThanaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.thanaName = other.thanaName == null ? null : other.thanaName.copy();
        this.districtId = other.districtId == null ? null : other.districtId.copy();
    }

    @Override
    public ThanaCriteria copy() {
        return new ThanaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getThanaName() {
        return thanaName;
    }

    public void setThanaName(StringFilter thanaName) {
        this.thanaName = thanaName;
    }

    public LongFilter getDistrictId() {
        return districtId;
    }

    public void setDistrictId(LongFilter districtId) {
        this.districtId = districtId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ThanaCriteria that = (ThanaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(thanaName, that.thanaName) &&
            Objects.equals(districtId, that.districtId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        thanaName,
        districtId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThanaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (thanaName != null ? "thanaName=" + thanaName + ", " : "") +
                (districtId != null ? "districtId=" + districtId + ", " : "") +
            "}";
    }

}
