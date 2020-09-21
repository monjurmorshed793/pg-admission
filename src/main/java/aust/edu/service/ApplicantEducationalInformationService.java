package aust.edu.service;

import aust.edu.domain.ApplicantEducationalInformation;
import aust.edu.repository.ApplicantEducationalInformationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ApplicantEducationalInformation}.
 */
@Service
@Transactional
public class ApplicantEducationalInformationService {

    private final Logger log = LoggerFactory.getLogger(ApplicantEducationalInformationService.class);

    private final ApplicantEducationalInformationRepository applicantEducationalInformationRepository;

    public ApplicantEducationalInformationService(ApplicantEducationalInformationRepository applicantEducationalInformationRepository) {
        this.applicantEducationalInformationRepository = applicantEducationalInformationRepository;
    }

    /**
     * Save a applicantEducationalInformation.
     *
     * @param applicantEducationalInformation the entity to save.
     * @return the persisted entity.
     */
    public ApplicantEducationalInformation save(ApplicantEducationalInformation applicantEducationalInformation) {
        log.debug("Request to save ApplicantEducationalInformation : {}", applicantEducationalInformation);
        return applicantEducationalInformationRepository.save(applicantEducationalInformation);
    }

    /**
     * Get all the applicantEducationalInformations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicantEducationalInformation> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicantEducationalInformations");
        return applicantEducationalInformationRepository.findAll(pageable);
    }


    /**
     * Get one applicantEducationalInformation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApplicantEducationalInformation> findOne(Long id) {
        log.debug("Request to get ApplicantEducationalInformation : {}", id);
        return applicantEducationalInformationRepository.findById(id);
    }

    /**
     * Delete the applicantEducationalInformation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApplicantEducationalInformation : {}", id);
        applicantEducationalInformationRepository.deleteById(id);
    }
}
