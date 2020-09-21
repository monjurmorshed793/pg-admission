package aust.edu.service;

import aust.edu.domain.Semester;
import aust.edu.repository.SemesterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Semester}.
 */
@Service
@Transactional
public class SemesterService {

    private final Logger log = LoggerFactory.getLogger(SemesterService.class);

    private final SemesterRepository semesterRepository;

    public SemesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    /**
     * Save a semester.
     *
     * @param semester the entity to save.
     * @return the persisted entity.
     */
    public Semester save(Semester semester) {
        log.debug("Request to save Semester : {}", semester);
        return semesterRepository.save(semester);
    }

    /**
     * Get all the semesters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Semester> findAll(Pageable pageable) {
        log.debug("Request to get all Semesters");
        return semesterRepository.findAll(pageable);
    }


    /**
     * Get one semester by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Semester> findOne(Long id) {
        log.debug("Request to get Semester : {}", id);
        return semesterRepository.findById(id);
    }

    /**
     * Delete the semester by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Semester : {}", id);
        semesterRepository.deleteById(id);
    }
}
