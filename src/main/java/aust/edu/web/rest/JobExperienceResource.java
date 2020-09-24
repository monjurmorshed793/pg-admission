package aust.edu.web.rest;

import aust.edu.domain.JobExperience;
import aust.edu.service.JobExperienceService;
import aust.edu.web.rest.errors.BadRequestAlertException;
import aust.edu.service.dto.JobExperienceCriteria;
import aust.edu.service.JobExperienceQueryService;

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
 * REST controller for managing {@link aust.edu.domain.JobExperience}.
 */
@RestController
@RequestMapping("/api")
public class JobExperienceResource {

    private final Logger log = LoggerFactory.getLogger(JobExperienceResource.class);

    private static final String ENTITY_NAME = "jobExperience";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobExperienceService jobExperienceService;

    private final JobExperienceQueryService jobExperienceQueryService;

    public JobExperienceResource(JobExperienceService jobExperienceService, JobExperienceQueryService jobExperienceQueryService) {
        this.jobExperienceService = jobExperienceService;
        this.jobExperienceQueryService = jobExperienceQueryService;
    }

    /**
     * {@code POST  /job-experiences} : Create a new jobExperience.
     *
     * @param jobExperience the jobExperience to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobExperience, or with status {@code 400 (Bad Request)} if the jobExperience has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-experiences")
    public ResponseEntity<JobExperience> createJobExperience(@Valid @RequestBody JobExperience jobExperience) throws URISyntaxException {
        log.debug("REST request to save JobExperience : {}", jobExperience);
        if (jobExperience.getId() != null) {
            throw new BadRequestAlertException("A new jobExperience cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobExperience result = jobExperienceService.save(jobExperience);
        return ResponseEntity.created(new URI("/api/job-experiences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-experiences} : Updates an existing jobExperience.
     *
     * @param jobExperience the jobExperience to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobExperience,
     * or with status {@code 400 (Bad Request)} if the jobExperience is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobExperience couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-experiences")
    public ResponseEntity<JobExperience> updateJobExperience(@Valid @RequestBody JobExperience jobExperience) throws URISyntaxException {
        log.debug("REST request to update JobExperience : {}", jobExperience);
        if (jobExperience.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobExperience result = jobExperienceService.save(jobExperience);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, jobExperience.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /job-experiences} : get all the jobExperiences.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobExperiences in body.
     */
    @GetMapping("/job-experiences")
    public ResponseEntity<List<JobExperience>> getAllJobExperiences(JobExperienceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get JobExperiences by criteria: {}", criteria);
        Page<JobExperience> page = jobExperienceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-experiences/count} : count all the jobExperiences.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/job-experiences/count")
    public ResponseEntity<Long> countJobExperiences(JobExperienceCriteria criteria) {
        log.debug("REST request to count JobExperiences by criteria: {}", criteria);
        return ResponseEntity.ok().body(jobExperienceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /job-experiences/:id} : get the "id" jobExperience.
     *
     * @param id the id of the jobExperience to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobExperience, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-experiences/{id}")
    public ResponseEntity<JobExperience> getJobExperience(@PathVariable Long id) {
        log.debug("REST request to get JobExperience : {}", id);
        Optional<JobExperience> jobExperience = jobExperienceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobExperience);
    }

    /**
     * {@code DELETE  /job-experiences/:id} : delete the "id" jobExperience.
     *
     * @param id the id of the jobExperience to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-experiences/{id}")
    public ResponseEntity<Void> deleteJobExperience(@PathVariable Long id) {
        log.debug("REST request to delete JobExperience : {}", id);
        jobExperienceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
