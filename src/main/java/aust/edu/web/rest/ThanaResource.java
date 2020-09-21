package aust.edu.web.rest;

import aust.edu.domain.Thana;
import aust.edu.service.ThanaService;
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
 * REST controller for managing {@link aust.edu.domain.Thana}.
 */
@RestController
@RequestMapping("/api")
public class ThanaResource {

    private final Logger log = LoggerFactory.getLogger(ThanaResource.class);

    private static final String ENTITY_NAME = "thana";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThanaService thanaService;

    public ThanaResource(ThanaService thanaService) {
        this.thanaService = thanaService;
    }

    /**
     * {@code POST  /thanas} : Create a new thana.
     *
     * @param thana the thana to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thana, or with status {@code 400 (Bad Request)} if the thana has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/thanas")
    public ResponseEntity<Thana> createThana(@Valid @RequestBody Thana thana) throws URISyntaxException {
        log.debug("REST request to save Thana : {}", thana);
        if (thana.getId() != null) {
            throw new BadRequestAlertException("A new thana cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Thana result = thanaService.save(thana);
        return ResponseEntity.created(new URI("/api/thanas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /thanas} : Updates an existing thana.
     *
     * @param thana the thana to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thana,
     * or with status {@code 400 (Bad Request)} if the thana is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thana couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/thanas")
    public ResponseEntity<Thana> updateThana(@Valid @RequestBody Thana thana) throws URISyntaxException {
        log.debug("REST request to update Thana : {}", thana);
        if (thana.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Thana result = thanaService.save(thana);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, thana.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /thanas} : get all the thanas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thanas in body.
     */
    @GetMapping("/thanas")
    public ResponseEntity<List<Thana>> getAllThanas(Pageable pageable) {
        log.debug("REST request to get a page of Thanas");
        Page<Thana> page = thanaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /thanas/:id} : get the "id" thana.
     *
     * @param id the id of the thana to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thana, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/thanas/{id}")
    public ResponseEntity<Thana> getThana(@PathVariable Long id) {
        log.debug("REST request to get Thana : {}", id);
        Optional<Thana> thana = thanaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(thana);
    }

    /**
     * {@code DELETE  /thanas/:id} : delete the "id" thana.
     *
     * @param id the id of the thana to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/thanas/{id}")
    public ResponseEntity<Void> deleteThana(@PathVariable Long id) {
        log.debug("REST request to delete Thana : {}", id);
        thanaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
