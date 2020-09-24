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

import aust.edu.domain.Semester;
import aust.edu.domain.*; // for static metamodels
import aust.edu.repository.SemesterRepository;
import aust.edu.service.dto.SemesterCriteria;

/**
 * Service for executing complex queries for {@link Semester} entities in the database.
 * The main input is a {@link SemesterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Semester} or a {@link Page} of {@link Semester} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SemesterQueryService extends QueryService<Semester> {

    private final Logger log = LoggerFactory.getLogger(SemesterQueryService.class);

    private final SemesterRepository semesterRepository;

    public SemesterQueryService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    /**
     * Return a {@link List} of {@link Semester} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Semester> findByCriteria(SemesterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Semester> specification = createSpecification(criteria);
        return semesterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Semester} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Semester> findByCriteria(SemesterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Semester> specification = createSpecification(criteria);
        return semesterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SemesterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Semester> specification = createSpecification(criteria);
        return semesterRepository.count(specification);
    }

    /**
     * Function to convert {@link SemesterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Semester> createSpecification(SemesterCriteria criteria) {
        Specification<Semester> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Semester_.id));
            }
            if (criteria.getSemesterName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSemesterName(), Semester_.semesterName));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), Semester_.isActive));
            }
        }
        return specification;
    }
}
