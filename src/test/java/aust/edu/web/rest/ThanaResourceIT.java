package aust.edu.web.rest;

import aust.edu.PgadmissionApp;
import aust.edu.domain.Thana;
import aust.edu.domain.District;
import aust.edu.repository.ThanaRepository;
import aust.edu.service.ThanaService;
import aust.edu.service.dto.ThanaCriteria;
import aust.edu.service.ThanaQueryService;

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
 * Integration tests for the {@link ThanaResource} REST controller.
 */
@SpringBootTest(classes = PgadmissionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ThanaResourceIT {

    private static final String DEFAULT_THANA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_THANA_NAME = "BBBBBBBBBB";

    @Autowired
    private ThanaRepository thanaRepository;

    @Autowired
    private ThanaService thanaService;

    @Autowired
    private ThanaQueryService thanaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThanaMockMvc;

    private Thana thana;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thana createEntity(EntityManager em) {
        Thana thana = new Thana()
            .thanaName(DEFAULT_THANA_NAME);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        thana.setDistrict(district);
        return thana;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thana createUpdatedEntity(EntityManager em) {
        Thana thana = new Thana()
            .thanaName(UPDATED_THANA_NAME);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createUpdatedEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        thana.setDistrict(district);
        return thana;
    }

    @BeforeEach
    public void initTest() {
        thana = createEntity(em);
    }

    @Test
    @Transactional
    public void createThana() throws Exception {
        int databaseSizeBeforeCreate = thanaRepository.findAll().size();
        // Create the Thana
        restThanaMockMvc.perform(post("/api/thanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(thana)))
            .andExpect(status().isCreated());

        // Validate the Thana in the database
        List<Thana> thanaList = thanaRepository.findAll();
        assertThat(thanaList).hasSize(databaseSizeBeforeCreate + 1);
        Thana testThana = thanaList.get(thanaList.size() - 1);
        assertThat(testThana.getThanaName()).isEqualTo(DEFAULT_THANA_NAME);
    }

    @Test
    @Transactional
    public void createThanaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = thanaRepository.findAll().size();

        // Create the Thana with an existing ID
        thana.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThanaMockMvc.perform(post("/api/thanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(thana)))
            .andExpect(status().isBadRequest());

        // Validate the Thana in the database
        List<Thana> thanaList = thanaRepository.findAll();
        assertThat(thanaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkThanaNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = thanaRepository.findAll().size();
        // set the field null
        thana.setThanaName(null);

        // Create the Thana, which fails.


        restThanaMockMvc.perform(post("/api/thanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(thana)))
            .andExpect(status().isBadRequest());

        List<Thana> thanaList = thanaRepository.findAll();
        assertThat(thanaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThanas() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList
        restThanaMockMvc.perform(get("/api/thanas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thana.getId().intValue())))
            .andExpect(jsonPath("$.[*].thanaName").value(hasItem(DEFAULT_THANA_NAME)));
    }
    
    @Test
    @Transactional
    public void getThana() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get the thana
        restThanaMockMvc.perform(get("/api/thanas/{id}", thana.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thana.getId().intValue()))
            .andExpect(jsonPath("$.thanaName").value(DEFAULT_THANA_NAME));
    }


    @Test
    @Transactional
    public void getThanasByIdFiltering() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        Long id = thana.getId();

        defaultThanaShouldBeFound("id.equals=" + id);
        defaultThanaShouldNotBeFound("id.notEquals=" + id);

        defaultThanaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultThanaShouldNotBeFound("id.greaterThan=" + id);

        defaultThanaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultThanaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllThanasByThanaNameIsEqualToSomething() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where thanaName equals to DEFAULT_THANA_NAME
        defaultThanaShouldBeFound("thanaName.equals=" + DEFAULT_THANA_NAME);

        // Get all the thanaList where thanaName equals to UPDATED_THANA_NAME
        defaultThanaShouldNotBeFound("thanaName.equals=" + UPDATED_THANA_NAME);
    }

    @Test
    @Transactional
    public void getAllThanasByThanaNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where thanaName not equals to DEFAULT_THANA_NAME
        defaultThanaShouldNotBeFound("thanaName.notEquals=" + DEFAULT_THANA_NAME);

        // Get all the thanaList where thanaName not equals to UPDATED_THANA_NAME
        defaultThanaShouldBeFound("thanaName.notEquals=" + UPDATED_THANA_NAME);
    }

    @Test
    @Transactional
    public void getAllThanasByThanaNameIsInShouldWork() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where thanaName in DEFAULT_THANA_NAME or UPDATED_THANA_NAME
        defaultThanaShouldBeFound("thanaName.in=" + DEFAULT_THANA_NAME + "," + UPDATED_THANA_NAME);

        // Get all the thanaList where thanaName equals to UPDATED_THANA_NAME
        defaultThanaShouldNotBeFound("thanaName.in=" + UPDATED_THANA_NAME);
    }

    @Test
    @Transactional
    public void getAllThanasByThanaNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where thanaName is not null
        defaultThanaShouldBeFound("thanaName.specified=true");

        // Get all the thanaList where thanaName is null
        defaultThanaShouldNotBeFound("thanaName.specified=false");
    }
                @Test
    @Transactional
    public void getAllThanasByThanaNameContainsSomething() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where thanaName contains DEFAULT_THANA_NAME
        defaultThanaShouldBeFound("thanaName.contains=" + DEFAULT_THANA_NAME);

        // Get all the thanaList where thanaName contains UPDATED_THANA_NAME
        defaultThanaShouldNotBeFound("thanaName.contains=" + UPDATED_THANA_NAME);
    }

    @Test
    @Transactional
    public void getAllThanasByThanaNameNotContainsSomething() throws Exception {
        // Initialize the database
        thanaRepository.saveAndFlush(thana);

        // Get all the thanaList where thanaName does not contain DEFAULT_THANA_NAME
        defaultThanaShouldNotBeFound("thanaName.doesNotContain=" + DEFAULT_THANA_NAME);

        // Get all the thanaList where thanaName does not contain UPDATED_THANA_NAME
        defaultThanaShouldBeFound("thanaName.doesNotContain=" + UPDATED_THANA_NAME);
    }


    @Test
    @Transactional
    public void getAllThanasByDistrictIsEqualToSomething() throws Exception {
        // Get already existing entity
        District district = thana.getDistrict();
        thanaRepository.saveAndFlush(thana);
        Long districtId = district.getId();

        // Get all the thanaList where district equals to districtId
        defaultThanaShouldBeFound("districtId.equals=" + districtId);

        // Get all the thanaList where district equals to districtId + 1
        defaultThanaShouldNotBeFound("districtId.equals=" + (districtId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultThanaShouldBeFound(String filter) throws Exception {
        restThanaMockMvc.perform(get("/api/thanas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thana.getId().intValue())))
            .andExpect(jsonPath("$.[*].thanaName").value(hasItem(DEFAULT_THANA_NAME)));

        // Check, that the count call also returns 1
        restThanaMockMvc.perform(get("/api/thanas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultThanaShouldNotBeFound(String filter) throws Exception {
        restThanaMockMvc.perform(get("/api/thanas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restThanaMockMvc.perform(get("/api/thanas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingThana() throws Exception {
        // Get the thana
        restThanaMockMvc.perform(get("/api/thanas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThana() throws Exception {
        // Initialize the database
        thanaService.save(thana);

        int databaseSizeBeforeUpdate = thanaRepository.findAll().size();

        // Update the thana
        Thana updatedThana = thanaRepository.findById(thana.getId()).get();
        // Disconnect from session so that the updates on updatedThana are not directly saved in db
        em.detach(updatedThana);
        updatedThana
            .thanaName(UPDATED_THANA_NAME);

        restThanaMockMvc.perform(put("/api/thanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedThana)))
            .andExpect(status().isOk());

        // Validate the Thana in the database
        List<Thana> thanaList = thanaRepository.findAll();
        assertThat(thanaList).hasSize(databaseSizeBeforeUpdate);
        Thana testThana = thanaList.get(thanaList.size() - 1);
        assertThat(testThana.getThanaName()).isEqualTo(UPDATED_THANA_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingThana() throws Exception {
        int databaseSizeBeforeUpdate = thanaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThanaMockMvc.perform(put("/api/thanas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(thana)))
            .andExpect(status().isBadRequest());

        // Validate the Thana in the database
        List<Thana> thanaList = thanaRepository.findAll();
        assertThat(thanaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteThana() throws Exception {
        // Initialize the database
        thanaService.save(thana);

        int databaseSizeBeforeDelete = thanaRepository.findAll().size();

        // Delete the thana
        restThanaMockMvc.perform(delete("/api/thanas/{id}", thana.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Thana> thanaList = thanaRepository.findAll();
        assertThat(thanaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
