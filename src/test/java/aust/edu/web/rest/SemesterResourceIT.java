package aust.edu.web.rest;

import aust.edu.PgadmissionApp;
import aust.edu.domain.Semester;
import aust.edu.repository.SemesterRepository;
import aust.edu.service.SemesterService;
import aust.edu.service.dto.SemesterCriteria;
import aust.edu.service.SemesterQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SemesterResource} REST controller.
 */
@SpringBootTest(classes = PgadmissionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SemesterResourceIT {

    private static final String DEFAULT_SEMESTER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SEMESTER_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private SemesterQueryService semesterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSemesterMockMvc;

    private Semester semester;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Semester createEntity(EntityManager em) {
        Semester semester = new Semester()
            .semesterName(DEFAULT_SEMESTER_NAME)
            .isActive(DEFAULT_IS_ACTIVE);
        return semester;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Semester createUpdatedEntity(EntityManager em) {
        Semester semester = new Semester()
            .semesterName(UPDATED_SEMESTER_NAME)
            .isActive(UPDATED_IS_ACTIVE);
        return semester;
    }

    @BeforeEach
    public void initTest() {
        semester = createEntity(em);
    }

    @Test
    @Transactional
    public void createSemester() throws Exception {
        int databaseSizeBeforeCreate = semesterRepository.findAll().size();
        // Create the Semester
        restSemesterMockMvc.perform(post("/api/semesters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(semester)))
            .andExpect(status().isCreated());

        // Validate the Semester in the database
        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeCreate + 1);
        Semester testSemester = semesterList.get(semesterList.size() - 1);
        assertThat(testSemester.getSemesterName()).isEqualTo(DEFAULT_SEMESTER_NAME);
        assertThat(testSemester.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createSemesterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = semesterRepository.findAll().size();

        // Create the Semester with an existing ID
        semester.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSemesterMockMvc.perform(post("/api/semesters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(semester)))
            .andExpect(status().isBadRequest());

        // Validate the Semester in the database
        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSemesterNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = semesterRepository.findAll().size();
        // set the field null
        semester.setSemesterName(null);

        // Create the Semester, which fails.


        restSemesterMockMvc.perform(post("/api/semesters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(semester)))
            .andExpect(status().isBadRequest());

        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = semesterRepository.findAll().size();
        // set the field null
        semester.setIsActive(null);

        // Create the Semester, which fails.


        restSemesterMockMvc.perform(post("/api/semesters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(semester)))
            .andExpect(status().isBadRequest());

        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSemesters() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        // Get all the semesterList
        restSemesterMockMvc.perform(get("/api/semesters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(semester.getId().intValue())))
            .andExpect(jsonPath("$.[*].semesterName").value(hasItem(DEFAULT_SEMESTER_NAME)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSemester() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        // Get the semester
        restSemesterMockMvc.perform(get("/api/semesters/{id}", semester.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(semester.getId().intValue()))
            .andExpect(jsonPath("$.semesterName").value(DEFAULT_SEMESTER_NAME))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getSemestersByIdFiltering() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        Long id = semester.getId();

        defaultSemesterShouldBeFound("id.equals=" + id);
        defaultSemesterShouldNotBeFound("id.notEquals=" + id);

        defaultSemesterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSemesterShouldNotBeFound("id.greaterThan=" + id);

        defaultSemesterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSemesterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSemestersBySemesterNameIsEqualToSomething() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        // Get all the semesterList where semesterName equals to DEFAULT_SEMESTER_NAME
        defaultSemesterShouldBeFound("semesterName.equals=" + DEFAULT_SEMESTER_NAME);

        // Get all the semesterList where semesterName equals to UPDATED_SEMESTER_NAME
        defaultSemesterShouldNotBeFound("semesterName.equals=" + UPDATED_SEMESTER_NAME);
    }

    @Test
    @Transactional
    public void getAllSemestersBySemesterNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        // Get all the semesterList where semesterName not equals to DEFAULT_SEMESTER_NAME
        defaultSemesterShouldNotBeFound("semesterName.notEquals=" + DEFAULT_SEMESTER_NAME);

        // Get all the semesterList where semesterName not equals to UPDATED_SEMESTER_NAME
        defaultSemesterShouldBeFound("semesterName.notEquals=" + UPDATED_SEMESTER_NAME);
    }

    @Test
    @Transactional
    public void getAllSemestersBySemesterNameIsInShouldWork() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        // Get all the semesterList where semesterName in DEFAULT_SEMESTER_NAME or UPDATED_SEMESTER_NAME
        defaultSemesterShouldBeFound("semesterName.in=" + DEFAULT_SEMESTER_NAME + "," + UPDATED_SEMESTER_NAME);

        // Get all the semesterList where semesterName equals to UPDATED_SEMESTER_NAME
        defaultSemesterShouldNotBeFound("semesterName.in=" + UPDATED_SEMESTER_NAME);
    }

    @Test
    @Transactional
    public void getAllSemestersBySemesterNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        // Get all the semesterList where semesterName is not null
        defaultSemesterShouldBeFound("semesterName.specified=true");

        // Get all the semesterList where semesterName is null
        defaultSemesterShouldNotBeFound("semesterName.specified=false");
    }
                @Test
    @Transactional
    public void getAllSemestersBySemesterNameContainsSomething() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        // Get all the semesterList where semesterName contains DEFAULT_SEMESTER_NAME
        defaultSemesterShouldBeFound("semesterName.contains=" + DEFAULT_SEMESTER_NAME);

        // Get all the semesterList where semesterName contains UPDATED_SEMESTER_NAME
        defaultSemesterShouldNotBeFound("semesterName.contains=" + UPDATED_SEMESTER_NAME);
    }

    @Test
    @Transactional
    public void getAllSemestersBySemesterNameNotContainsSomething() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        // Get all the semesterList where semesterName does not contain DEFAULT_SEMESTER_NAME
        defaultSemesterShouldNotBeFound("semesterName.doesNotContain=" + DEFAULT_SEMESTER_NAME);

        // Get all the semesterList where semesterName does not contain UPDATED_SEMESTER_NAME
        defaultSemesterShouldBeFound("semesterName.doesNotContain=" + UPDATED_SEMESTER_NAME);
    }


    @Test
    @Transactional
    public void getAllSemestersByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        // Get all the semesterList where isActive equals to DEFAULT_IS_ACTIVE
        defaultSemesterShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the semesterList where isActive equals to UPDATED_IS_ACTIVE
        defaultSemesterShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllSemestersByIsActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        // Get all the semesterList where isActive not equals to DEFAULT_IS_ACTIVE
        defaultSemesterShouldNotBeFound("isActive.notEquals=" + DEFAULT_IS_ACTIVE);

        // Get all the semesterList where isActive not equals to UPDATED_IS_ACTIVE
        defaultSemesterShouldBeFound("isActive.notEquals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllSemestersByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        // Get all the semesterList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultSemesterShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the semesterList where isActive equals to UPDATED_IS_ACTIVE
        defaultSemesterShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllSemestersByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        // Get all the semesterList where isActive is not null
        defaultSemesterShouldBeFound("isActive.specified=true");

        // Get all the semesterList where isActive is null
        defaultSemesterShouldNotBeFound("isActive.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSemesterShouldBeFound(String filter) throws Exception {
        restSemesterMockMvc.perform(get("/api/semesters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(semester.getId().intValue())))
            .andExpect(jsonPath("$.[*].semesterName").value(hasItem(DEFAULT_SEMESTER_NAME)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restSemesterMockMvc.perform(get("/api/semesters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSemesterShouldNotBeFound(String filter) throws Exception {
        restSemesterMockMvc.perform(get("/api/semesters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSemesterMockMvc.perform(get("/api/semesters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSemester() throws Exception {
        // Get the semester
        restSemesterMockMvc.perform(get("/api/semesters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSemester() throws Exception {
        // Initialize the database
        semesterService.save(semester);

        int databaseSizeBeforeUpdate = semesterRepository.findAll().size();

        // Update the semester
        Semester updatedSemester = semesterRepository.findById(semester.getId()).get();
        // Disconnect from session so that the updates on updatedSemester are not directly saved in db
        em.detach(updatedSemester);
        updatedSemester
            .semesterName(UPDATED_SEMESTER_NAME)
            .isActive(UPDATED_IS_ACTIVE);

        restSemesterMockMvc.perform(put("/api/semesters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSemester)))
            .andExpect(status().isOk());

        // Validate the Semester in the database
        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeUpdate);
        Semester testSemester = semesterList.get(semesterList.size() - 1);
        assertThat(testSemester.getSemesterName()).isEqualTo(UPDATED_SEMESTER_NAME);
        assertThat(testSemester.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingSemester() throws Exception {
        int databaseSizeBeforeUpdate = semesterRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSemesterMockMvc.perform(put("/api/semesters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(semester)))
            .andExpect(status().isBadRequest());

        // Validate the Semester in the database
        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSemester() throws Exception {
        // Initialize the database
        semesterService.save(semester);

        int databaseSizeBeforeDelete = semesterRepository.findAll().size();

        // Delete the semester
        restSemesterMockMvc.perform(delete("/api/semesters/{id}", semester.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
