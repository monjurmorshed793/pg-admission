package aust.edu.web.rest;

import aust.edu.PgadmissionApp;
import aust.edu.domain.Thana;
import aust.edu.domain.District;
import aust.edu.repository.ThanaRepository;
import aust.edu.service.ThanaService;

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
