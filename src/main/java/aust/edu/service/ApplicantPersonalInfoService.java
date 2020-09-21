package aust.edu.service;

import aust.edu.domain.ApplicantPersonalInfo;
import aust.edu.repository.ApplicantPersonalInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ApplicantPersonalInfo}.
 */
@Service
@Transactional
public class ApplicantPersonalInfoService {

    private final Logger log = LoggerFactory.getLogger(ApplicantPersonalInfoService.class);

    private final ApplicantPersonalInfoRepository applicantPersonalInfoRepository;

    public ApplicantPersonalInfoService(ApplicantPersonalInfoRepository applicantPersonalInfoRepository) {
        this.applicantPersonalInfoRepository = applicantPersonalInfoRepository;
    }

    /**
     * Save a applicantPersonalInfo.
     *
     * @param applicantPersonalInfo the entity to save.
     * @return the persisted entity.
     */
    public ApplicantPersonalInfo save(ApplicantPersonalInfo applicantPersonalInfo) {
        log.debug("Request to save ApplicantPersonalInfo : {}", applicantPersonalInfo);
        return applicantPersonalInfoRepository.save(applicantPersonalInfo);
    }

    /**
     * Get all the applicantPersonalInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicantPersonalInfo> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicantPersonalInfos");
        return applicantPersonalInfoRepository.findAll(pageable);
    }


    /**
     * Get one applicantPersonalInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApplicantPersonalInfo> findOne(Long id) {
        log.debug("Request to get ApplicantPersonalInfo : {}", id);
        return applicantPersonalInfoRepository.findById(id);
    }

    /**
     * Delete the applicantPersonalInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApplicantPersonalInfo : {}", id);
        applicantPersonalInfoRepository.deleteById(id);
    }
}
