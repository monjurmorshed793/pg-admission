package aust.edu.web.rest;

import aust.edu.domain.ApplicantPersonalInfo;
import aust.edu.service.ApplicantPersonalInfoService;
import aust.edu.web.rest.errors.BadRequestAlertException;
import aust.edu.service.dto.ApplicantPersonalInfoCriteria;
import aust.edu.service.ApplicantPersonalInfoQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link aust.edu.domain.ApplicantPersonalInfo}.
 */
@RestController
@RequestMapping("/api")
public class ApplicantPersonalInfoResource {

    private final Logger log = LoggerFactory.getLogger(ApplicantPersonalInfoResource.class);

    private static final String ENTITY_NAME = "applicantPersonalInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicantPersonalInfoService applicantPersonalInfoService;

    private final ApplicantPersonalInfoQueryService applicantPersonalInfoQueryService;

    public ApplicantPersonalInfoResource(ApplicantPersonalInfoService applicantPersonalInfoService, ApplicantPersonalInfoQueryService applicantPersonalInfoQueryService) {
        this.applicantPersonalInfoService = applicantPersonalInfoService;
        this.applicantPersonalInfoQueryService = applicantPersonalInfoQueryService;
    }

    /**
     * {@code POST  /applicant-personal-infos} : Create a new applicantPersonalInfo.
     *
     * @param applicantPersonalInfo the applicantPersonalInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicantPersonalInfo, or with status {@code 400 (Bad Request)} if the applicantPersonalInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applicant-personal-infos")
    public ResponseEntity<ApplicantPersonalInfo> createApplicantPersonalInfo(@Valid @RequestBody ApplicantPersonalInfo applicantPersonalInfo) throws URISyntaxException {
        log.debug("REST request to save ApplicantPersonalInfo : {}", applicantPersonalInfo);
        if (applicantPersonalInfo.getId() != null) {
            throw new BadRequestAlertException("A new applicantPersonalInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicantPersonalInfo result = applicantPersonalInfoService.save(applicantPersonalInfo);
        return ResponseEntity.created(new URI("/api/applicant-personal-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applicant-personal-infos} : Updates an existing applicantPersonalInfo.
     *
     * @param applicantPersonalInfo the applicantPersonalInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicantPersonalInfo,
     * or with status {@code 400 (Bad Request)} if the applicantPersonalInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicantPersonalInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applicant-personal-infos")
    public ResponseEntity<ApplicantPersonalInfo> updateApplicantPersonalInfo(@Valid @RequestBody ApplicantPersonalInfo applicantPersonalInfo) throws URISyntaxException {
        log.debug("REST request to update ApplicantPersonalInfo : {}", applicantPersonalInfo);
        if (applicantPersonalInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicantPersonalInfo result = applicantPersonalInfoService.save(applicantPersonalInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applicantPersonalInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /applicant-personal-infos} : get all the applicantPersonalInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicantPersonalInfos in body.
     */
    @GetMapping("/applicant-personal-infos")
    public ResponseEntity<List<ApplicantPersonalInfo>> getAllApplicantPersonalInfos(ApplicantPersonalInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ApplicantPersonalInfos by criteria: {}", criteria);
        Page<ApplicantPersonalInfo> page = applicantPersonalInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /applicant-personal-infos/count} : count all the applicantPersonalInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/applicant-personal-infos/count")
    public ResponseEntity<Long> countApplicantPersonalInfos(ApplicantPersonalInfoCriteria criteria) {
        log.debug("REST request to count ApplicantPersonalInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicantPersonalInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /applicant-personal-infos/:id} : get the "id" applicantPersonalInfo.
     *
     * @param id the id of the applicantPersonalInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicantPersonalInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applicant-personal-infos/{id}")
    public ResponseEntity<ApplicantPersonalInfo> getApplicantPersonalInfo(@PathVariable Long id) {
        log.debug("REST request to get ApplicantPersonalInfo : {}", id);
        Optional<ApplicantPersonalInfo> applicantPersonalInfo = applicantPersonalInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicantPersonalInfo);
    }

    /**
     * {@code DELETE  /applicant-personal-infos/:id} : delete the "id" applicantPersonalInfo.
     *
     * @param id the id of the applicantPersonalInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applicant-personal-infos/{id}")
    public ResponseEntity<Void> deleteApplicantPersonalInfo(@PathVariable Long id) {
        log.debug("REST request to delete ApplicantPersonalInfo : {}", id);
        applicantPersonalInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
