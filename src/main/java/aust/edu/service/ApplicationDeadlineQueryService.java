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

import aust.edu.domain.ApplicationDeadline;
import aust.edu.domain.*; // for static metamodels
import aust.edu.repository.ApplicationDeadlineRepository;
import aust.edu.service.dto.ApplicationDeadlineCriteria;

/**
 * Service for executing complex queries for {@link ApplicationDeadline} entities in the database.
 * The main input is a {@link ApplicationDeadlineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicationDeadline} or a {@link Page} of {@link ApplicationDeadline} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicationDeadlineQueryService extends QueryService<ApplicationDeadline> {

    private final Logger log = LoggerFactory.getLogger(ApplicationDeadlineQueryService.class);

    private final ApplicationDeadlineRepository applicationDeadlineRepository;

    public ApplicationDeadlineQueryService(ApplicationDeadlineRepository applicationDeadlineRepository) {
        this.applicationDeadlineRepository = applicationDeadlineRepository;
    }

    /**
     * Return a {@link List} of {@link ApplicationDeadline} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicationDeadline> findByCriteria(ApplicationDeadlineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicationDeadline> specification = createSpecification(criteria);
        return applicationDeadlineRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ApplicationDeadline} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationDeadline> findByCriteria(ApplicationDeadlineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicationDeadline> specification = createSpecification(criteria);
        return applicationDeadlineRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicationDeadlineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicationDeadline> specification = createSpecification(criteria);
        return applicationDeadlineRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplicationDeadlineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApplicationDeadline> createSpecification(ApplicationDeadlineCriteria criteria) {
        Specification<ApplicationDeadline> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApplicationDeadline_.id));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), ApplicationDeadline_.fromDate));
            }
            if (criteria.getToDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getToDate(), ApplicationDeadline_.toDate));
            }
            if (criteria.getSemesterId() != null) {
                specification = specification.and(buildSpecification(criteria.getSemesterId(),
                    root -> root.join(ApplicationDeadline_.semester, JoinType.LEFT).get(Semester_.id)));
            }
            if (criteria.getProgramId() != null) {
                specification = specification.and(buildSpecification(criteria.getProgramId(),
                    root -> root.join(ApplicationDeadline_.program, JoinType.LEFT).get(Program_.id)));
            }
        }
        return specification;
    }
}
