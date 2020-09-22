package aust.edu.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import aust.edu.domain.enumeration.AddressType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link aust.edu.domain.ApplicantAddress} entity. This class is used
 * in {@link aust.edu.web.rest.ApplicantAddressResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /applicant-addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApplicantAddressCriteria implements Serializable, Criteria {
    /**
     * Class for filtering AddressType
     */
    public static class AddressTypeFilter extends Filter<AddressType> {

        public AddressTypeFilter() {
        }

        public AddressTypeFilter(AddressTypeFilter filter) {
            super(filter);
        }

        @Override
        public AddressTypeFilter copy() {
            return new AddressTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter applicationSerial;

    private AddressTypeFilter addressType;

    private StringFilter thanaOther;

    private StringFilter line1;

    private StringFilter line2;

    private LongFilter applicantId;

    public ApplicantAddressCriteria() {
    }

    public ApplicantAddressCriteria(ApplicantAddressCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.applicationSerial = other.applicationSerial == null ? null : other.applicationSerial.copy();
        this.addressType = other.addressType == null ? null : other.addressType.copy();
        this.thanaOther = other.thanaOther == null ? null : other.thanaOther.copy();
        this.line1 = other.line1 == null ? null : other.line1.copy();
        this.line2 = other.line2 == null ? null : other.line2.copy();
        this.applicantId = other.applicantId == null ? null : other.applicantId.copy();
    }

    @Override
    public ApplicantAddressCriteria copy() {
        return new ApplicantAddressCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getApplicationSerial() {
        return applicationSerial;
    }

    public void setApplicationSerial(IntegerFilter applicationSerial) {
        this.applicationSerial = applicationSerial;
    }

    public AddressTypeFilter getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressTypeFilter addressType) {
        this.addressType = addressType;
    }

    public StringFilter getThanaOther() {
        return thanaOther;
    }

    public void setThanaOther(StringFilter thanaOther) {
        this.thanaOther = thanaOther;
    }

    public StringFilter getLine1() {
        return line1;
    }

    public void setLine1(StringFilter line1) {
        this.line1 = line1;
    }

    public StringFilter getLine2() {
        return line2;
    }

    public void setLine2(StringFilter line2) {
        this.line2 = line2;
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
        final ApplicantAddressCriteria that = (ApplicantAddressCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(applicationSerial, that.applicationSerial) &&
            Objects.equals(addressType, that.addressType) &&
            Objects.equals(thanaOther, that.thanaOther) &&
            Objects.equals(line1, that.line1) &&
            Objects.equals(line2, that.line2) &&
            Objects.equals(applicantId, that.applicantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        applicationSerial,
        addressType,
        thanaOther,
        line1,
        line2,
        applicantId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantAddressCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (applicationSerial != null ? "applicationSerial=" + applicationSerial + ", " : "") +
                (addressType != null ? "addressType=" + addressType + ", " : "") +
                (thanaOther != null ? "thanaOther=" + thanaOther + ", " : "") +
                (line1 != null ? "line1=" + line1 + ", " : "") +
                (line2 != null ? "line2=" + line2 + ", " : "") +
                (applicantId != null ? "applicantId=" + applicantId + ", " : "") +
            "}";
    }

}
