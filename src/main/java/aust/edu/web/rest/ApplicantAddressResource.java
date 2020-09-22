package aust.edu.web.rest;

import aust.edu.domain.ApplicantAddress;
import aust.edu.service.ApplicantAddressService;
import aust.edu.web.rest.errors.BadRequestAlertException;
import aust.edu.service.dto.ApplicantAddressCriteria;
import aust.edu.service.ApplicantAddressQueryService;

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
 * REST controller for managing {@link aust.edu.domain.ApplicantAddress}.
 */
@RestController
@RequestMapping("/api")
public class ApplicantAddressResource {

    private final Logger log = LoggerFactory.getLogger(ApplicantAddressResource.class);

    private static final String ENTITY_NAME = "applicantAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicantAddressService applicantAddressService;

    private final ApplicantAddressQueryService applicantAddressQueryService;

    public ApplicantAddressResource(ApplicantAddressService applicantAddressService, ApplicantAddressQueryService applicantAddressQueryService) {
        this.applicantAddressService = applicantAddressService;
        this.applicantAddressQueryService = applicantAddressQueryService;
    }

    /**
     * {@code POST  /applicant-addresses} : Create a new applicantAddress.
     *
     * @param applicantAddress the applicantAddress to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicantAddress, or with status {@code 400 (Bad Request)} if the applicantAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applicant-addresses")
    public ResponseEntity<ApplicantAddress> createApplicantAddress(@Valid @RequestBody ApplicantAddress applicantAddress) throws URISyntaxException {
        log.debug("REST request to save ApplicantAddress : {}", applicantAddress);
        if (applicantAddress.getId() != null) {
            throw new BadRequestAlertException("A new applicantAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicantAddress result = applicantAddressService.save(applicantAddress);
        return ResponseEntity.created(new URI("/api/applicant-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applicant-addresses} : Updates an existing applicantAddress.
     *
     * @param applicantAddress the applicantAddress to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicantAddress,
     * or with status {@code 400 (Bad Request)} if the applicantAddress is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicantAddress couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applicant-addresses")
    public ResponseEntity<ApplicantAddress> updateApplicantAddress(@Valid @RequestBody ApplicantAddress applicantAddress) throws URISyntaxException {
        log.debug("REST request to update ApplicantAddress : {}", applicantAddress);
        if (applicantAddress.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicantAddress result = applicantAddressService.save(applicantAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applicantAddress.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /applicant-addresses} : get all the applicantAddresses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicantAddresses in body.
     */
    @GetMapping("/applicant-addresses")
    public ResponseEntity<List<ApplicantAddress>> getAllApplicantAddresses(ApplicantAddressCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ApplicantAddresses by criteria: {}", criteria);
        Page<ApplicantAddress> page = applicantAddressQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /applicant-addresses/count} : count all the applicantAddresses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/applicant-addresses/count")
    public ResponseEntity<Long> countApplicantAddresses(ApplicantAddressCriteria criteria) {
        log.debug("REST request to count ApplicantAddresses by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicantAddressQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /applicant-addresses/:id} : get the "id" applicantAddress.
     *
     * @param id the id of the applicantAddress to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicantAddress, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applicant-addresses/{id}")
    public ResponseEntity<ApplicantAddress> getApplicantAddress(@PathVariable Long id) {
        log.debug("REST request to get ApplicantAddress : {}", id);
        Optional<ApplicantAddress> applicantAddress = applicantAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicantAddress);
    }

    /**
     * {@code DELETE  /applicant-addresses/:id} : delete the "id" applicantAddress.
     *
     * @param id the id of the applicantAddress to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applicant-addresses/{id}")
    public ResponseEntity<Void> deleteApplicantAddress(@PathVariable Long id) {
        log.debug("REST request to delete ApplicantAddress : {}", id);
        applicantAddressService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
