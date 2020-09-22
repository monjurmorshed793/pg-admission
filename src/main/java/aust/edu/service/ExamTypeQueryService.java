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

import aust.edu.domain.ExamType;
import aust.edu.domain.*; // for static metamodels
import aust.edu.repository.ExamTypeRepository;
import aust.edu.service.dto.ExamTypeCriteria;

/**
 * Service for executing complex queries for {@link ExamType} entities in the database.
 * The main input is a {@link ExamTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExamType} or a {@link Page} of {@link ExamType} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExamTypeQueryService extends QueryService<ExamType> {

    private final Logger log = LoggerFactory.getLogger(ExamTypeQueryService.class);

    private final ExamTypeRepository examTypeRepository;

    public ExamTypeQueryService(ExamTypeRepository examTypeRepository) {
        this.examTypeRepository = examTypeRepository;
    }

    /**
     * Return a {@link List} of {@link ExamType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExamType> findByCriteria(ExamTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ExamType> specification = createSpecification(criteria);
        return examTypeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ExamType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExamType> findByCriteria(ExamTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ExamType> specification = createSpecification(criteria);
        return examTypeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExamTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ExamType> specification = createSpecification(criteria);
        return examTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link ExamTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ExamType> createSpecification(ExamTypeCriteria criteria) {
        Specification<ExamType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ExamType_.id));
            }
            if (criteria.getExamTypeCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExamTypeCode(), ExamType_.examTypeCode));
            }
            if (criteria.getExamTypeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExamTypeName(), ExamType_.examTypeName));
            }
        }
        return specification;
    }
}
