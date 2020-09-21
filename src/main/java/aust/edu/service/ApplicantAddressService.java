package aust.edu.service;

import aust.edu.domain.ApplicantAddress;
import aust.edu.repository.ApplicantAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ApplicantAddress}.
 */
@Service
@Transactional
public class ApplicantAddressService {

    private final Logger log = LoggerFactory.getLogger(ApplicantAddressService.class);

    private final ApplicantAddressRepository applicantAddressRepository;

    public ApplicantAddressService(ApplicantAddressRepository applicantAddressRepository) {
        this.applicantAddressRepository = applicantAddressRepository;
    }

    /**
     * Save a applicantAddress.
     *
     * @param applicantAddress the entity to save.
     * @return the persisted entity.
     */
    public ApplicantAddress save(ApplicantAddress applicantAddress) {
        log.debug("Request to save ApplicantAddress : {}", applicantAddress);
        return applicantAddressRepository.save(applicantAddress);
    }

    /**
     * Get all the applicantAddresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicantAddress> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicantAddresses");
        return applicantAddressRepository.findAll(pageable);
    }


    /**
     * Get one applicantAddress by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApplicantAddress> findOne(Long id) {
        log.debug("Request to get ApplicantAddress : {}", id);
        return applicantAddressRepository.findById(id);
    }

    /**
     * Delete the applicantAddress by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApplicantAddress : {}", id);
        applicantAddressRepository.deleteById(id);
    }
}
