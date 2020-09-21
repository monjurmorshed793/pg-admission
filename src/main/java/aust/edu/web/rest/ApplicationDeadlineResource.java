package aust.edu.web.rest;

import aust.edu.domain.ApplicationDeadline;
import aust.edu.service.ApplicationDeadlineService;
import aust.edu.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link aust.edu.domain.ApplicationDeadline}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationDeadlineResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationDeadlineResource.class);

    private static final String ENTITY_NAME = "applicationDeadline";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationDeadlineService applicationDeadlineService;

    public ApplicationDeadlineResource(ApplicationDeadlineService applicationDeadlineService) {
        this.applicationDeadlineService = applicationDeadlineService;
    }

    /**
     * {@code POST  /application-deadlines} : Create a new applicationDeadline.
     *
     * @param applicationDeadline the applicationDeadline to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationDeadline, or with status {@code 400 (Bad Request)} if the applicationDeadline has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-deadlines")
    public ResponseEntity<ApplicationDeadline> createApplicationDeadline(@Valid @RequestBody ApplicationDeadline applicationDeadline) throws URISyntaxException {
        log.debug("REST request to save ApplicationDeadline : {}", applicationDeadline);
        if (applicationDeadline.getId() != null) {
            throw new BadRequestAlertException("A new applicationDeadline cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationDeadline result = applicationDeadlineService.save(applicationDeadline);
        return ResponseEntity.created(new URI("/api/application-deadlines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-deadlines} : Updates an existing applicationDeadline.
     *
     * @param applicationDeadline the applicationDeadline to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationDeadline,
     * or with status {@code 400 (Bad Request)} if the applicationDeadline is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationDeadline couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-deadlines")
    public ResponseEntity<ApplicationDeadline> updateApplicationDeadline(@Valid @RequestBody ApplicationDeadline applicationDeadline) throws URISyntaxException {
        log.debug("REST request to update ApplicationDeadline : {}", applicationDeadline);
        if (applicationDeadline.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationDeadline result = applicationDeadlineService.save(applicationDeadline);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applicationDeadline.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /application-deadlines} : get all the applicationDeadlines.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationDeadlines in body.
     */
    @GetMapping("/application-deadlines")
    public ResponseEntity<List<ApplicationDeadline>> getAllApplicationDeadlines(Pageable pageable) {
        log.debug("REST request to get a page of ApplicationDeadlines");
        Page<ApplicationDeadline> page = applicationDeadlineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /application-deadlines/:id} : get the "id" applicationDeadline.
     *
     * @param id the id of the applicationDeadline to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationDeadline, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-deadlines/{id}")
    public ResponseEntity<ApplicationDeadline> getApplicationDeadline(@PathVariable Long id) {
        log.debug("REST request to get ApplicationDeadline : {}", id);
        Optional<ApplicationDeadline> applicationDeadline = applicationDeadlineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationDeadline);
    }

    /**
     * {@code DELETE  /application-deadlines/:id} : delete the "id" applicationDeadline.
     *
     * @param id the id of the applicationDeadline to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-deadlines/{id}")
    public ResponseEntity<Void> deleteApplicationDeadline(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationDeadline : {}", id);
        applicationDeadlineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
