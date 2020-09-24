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

import aust.edu.domain.Applicant;
import aust.edu.domain.*; // for static metamodels
import aust.edu.repository.ApplicantRepository;
import aust.edu.service.dto.ApplicantCriteria;

/**
 * Service for executing complex queries for {@link Applicant} entities in the database.
 * The main input is a {@link ApplicantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Applicant} or a {@link Page} of {@link Applicant} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicantQueryService extends QueryService<Applicant> {

    private final Logger log = LoggerFactory.getLogger(ApplicantQueryService.class);

    private final ApplicantRepository applicantRepository;

    public ApplicantQueryService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    /**
     * Return a {@link List} of {@link Applicant} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Applicant> findByCriteria(ApplicantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Applicant> specification = createSpecification(criteria);
        return applicantRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Applicant} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Applicant> findByCriteria(ApplicantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Applicant> specification = createSpecification(criteria);
        return applicantRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Applicant> specification = createSpecification(criteria);
        return applicantRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplicantCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Applicant> createSpecification(ApplicantCriteria criteria) {
        Specification<Applicant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Applicant_.id));
            }
            if (criteria.getApplicationSerial() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApplicationSerial(), Applicant_.applicationSerial));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Applicant_.status));
            }
            if (criteria.getAppliedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAppliedOn(), Applicant_.appliedOn));
            }
            if (criteria.getApplicationFeePaidOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApplicationFeePaidOn(), Applicant_.applicationFeePaidOn));
            }
            if (criteria.getSelectedRejectedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSelectedRejectedOn(), Applicant_.selectedRejectedOn));
            }
            if (criteria.getApplicantEducationalInformationId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicantEducationalInformationId(),
                    root -> root.join(Applicant_.applicantEducationalInformations, JoinType.LEFT).get(ApplicantEducationalInformation_.id)));
            }
            if (criteria.getJobExperienceId() != null) {
                specification = specification.and(buildSpecification(criteria.getJobExperienceId(),
                    root -> root.join(Applicant_.jobExperiences, JoinType.LEFT).get(JobExperience_.id)));
            }
            if (criteria.getApplicantAddressId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicantAddressId(),
                    root -> root.join(Applicant_.applicantAddresses, JoinType.LEFT).get(ApplicantAddress_.id)));
            }
            if (criteria.getApplicantPersonalInformationId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicantPersonalInformationId(),
                    root -> root.join(Applicant_.applicantPersonalInformation, JoinType.LEFT).get(ApplicantPersonalInfo_.id)));
            }
        }
        return specification;
    }
}
