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

import aust.edu.domain.ApplicantAddress;
import aust.edu.domain.*; // for static metamodels
import aust.edu.repository.ApplicantAddressRepository;
import aust.edu.service.dto.ApplicantAddressCriteria;

/**
 * Service for executing complex queries for {@link ApplicantAddress} entities in the database.
 * The main input is a {@link ApplicantAddressCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicantAddress} or a {@link Page} of {@link ApplicantAddress} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicantAddressQueryService extends QueryService<ApplicantAddress> {

    private final Logger log = LoggerFactory.getLogger(ApplicantAddressQueryService.class);

    private final ApplicantAddressRepository applicantAddressRepository;

    public ApplicantAddressQueryService(ApplicantAddressRepository applicantAddressRepository) {
        this.applicantAddressRepository = applicantAddressRepository;
    }

    /**
     * Return a {@link List} of {@link ApplicantAddress} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicantAddress> findByCriteria(ApplicantAddressCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicantAddress> specification = createSpecification(criteria);
        return applicantAddressRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ApplicantAddress} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicantAddress> findByCriteria(ApplicantAddressCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicantAddress> specification = createSpecification(criteria);
        return applicantAddressRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicantAddressCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicantAddress> specification = createSpecification(criteria);
        return applicantAddressRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplicantAddressCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApplicantAddress> createSpecification(ApplicantAddressCriteria criteria) {
        Specification<ApplicantAddress> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApplicantAddress_.id));
            }
            if (criteria.getApplicationSerial() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApplicationSerial(), ApplicantAddress_.applicationSerial));
            }
            if (criteria.getAddressType() != null) {
                specification = specification.and(buildSpecification(criteria.getAddressType(), ApplicantAddress_.addressType));
            }
            if (criteria.getThanaOther() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThanaOther(), ApplicantAddress_.thanaOther));
            }
            if (criteria.getLine1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLine1(), ApplicantAddress_.line1));
            }
            if (criteria.getLine2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLine2(), ApplicantAddress_.line2));
            }
            if (criteria.getDistrictId() != null) {
                specification = specification.and(buildSpecification(criteria.getDistrictId(),
                    root -> root.join(ApplicantAddress_.district, JoinType.LEFT).get(District_.id)));
            }
            if (criteria.getDivisionId() != null) {
                specification = specification.and(buildSpecification(criteria.getDivisionId(),
                    root -> root.join(ApplicantAddress_.division, JoinType.LEFT).get(Division_.id)));
            }
            if (criteria.getThanaId() != null) {
                specification = specification.and(buildSpecification(criteria.getThanaId(),
                    root -> root.join(ApplicantAddress_.thana, JoinType.LEFT).get(Thana_.id)));
            }
            if (criteria.getApplicantId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicantId(),
                    root -> root.join(ApplicantAddress_.applicant, JoinType.LEFT).get(Applicant_.id)));
            }
        }
        return specification;
    }
}
