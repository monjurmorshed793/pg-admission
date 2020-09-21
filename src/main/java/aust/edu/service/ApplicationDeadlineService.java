package aust.edu.service;

import aust.edu.domain.ApplicationDeadline;
import aust.edu.repository.ApplicationDeadlineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ApplicationDeadline}.
 */
@Service
@Transactional
public class ApplicationDeadlineService {

    private final Logger log = LoggerFactory.getLogger(ApplicationDeadlineService.class);

    private final ApplicationDeadlineRepository applicationDeadlineRepository;

    public ApplicationDeadlineService(ApplicationDeadlineRepository applicationDeadlineRepository) {
        this.applicationDeadlineRepository = applicationDeadlineRepository;
    }

    /**
     * Save a applicationDeadline.
     *
     * @param applicationDeadline the entity to save.
     * @return the persisted entity.
     */
    public ApplicationDeadline save(ApplicationDeadline applicationDeadline) {
        log.debug("Request to save ApplicationDeadline : {}", applicationDeadline);
        return applicationDeadlineRepository.save(applicationDeadline);
    }

    /**
     * Get all the applicationDeadlines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationDeadline> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicationDeadlines");
        return applicationDeadlineRepository.findAll(pageable);
    }


    /**
     * Get one applicationDeadline by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApplicationDeadline> findOne(Long id) {
        log.debug("Request to get ApplicationDeadline : {}", id);
        return applicationDeadlineRepository.findById(id);
    }

    /**
     * Delete the applicationDeadline by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApplicationDeadline : {}", id);
        applicationDeadlineRepository.deleteById(id);
    }
}
