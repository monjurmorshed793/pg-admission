package aust.edu.web.rest;

import aust.edu.PgadmissionApp;
import aust.edu.domain.District;
import aust.edu.domain.Division;
import aust.edu.repository.DistrictRepository;
import aust.edu.service.DistrictService;
import aust.edu.service.dto.DistrictCriteria;
import aust.edu.service.DistrictQueryService;

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
 * Integration tests for the {@link DistrictResource} REST controller.
 */
@SpringBootTest(classes = PgadmissionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DistrictResourceIT {

    private static final String DEFAULT_DISTRICT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT_NAME = "BBBBBBBBBB";

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private DistrictQueryService districtQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDistrictMockMvc;

    private District district;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static District createEntity(EntityManager em) {
        District district = new District()
            .districtName(DEFAULT_DISTRICT_NAME);
        // Add required entity
        Division division;
        if (TestUtil.findAll(em, Division.class).isEmpty()) {
            division = DivisionResourceIT.createEntity(em);
            em.persist(division);
            em.flush();
        } else {
            division = TestUtil.findAll(em, Division.class).get(0);
        }
        district.setDivision(division);
        return district;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static District createUpdatedEntity(EntityManager em) {
        District district = new District()
            .districtName(UPDATED_DISTRICT_NAME);
        // Add required entity
        Division division;
        if (TestUtil.findAll(em, Division.class).isEmpty()) {
            division = DivisionResourceIT.createUpdatedEntity(em);
            em.persist(division);
            em.flush();
        } else {
            division = TestUtil.findAll(em, Division.class).get(0);
        }
        district.setDivision(division);
        return district;
    }

    @BeforeEach
    public void initTest() {
        district = createEntity(em);
    }

    @Test
    @Transactional
    public void createDistrict() throws Exception {
        int databaseSizeBeforeCreate = districtRepository.findAll().size();
        // Create the District
        restDistrictMockMvc.perform(post("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(district)))
            .andExpect(status().isCreated());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeCreate + 1);
        District testDistrict = districtList.get(districtList.size() - 1);
        assertThat(testDistrict.getDistrictName()).isEqualTo(DEFAULT_DISTRICT_NAME);
    }

    @Test
    @Transactional
    public void createDistrictWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = districtRepository.findAll().size();

        // Create the District with an existing ID
        district.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistrictMockMvc.perform(post("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(district)))
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDistrictNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = districtRepository.findAll().size();
        // set the field null
        district.setDistrictName(null);

        // Create the District, which fails.


        restDistrictMockMvc.perform(post("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(district)))
            .andExpect(status().isBadRequest());

        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDistricts() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList
        restDistrictMockMvc.perform(get("/api/districts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(district.getId().intValue())))
            .andExpect(jsonPath("$.[*].districtName").value(hasItem(DEFAULT_DISTRICT_NAME)));
    }
    
    @Test
    @Transactional
    public void getDistrict() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get the district
        restDistrictMockMvc.perform(get("/api/districts/{id}", district.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(district.getId().intValue()))
            .andExpect(jsonPath("$.districtName").value(DEFAULT_DISTRICT_NAME));
    }


    @Test
    @Transactional
    public void getDistrictsByIdFiltering() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        Long id = district.getId();

        defaultDistrictShouldBeFound("id.equals=" + id);
        defaultDistrictShouldNotBeFound("id.notEquals=" + id);

        defaultDistrictShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDistrictShouldNotBeFound("id.greaterThan=" + id);

        defaultDistrictShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDistrictShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDistrictsByDistrictNameIsEqualToSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where districtName equals to DEFAULT_DISTRICT_NAME
        defaultDistrictShouldBeFound("districtName.equals=" + DEFAULT_DISTRICT_NAME);

        // Get all the districtList where districtName equals to UPDATED_DISTRICT_NAME
        defaultDistrictShouldNotBeFound("districtName.equals=" + UPDATED_DISTRICT_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByDistrictNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where districtName not equals to DEFAULT_DISTRICT_NAME
        defaultDistrictShouldNotBeFound("districtName.notEquals=" + DEFAULT_DISTRICT_NAME);

        // Get all the districtList where districtName not equals to UPDATED_DISTRICT_NAME
        defaultDistrictShouldBeFound("districtName.notEquals=" + UPDATED_DISTRICT_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByDistrictNameIsInShouldWork() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where districtName in DEFAULT_DISTRICT_NAME or UPDATED_DISTRICT_NAME
        defaultDistrictShouldBeFound("districtName.in=" + DEFAULT_DISTRICT_NAME + "," + UPDATED_DISTRICT_NAME);

        // Get all the districtList where districtName equals to UPDATED_DISTRICT_NAME
        defaultDistrictShouldNotBeFound("districtName.in=" + UPDATED_DISTRICT_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByDistrictNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where districtName is not null
        defaultDistrictShouldBeFound("districtName.specified=true");

        // Get all the districtList where districtName is null
        defaultDistrictShouldNotBeFound("districtName.specified=false");
    }
                @Test
    @Transactional
    public void getAllDistrictsByDistrictNameContainsSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where districtName contains DEFAULT_DISTRICT_NAME
        defaultDistrictShouldBeFound("districtName.contains=" + DEFAULT_DISTRICT_NAME);

        // Get all the districtList where districtName contains UPDATED_DISTRICT_NAME
        defaultDistrictShouldNotBeFound("districtName.contains=" + UPDATED_DISTRICT_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByDistrictNameNotContainsSomething() throws Exception {
        // Initialize the database
        districtRepository.saveAndFlush(district);

        // Get all the districtList where districtName does not contain DEFAULT_DISTRICT_NAME
        defaultDistrictShouldNotBeFound("districtName.doesNotContain=" + DEFAULT_DISTRICT_NAME);

        // Get all the districtList where districtName does not contain UPDATED_DISTRICT_NAME
        defaultDistrictShouldBeFound("districtName.doesNotContain=" + UPDATED_DISTRICT_NAME);
    }


    @Test
    @Transactional
    public void getAllDistrictsByDivisionIsEqualToSomething() throws Exception {
        // Get already existing entity
        Division division = district.getDivision();
        districtRepository.saveAndFlush(district);
        Long divisionId = division.getId();

        // Get all the districtList where division equals to divisionId
        defaultDistrictShouldBeFound("divisionId.equals=" + divisionId);

        // Get all the districtList where division equals to divisionId + 1
        defaultDistrictShouldNotBeFound("divisionId.equals=" + (divisionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDistrictShouldBeFound(String filter) throws Exception {
        restDistrictMockMvc.perform(get("/api/districts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(district.getId().intValue())))
            .andExpect(jsonPath("$.[*].districtName").value(hasItem(DEFAULT_DISTRICT_NAME)));

        // Check, that the count call also returns 1
        restDistrictMockMvc.perform(get("/api/districts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDistrictShouldNotBeFound(String filter) throws Exception {
        restDistrictMockMvc.perform(get("/api/districts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDistrictMockMvc.perform(get("/api/districts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDistrict() throws Exception {
        // Get the district
        restDistrictMockMvc.perform(get("/api/districts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDistrict() throws Exception {
        // Initialize the database
        districtService.save(district);

        int databaseSizeBeforeUpdate = districtRepository.findAll().size();

        // Update the district
        District updatedDistrict = districtRepository.findById(district.getId()).get();
        // Disconnect from session so that the updates on updatedDistrict are not directly saved in db
        em.detach(updatedDistrict);
        updatedDistrict
            .districtName(UPDATED_DISTRICT_NAME);

        restDistrictMockMvc.perform(put("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDistrict)))
            .andExpect(status().isOk());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeUpdate);
        District testDistrict = districtList.get(districtList.size() - 1);
        assertThat(testDistrict.getDistrictName()).isEqualTo(UPDATED_DISTRICT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDistrict() throws Exception {
        int databaseSizeBeforeUpdate = districtRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictMockMvc.perform(put("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(district)))
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDistrict() throws Exception {
        // Initialize the database
        districtService.save(district);

        int databaseSizeBeforeDelete = districtRepository.findAll().size();

        // Delete the district
        restDistrictMockMvc.perform(delete("/api/districts/{id}", district.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<District> districtList = districtRepository.findAll();
        assertThat(districtList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
