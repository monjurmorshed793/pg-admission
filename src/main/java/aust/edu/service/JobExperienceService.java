package aust.edu.service;

import aust.edu.domain.JobExperience;
import aust.edu.repository.JobExperienceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link JobExperience}.
 */
@Service
@Transactional
public class JobExperienceService {

    private final Logger log = LoggerFactory.getLogger(JobExperienceService.class);

    private final JobExperienceRepository jobExperienceRepository;

    public JobExperienceService(JobExperienceRepository jobExperienceRepository) {
        this.jobExperienceRepository = jobExperienceRepository;
    }

    /**
     * Save a jobExperience.
     *
     * @param jobExperience the entity to save.
     * @return the persisted entity.
     */
    public JobExperience save(JobExperience jobExperience) {
        log.debug("Request to save JobExperience : {}", jobExperience);
        return jobExperienceRepository.save(jobExperience);
    }

    /**
     * Get all the jobExperiences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JobExperience> findAll(Pageable pageable) {
        log.debug("Request to get all JobExperiences");
        return jobExperienceRepository.findAll(pageable);
    }


    /**
     * Get one jobExperience by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JobExperience> findOne(Long id) {
        log.debug("Request to get JobExperience : {}", id);
        return jobExperienceRepository.findById(id);
    }

    /**
     * Delete the jobExperience by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JobExperience : {}", id);
        jobExperienceRepository.deleteById(id);
    }
}
