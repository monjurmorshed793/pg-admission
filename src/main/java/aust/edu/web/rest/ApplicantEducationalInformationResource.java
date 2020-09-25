package aust.edu.web.rest;

import aust.edu.domain.ApplicantEducationalInformation;
import aust.edu.service.ApplicantEducationalInformationService;
import aust.edu.web.rest.errors.BadRequestAlertException;
import aust.edu.service.dto.ApplicantEducationalInformationCriteria;
import aust.edu.service.ApplicantEducationalInformationQueryService;

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
 * REST controller for managing {@link aust.edu.domain.ApplicantEducationalInformation}.
 */
@RestController
@RequestMapping("/api")
public class ApplicantEducationalInformationResource {

    private final Logger log = LoggerFactory.getLogger(ApplicantEducationalInformationResource.class);

    private static final String ENTITY_NAME = "applicantEducationalInformation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicantEducationalInformationService applicantEducationalInformationService;

    private final ApplicantEducationalInformationQueryService applicantEducationalInformationQueryService;

    public ApplicantEducationalInformationResource(ApplicantEducationalInformationService applicantEducationalInformationService, ApplicantEducationalInformationQueryService applicantEducationalInformationQueryService) {
        this.applicantEducationalInformationService = applicantEducationalInformationService;
        this.applicantEducationalInformationQueryService = applicantEducationalInformationQueryService;
    }

    /**
     * {@code POST  /applicant-educational-informations} : Create a new applicantEducationalInformation.
     *
     * @param applicantEducationalInformation the applicantEducationalInformation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicantEducationalInformation, or with status {@code 400 (Bad Request)} if the applicantEducationalInformation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applicant-educational-informations")
    public ResponseEntity<ApplicantEducationalInformation> createApplicantEducationalInformation(@Valid @RequestBody ApplicantEducationalInformation applicantEducationalInformation) throws URISyntaxException {
        log.debug("REST request to save ApplicantEducationalInformation : {}", applicantEducationalInformation);
        if (applicantEducationalInformation.getId() != null) {
            throw new BadRequestAlertException("A new applicantEducationalInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicantEducationalInformation result = applicantEducationalInformationService.save(applicantEducationalInformation);
        return ResponseEntity.created(new URI("/api/applicant-educational-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applicant-educational-informations} : Updates an existing applicantEducationalInformation.
     *
     * @param applicantEducationalInformation the applicantEducationalInformation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicantEducationalInformation,
     * or with status {@code 400 (Bad Request)} if the applicantEducationalInformation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicantEducationalInformation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applicant-educational-informations")
    public ResponseEntity<ApplicantEducationalInformation> updateApplicantEducationalInformation(@Valid @RequestBody ApplicantEducationalInformation applicantEducationalInformation) throws URISyntaxException {
        log.debug("REST request to update ApplicantEducationalInformation : {}", applicantEducationalInformation);
        if (applicantEducationalInformation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicantEducationalInformation result = applicantEducationalInformationService.save(applicantEducationalInformation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applicantEducationalInformation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /applicant-educational-informations} : get all the applicantEducationalInformations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicantEducationalInformations in body.
     */
    @GetMapping("/applicant-educational-informations")
    public ResponseEntity<List<ApplicantEducationalInformation>> getAllApplicantEducationalInformations(ApplicantEducationalInformationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ApplicantEducationalInformations by criteria: {}", criteria);
        Page<ApplicantEducationalInformation> page = applicantEducationalInformationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /applicant-educational-informations/count} : count all the applicantEducationalInformations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/applicant-educational-informations/count")
    public ResponseEntity<Long> countApplicantEducationalInformations(ApplicantEducationalInformationCriteria criteria) {
        log.debug("REST request to count ApplicantEducationalInformations by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicantEducationalInformationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /applicant-educational-informations/:id} : get the "id" applicantEducationalInformation.
     *
     * @param id the id of the applicantEducationalInformation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicantEducationalInformation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applicant-educational-informations/{id}")
    public ResponseEntity<ApplicantEducationalInformation> getApplicantEducationalInformation(@PathVariable Long id) {
        log.debug("REST request to get ApplicantEducationalInformation : {}", id);
        Optional<ApplicantEducationalInformation> applicantEducationalInformation = applicantEducationalInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicantEducationalInformation);
    }

    /**
     * {@code DELETE  /applicant-educational-informations/:id} : delete the "id" applicantEducationalInformation.
     *
     * @param id the id of the applicantEducationalInformation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applicant-educational-informations/{id}")
    public ResponseEntity<Void> deleteApplicantEducationalInformation(@PathVariable Long id) {
        log.debug("REST request to delete ApplicantEducationalInformation : {}", id);
        applicantEducationalInformationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
