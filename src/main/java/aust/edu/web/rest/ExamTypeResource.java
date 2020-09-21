package aust.edu.web.rest;

import aust.edu.domain.ExamType;
import aust.edu.service.ExamTypeService;
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
 * REST controller for managing {@link aust.edu.domain.ExamType}.
 */
@RestController
@RequestMapping("/api")
public class ExamTypeResource {

    private final Logger log = LoggerFactory.getLogger(ExamTypeResource.class);

    private static final String ENTITY_NAME = "examType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExamTypeService examTypeService;

    public ExamTypeResource(ExamTypeService examTypeService) {
        this.examTypeService = examTypeService;
    }

    /**
     * {@code POST  /exam-types} : Create a new examType.
     *
     * @param examType the examType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new examType, or with status {@code 400 (Bad Request)} if the examType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exam-types")
    public ResponseEntity<ExamType> createExamType(@Valid @RequestBody ExamType examType) throws URISyntaxException {
        log.debug("REST request to save ExamType : {}", examType);
        if (examType.getId() != null) {
            throw new BadRequestAlertException("A new examType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamType result = examTypeService.save(examType);
        return ResponseEntity.created(new URI("/api/exam-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exam-types} : Updates an existing examType.
     *
     * @param examType the examType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examType,
     * or with status {@code 400 (Bad Request)} if the examType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exam-types")
    public ResponseEntity<ExamType> updateExamType(@Valid @RequestBody ExamType examType) throws URISyntaxException {
        log.debug("REST request to update ExamType : {}", examType);
        if (examType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExamType result = examTypeService.save(examType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, examType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /exam-types} : get all the examTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of examTypes in body.
     */
    @GetMapping("/exam-types")
    public ResponseEntity<List<ExamType>> getAllExamTypes(Pageable pageable) {
        log.debug("REST request to get a page of ExamTypes");
        Page<ExamType> page = examTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /exam-types/:id} : get the "id" examType.
     *
     * @param id the id of the examType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the examType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exam-types/{id}")
    public ResponseEntity<ExamType> getExamType(@PathVariable Long id) {
        log.debug("REST request to get ExamType : {}", id);
        Optional<ExamType> examType = examTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(examType);
    }

    /**
     * {@code DELETE  /exam-types/:id} : delete the "id" examType.
     *
     * @param id the id of the examType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exam-types/{id}")
    public ResponseEntity<Void> deleteExamType(@PathVariable Long id) {
        log.debug("REST request to delete ExamType : {}", id);
        examTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
