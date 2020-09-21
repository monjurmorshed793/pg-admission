package aust.edu.service;

import aust.edu.domain.ExamType;
import aust.edu.repository.ExamTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ExamType}.
 */
@Service
@Transactional
public class ExamTypeService {

    private final Logger log = LoggerFactory.getLogger(ExamTypeService.class);

    private final ExamTypeRepository examTypeRepository;

    public ExamTypeService(ExamTypeRepository examTypeRepository) {
        this.examTypeRepository = examTypeRepository;
    }

    /**
     * Save a examType.
     *
     * @param examType the entity to save.
     * @return the persisted entity.
     */
    public ExamType save(ExamType examType) {
        log.debug("Request to save ExamType : {}", examType);
        return examTypeRepository.save(examType);
    }

    /**
     * Get all the examTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ExamType> findAll(Pageable pageable) {
        log.debug("Request to get all ExamTypes");
        return examTypeRepository.findAll(pageable);
    }


    /**
     * Get one examType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExamType> findOne(Long id) {
        log.debug("Request to get ExamType : {}", id);
        return examTypeRepository.findById(id);
    }

    /**
     * Delete the examType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ExamType : {}", id);
        examTypeRepository.deleteById(id);
    }
}
