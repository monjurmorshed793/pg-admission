package aust.edu.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import aust.edu.domain.enumeration.Gender;
import aust.edu.domain.enumeration.Religion;
import aust.edu.domain.enumeration.MaritalStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link aust.edu.domain.ApplicantPersonalInfo} entity. This class is used
 * in {@link aust.edu.web.rest.ApplicantPersonalInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /applicant-personal-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApplicantPersonalInfoCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {

        public GenderFilter() {
        }

        public GenderFilter(GenderFilter filter) {
            super(filter);
        }

        @Override
        public GenderFilter copy() {
            return new GenderFilter(this);
        }

    }
    /**
     * Class for filtering Religion
     */
    public static class ReligionFilter extends Filter<Religion> {

        public ReligionFilter() {
        }

        public ReligionFilter(ReligionFilter filter) {
            super(filter);
        }

        @Override
        public ReligionFilter copy() {
            return new ReligionFilter(this);
        }

    }
    /**
     * Class for filtering MaritalStatus
     */
    public static class MaritalStatusFilter extends Filter<MaritalStatus> {

        public MaritalStatusFilter() {
        }

        public MaritalStatusFilter(MaritalStatusFilter filter) {
            super(filter);
        }

        @Override
        public MaritalStatusFilter copy() {
            return new MaritalStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter applicationSerial;

    private StringFilter fullName;

    private StringFilter firstName;

    private StringFilter middleName;

    private StringFilter lastName;

    private StringFilter fatherName;

    private StringFilter motherName;

    private GenderFilter gender;

    private ReligionFilter religion;

    private StringFilter nationality;

    private LocalDateFilter dateOfBirth;

    private StringFilter placeOfBirth;

    private StringFilter mobileNumber;

    private StringFilter emailAddress;

    private MaritalStatusFilter maritalStatus;

    private LongFilter applicantId;

    public ApplicantPersonalInfoCriteria() {
    }

    public ApplicantPersonalInfoCriteria(ApplicantPersonalInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.applicationSerial = other.applicationSerial == null ? null : other.applicationSerial.copy();
        this.fullName = other.fullName == null ? null : other.fullName.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.fatherName = other.fatherName == null ? null : other.fatherName.copy();
        this.motherName = other.motherName == null ? null : other.motherName.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.religion = other.religion == null ? null : other.religion.copy();
        this.nationality = other.nationality == null ? null : other.nationality.copy();
        this.dateOfBirth = other.dateOfBirth == null ? null : other.dateOfBirth.copy();
        this.placeOfBirth = other.placeOfBirth == null ? null : other.placeOfBirth.copy();
        this.mobileNumber = other.mobileNumber == null ? null : other.mobileNumber.copy();
        this.emailAddress = other.emailAddress == null ? null : other.emailAddress.copy();
        this.maritalStatus = other.maritalStatus == null ? null : other.maritalStatus.copy();
        this.applicantId = other.applicantId == null ? null : other.applicantId.copy();
    }

    @Override
    public ApplicantPersonalInfoCriteria copy() {
        return new ApplicantPersonalInfoCriteria(this);
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

    public StringFilter getFullName() {
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getMiddleName() {
        return middleName;
    }

    public void setMiddleName(StringFilter middleName) {
        this.middleName = middleName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getFatherName() {
        return fatherName;
    }

    public void setFatherName(StringFilter fatherName) {
        this.fatherName = fatherName;
    }

    public StringFilter getMotherName() {
        return motherName;
    }

    public void setMotherName(StringFilter motherName) {
        this.motherName = motherName;
    }

    public GenderFilter getGender() {
        return gender;
    }

    public void setGender(GenderFilter gender) {
        this.gender = gender;
    }

    public ReligionFilter getReligion() {
        return religion;
    }

    public void setReligion(ReligionFilter religion) {
        this.religion = religion;
    }

    public StringFilter getNationality() {
        return nationality;
    }

    public void setNationality(StringFilter nationality) {
        this.nationality = nationality;
    }

    public LocalDateFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public StringFilter getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(StringFilter placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public StringFilter getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(StringFilter mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public StringFilter getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(StringFilter emailAddress) {
        this.emailAddress = emailAddress;
    }

    public MaritalStatusFilter getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatusFilter maritalStatus) {
        this.maritalStatus = maritalStatus;
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
        final ApplicantPersonalInfoCriteria that = (ApplicantPersonalInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(applicationSerial, that.applicationSerial) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(fatherName, that.fatherName) &&
            Objects.equals(motherName, that.motherName) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(religion, that.religion) &&
            Objects.equals(nationality, that.nationality) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(placeOfBirth, that.placeOfBirth) &&
            Objects.equals(mobileNumber, that.mobileNumber) &&
            Objects.equals(emailAddress, that.emailAddress) &&
            Objects.equals(maritalStatus, that.maritalStatus) &&
            Objects.equals(applicantId, that.applicantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        applicationSerial,
        fullName,
        firstName,
        middleName,
        lastName,
        fatherName,
        motherName,
        gender,
        religion,
        nationality,
        dateOfBirth,
        placeOfBirth,
        mobileNumber,
        emailAddress,
        maritalStatus,
        applicantId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantPersonalInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (applicationSerial != null ? "applicationSerial=" + applicationSerial + ", " : "") +
                (fullName != null ? "fullName=" + fullName + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (middleName != null ? "middleName=" + middleName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (fatherName != null ? "fatherName=" + fatherName + ", " : "") +
                (motherName != null ? "motherName=" + motherName + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (religion != null ? "religion=" + religion + ", " : "") +
                (nationality != null ? "nationality=" + nationality + ", " : "") +
                (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "") +
                (placeOfBirth != null ? "placeOfBirth=" + placeOfBirth + ", " : "") +
                (mobileNumber != null ? "mobileNumber=" + mobileNumber + ", " : "") +
                (emailAddress != null ? "emailAddress=" + emailAddress + ", " : "") +
                (maritalStatus != null ? "maritalStatus=" + maritalStatus + ", " : "") +
                (applicantId != null ? "applicantId=" + applicantId + ", " : "") +
            "}";
    }

}
