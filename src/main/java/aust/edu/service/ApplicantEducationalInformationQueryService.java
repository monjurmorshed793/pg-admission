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

import aust.edu.domain.ApplicantEducationalInformation;
import aust.edu.domain.*; // for static metamodels
import aust.edu.repository.ApplicantEducationalInformationRepository;
import aust.edu.service.dto.ApplicantEducationalInformationCriteria;

/**
 * Service for executing complex queries for {@link ApplicantEducationalInformation} entities in the database.
 * The main input is a {@link ApplicantEducationalInformationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicantEducationalInformation} or a {@link Page} of {@link ApplicantEducationalInformation} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicantEducationalInformationQueryService extends QueryService<ApplicantEducationalInformation> {

    private final Logger log = LoggerFactory.getLogger(ApplicantEducationalInformationQueryService.class);

    private final ApplicantEducationalInformationRepository applicantEducationalInformationRepository;

    public ApplicantEducationalInformationQueryService(ApplicantEducationalInformationRepository applicantEducationalInformationRepository) {
        this.applicantEducationalInformationRepository = applicantEducationalInformationRepository;
    }

    /**
     * Return a {@link List} of {@link ApplicantEducationalInformation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicantEducationalInformation> findByCriteria(ApplicantEducationalInformationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicantEducationalInformation> specification = createSpecification(criteria);
        return applicantEducationalInformationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ApplicantEducationalInformation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicantEducationalInformation> findByCriteria(ApplicantEducationalInformationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicantEducationalInformation> specification = createSpecification(criteria);
        return applicantEducationalInformationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicantEducationalInformationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicantEducationalInformation> specification = createSpecification(criteria);
        return applicantEducationalInformationRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplicantEducationalInformationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApplicantEducationalInformation> createSpecification(ApplicantEducationalInformationCriteria criteria) {
        Specification<ApplicantEducationalInformation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApplicantEducationalInformation_.id));
            }
            if (criteria.getInstituteName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInstituteName(), ApplicantEducationalInformation_.instituteName));
            }
            if (criteria.getBoard() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBoard(), ApplicantEducationalInformation_.board));
            }
            if (criteria.getTotalMarksGrade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalMarksGrade(), ApplicantEducationalInformation_.totalMarksGrade));
            }
            if (criteria.getDivisionClassGrade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDivisionClassGrade(), ApplicantEducationalInformation_.divisionClassGrade));
            }
            if (criteria.getPassingYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPassingYear(), ApplicantEducationalInformation_.passingYear));
            }
            if (criteria.getExamTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getExamTypeId(),
                    root -> root.join(ApplicantEducationalInformation_.examType, JoinType.LEFT).get(ExamType_.id)));
            }
            if (criteria.getApplicantId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicantId(),
                    root -> root.join(ApplicantEducationalInformation_.applicant, JoinType.LEFT).get(Applicant_.id)));
            }
        }
        return specification;
    }
}
