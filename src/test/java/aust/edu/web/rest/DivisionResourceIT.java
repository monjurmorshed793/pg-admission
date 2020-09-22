package aust.edu.web.rest;

import aust.edu.PgadmissionApp;
import aust.edu.domain.Division;
import aust.edu.repository.DivisionRepository;
import aust.edu.service.DivisionService;
import aust.edu.service.dto.DivisionCriteria;
import aust.edu.service.DivisionQueryService;

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
 * Integration tests for the {@link DivisionResource} REST controller.
 */
@SpringBootTest(classes = PgadmissionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DivisionResourceIT {

    private static final String DEFAULT_DIVISION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DIVISION_NAME = "BBBBBBBBBB";

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private DivisionQueryService divisionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDivisionMockMvc;

    private Division division;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Division createEntity(EntityManager em) {
        Division division = new Division()
            .divisionName(DEFAULT_DIVISION_NAME);
        return division;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Division createUpdatedEntity(EntityManager em) {
        Division division = new Division()
            .divisionName(UPDATED_DIVISION_NAME);
        return division;
    }

    @BeforeEach
    public void initTest() {
        division = createEntity(em);
    }

    @Test
    @Transactional
    public void createDivision() throws Exception {
        int databaseSizeBeforeCreate = divisionRepository.findAll().size();
        // Create the Division
        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(division)))
            .andExpect(status().isCreated());

        // Validate the Division in the database
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeCreate + 1);
        Division testDivision = divisionList.get(divisionList.size() - 1);
        assertThat(testDivision.getDivisionName()).isEqualTo(DEFAULT_DIVISION_NAME);
    }

    @Test
    @Transactional
    public void createDivisionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = divisionRepository.findAll().size();

        // Create the Division with an existing ID
        division.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(division)))
            .andExpect(status().isBadRequest());

        // Validate the Division in the database
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDivisionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = divisionRepository.findAll().size();
        // set the field null
        division.setDivisionName(null);

        // Create the Division, which fails.


        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(division)))
            .andExpect(status().isBadRequest());

        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDivisions() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList
        restDivisionMockMvc.perform(get("/api/divisions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(division.getId().intValue())))
            .andExpect(jsonPath("$.[*].divisionName").value(hasItem(DEFAULT_DIVISION_NAME)));
    }
    
    @Test
    @Transactional
    public void getDivision() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get the division
        restDivisionMockMvc.perform(get("/api/divisions/{id}", division.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(division.getId().intValue()))
            .andExpect(jsonPath("$.divisionName").value(DEFAULT_DIVISION_NAME));
    }


    @Test
    @Transactional
    public void getDivisionsByIdFiltering() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        Long id = division.getId();

        defaultDivisionShouldBeFound("id.equals=" + id);
        defaultDivisionShouldNotBeFound("id.notEquals=" + id);

        defaultDivisionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDivisionShouldNotBeFound("id.greaterThan=" + id);

        defaultDivisionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDivisionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDivisionsByDivisionNameIsEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where divisionName equals to DEFAULT_DIVISION_NAME
        defaultDivisionShouldBeFound("divisionName.equals=" + DEFAULT_DIVISION_NAME);

        // Get all the divisionList where divisionName equals to UPDATED_DIVISION_NAME
        defaultDivisionShouldNotBeFound("divisionName.equals=" + UPDATED_DIVISION_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByDivisionNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where divisionName not equals to DEFAULT_DIVISION_NAME
        defaultDivisionShouldNotBeFound("divisionName.notEquals=" + DEFAULT_DIVISION_NAME);

        // Get all the divisionList where divisionName not equals to UPDATED_DIVISION_NAME
        defaultDivisionShouldBeFound("divisionName.notEquals=" + UPDATED_DIVISION_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByDivisionNameIsInShouldWork() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where divisionName in DEFAULT_DIVISION_NAME or UPDATED_DIVISION_NAME
        defaultDivisionShouldBeFound("divisionName.in=" + DEFAULT_DIVISION_NAME + "," + UPDATED_DIVISION_NAME);

        // Get all the divisionList where divisionName equals to UPDATED_DIVISION_NAME
        defaultDivisionShouldNotBeFound("divisionName.in=" + UPDATED_DIVISION_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByDivisionNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where divisionName is not null
        defaultDivisionShouldBeFound("divisionName.specified=true");

        // Get all the divisionList where divisionName is null
        defaultDivisionShouldNotBeFound("divisionName.specified=false");
    }
                @Test
    @Transactional
    public void getAllDivisionsByDivisionNameContainsSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where divisionName contains DEFAULT_DIVISION_NAME
        defaultDivisionShouldBeFound("divisionName.contains=" + DEFAULT_DIVISION_NAME);

        // Get all the divisionList where divisionName contains UPDATED_DIVISION_NAME
        defaultDivisionShouldNotBeFound("divisionName.contains=" + UPDATED_DIVISION_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByDivisionNameNotContainsSomething() throws Exception {
        // Initialize the database
        divisionRepository.saveAndFlush(division);

        // Get all the divisionList where divisionName does not contain DEFAULT_DIVISION_NAME
        defaultDivisionShouldNotBeFound("divisionName.doesNotContain=" + DEFAULT_DIVISION_NAME);

        // Get all the divisionList where divisionName does not contain UPDATED_DIVISION_NAME
        defaultDivisionShouldBeFound("divisionName.doesNotContain=" + UPDATED_DIVISION_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDivisionShouldBeFound(String filter) throws Exception {
        restDivisionMockMvc.perform(get("/api/divisions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(division.getId().intValue())))
            .andExpect(jsonPath("$.[*].divisionName").value(hasItem(DEFAULT_DIVISION_NAME)));

        // Check, that the count call also returns 1
        restDivisionMockMvc.perform(get("/api/divisions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDivisionShouldNotBeFound(String filter) throws Exception {
        restDivisionMockMvc.perform(get("/api/divisions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDivisionMockMvc.perform(get("/api/divisions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDivision() throws Exception {
        // Get the division
        restDivisionMockMvc.perform(get("/api/divisions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDivision() throws Exception {
        // Initialize the database
        divisionService.save(division);

        int databaseSizeBeforeUpdate = divisionRepository.findAll().size();

        // Update the division
        Division updatedDivision = divisionRepository.findById(division.getId()).get();
        // Disconnect from session so that the updates on updatedDivision are not directly saved in db
        em.detach(updatedDivision);
        updatedDivision
            .divisionName(UPDATED_DIVISION_NAME);

        restDivisionMockMvc.perform(put("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDivision)))
            .andExpect(status().isOk());

        // Validate the Division in the database
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeUpdate);
        Division testDivision = divisionList.get(divisionList.size() - 1);
        assertThat(testDivision.getDivisionName()).isEqualTo(UPDATED_DIVISION_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDivision() throws Exception {
        int databaseSizeBeforeUpdate = divisionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDivisionMockMvc.perform(put("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(division)))
            .andExpect(status().isBadRequest());

        // Validate the Division in the database
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDivision() throws Exception {
        // Initialize the database
        divisionService.save(division);

        int databaseSizeBeforeDelete = divisionRepository.findAll().size();

        // Delete the division
        restDivisionMockMvc.perform(delete("/api/divisions/{id}", division.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Division> divisionList = divisionRepository.findAll();
        assertThat(divisionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
