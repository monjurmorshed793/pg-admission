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

import aust.edu.domain.Program;
import aust.edu.domain.*; // for static metamodels
import aust.edu.repository.ProgramRepository;
import aust.edu.service.dto.ProgramCriteria;

/**
 * Service for executing complex queries for {@link Program} entities in the database.
 * The main input is a {@link ProgramCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Program} or a {@link Page} of {@link Program} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProgramQueryService extends QueryService<Program> {

    private final Logger log = LoggerFactory.getLogger(ProgramQueryService.class);

    private final ProgramRepository programRepository;

    public ProgramQueryService(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    /**
     * Return a {@link List} of {@link Program} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Program> findByCriteria(ProgramCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Program> specification = createSpecification(criteria);
        return programRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Program} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Program> findByCriteria(ProgramCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Program> specification = createSpecification(criteria);
        return programRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProgramCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Program> specification = createSpecification(criteria);
        return programRepository.count(specification);
    }

    /**
     * Function to convert {@link ProgramCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Program> createSpecification(ProgramCriteria criteria) {
        Specification<Program> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Program_.id));
            }
            if (criteria.getProgramNameShort() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProgramNameShort(), Program_.programNameShort));
            }
            if (criteria.getProgramName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProgramName(), Program_.programName));
            }
        }
        return specification;
    }
}
