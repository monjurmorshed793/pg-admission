package aust.edu.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import aust.edu.domain.ApplicantPersonalInfo;
import aust.edu.domain.*; // for static metamodels
import aust.edu.repository.ApplicantPersonalInfoRepository;
import aust.edu.service.dto.ApplicantPersonalInfoCriteria;

/**
 * Service for executing complex queries for {@link ApplicantPersonalInfo} entities in the database.
 * The main input is a {@link ApplicantPersonalInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicantPersonalInfo} or a {@link Page} of {@link ApplicantPersonalInfo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicantPersonalInfoQueryService extends QueryService<ApplicantPersonalInfo> {

    private final Logger log = LoggerFactory.getLogger(ApplicantPersonalInfoQueryService.class);

    private final ApplicantPersonalInfoRepository applicantPersonalInfoRepository;

    public ApplicantPersonalInfoQueryService(ApplicantPersonalInfoRepository applicantPersonalInfoRepository) {
        this.applicantPersonalInfoRepository = applicantPersonalInfoRepository;
    }

    /**
     * Return a {@link List} of {@link ApplicantPersonalInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicantPersonalInfo> findByCriteria(ApplicantPersonalInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicantPersonalInfo> specification = createSpecification(criteria);
        return applicantPersonalInfoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ApplicantPersonalInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicantPersonalInfo> findByCriteria(ApplicantPersonalInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicantPersonalInfo> specification = createSpecification(criteria);
        return applicantPersonalInfoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicantPersonalInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicantPersonalInfo> specification = createSpecification(criteria);
        return applicantPersonalInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplicantPersonalInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApplicantPersonalInfo> createSpecification(ApplicantPersonalInfoCriteria criteria) {
        Specification<ApplicantPersonalInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApplicantPersonalInfo_.id));
            }
            if (criteria.getApplicationSerial() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApplicationSerial(), ApplicantPersonalInfo_.applicationSerial));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), ApplicantPersonalInfo_.fullName));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), ApplicantPersonalInfo_.firstName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), ApplicantPersonalInfo_.middleName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), ApplicantPersonalInfo_.lastName));
            }
            if (criteria.getFatherName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFatherName(), ApplicantPersonalInfo_.fatherName));
            }
            if (criteria.getMotherName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMotherName(), ApplicantPersonalInfo_.motherName));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), ApplicantPersonalInfo_.gender));
            }
            if (criteria.getReligion() != null) {
                specification = specification.and(buildSpecification(criteria.getReligion(), ApplicantPersonalInfo_.religion));
            }
            if (criteria.getNationality() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNationality(), ApplicantPersonalInfo_.nationality));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), ApplicantPersonalInfo_.dateOfBirth));
            }
            if (criteria.getPlaceOfBirth() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlaceOfBirth(), ApplicantPersonalInfo_.placeOfBirth));
            }
            if (criteria.getMobileNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileNumber(), ApplicantPersonalInfo_.mobileNumber));
            }
            if (criteria.getEmailAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailAddress(), ApplicantPersonalInfo_.emailAddress));
            }
            if (criteria.getMaritalStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getMaritalStatus(), ApplicantPersonalInfo_.maritalStatus));
            }
            if (criteria.getApplicantId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicantId(),
                    root -> root.join(ApplicantPersonalInfo_.applicant, JoinType.LEFT).get(Applicant_.id)));
            }
        }
        return specification;
    }
}
