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

import aust.edu.domain.JobExperience;
import aust.edu.domain.*; // for static metamodels
import aust.edu.repository.JobExperienceRepository;
import aust.edu.service.dto.JobExperienceCriteria;

/**
 * Service for executing complex queries for {@link JobExperience} entities in the database.
 * The main input is a {@link JobExperienceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link JobExperience} or a {@link Page} of {@link JobExperience} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JobExperienceQueryService extends QueryService<JobExperience> {

    private final Logger log = LoggerFactory.getLogger(JobExperienceQueryService.class);

    private final JobExperienceRepository jobExperienceRepository;

    public JobExperienceQueryService(JobExperienceRepository jobExperienceRepository) {
        this.jobExperienceRepository = jobExperienceRepository;
    }

    /**
     * Return a {@link List} of {@link JobExperience} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<JobExperience> findByCriteria(JobExperienceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<JobExperience> specification = createSpecification(criteria);
        return jobExperienceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link JobExperience} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<JobExperience> findByCriteria(JobExperienceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<JobExperience> specification = createSpecification(criteria);
        return jobExperienceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JobExperienceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<JobExperience> specification = createSpecification(criteria);
        return jobExperienceRepository.count(specification);
    }

    /**
     * Function to convert {@link JobExperienceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<JobExperience> createSpecification(JobExperienceCriteria criteria) {
        Specification<JobExperience> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), JobExperience_.id));
            }
            if (criteria.getOrganizationName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganizationName(), JobExperience_.organizationName));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), JobExperience_.designation));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), JobExperience_.fromDate));
            }
            if (criteria.getToDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getToDate(), JobExperience_.toDate));
            }
            if (criteria.getCurrentlyWorking() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrentlyWorking(), JobExperience_.currentlyWorking));
            }
            if (criteria.getApplicantId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicantId(),
                    root -> root.join(JobExperience_.applicant, JoinType.LEFT).get(Applicant_.id)));
            }
        }
        return specification;
    }
}
