package aust.edu.service;

import aust.edu.domain.Applicant;
import aust.edu.repository.ApplicantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Applicant}.
 */
@Service
@Transactional
public class ApplicantService {

    private final Logger log = LoggerFactory.getLogger(ApplicantService.class);

    private final ApplicantRepository applicantRepository;

    public ApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    /**
     * Save a applicant.
     *
     * @param applicant the entity to save.
     * @return the persisted entity.
     */
    public Applicant save(Applicant applicant) {
        log.debug("Request to save Applicant : {}", applicant);
        return applicantRepository.save(applicant);
    }

    /**
     * Get all the applicants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Applicant> findAll(Pageable pageable) {
        log.debug("Request to get all Applicants");
        return applicantRepository.findAll(pageable);
    }



    /**
     *  Get all the applicants where ApplicantPersonalInformation is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Applicant> findAllWhereApplicantPersonalInformationIsNull() {
        log.debug("Request to get all applicants where ApplicantPersonalInformation is null");
        return StreamSupport
            .stream(applicantRepository.findAll().spliterator(), false)
            .filter(applicant -> applicant.getApplicantPersonalInformation() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one applicant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Applicant> findOne(Long id) {
        log.debug("Request to get Applicant : {}", id);
        return applicantRepository.findById(id);
    }

    /**
     * Delete the applicant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Applicant : {}", id);
        applicantRepository.deleteById(id);
    }
}
