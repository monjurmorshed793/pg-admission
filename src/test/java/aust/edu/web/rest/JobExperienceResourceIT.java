package aust.edu.web.rest;

import aust.edu.PgadmissionApp;
import aust.edu.domain.JobExperience;
import aust.edu.repository.JobExperienceRepository;
import aust.edu.service.JobExperienceService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link JobExperienceResource} REST controller.
 */
@SpringBootTest(classes = PgadmissionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JobExperienceResourceIT {

    private static final String DEFAULT_ORGANIZATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_RESPONSIBILITY = "AAAAAAAAAA";
    private static final String UPDATED_JOB_RESPONSIBILITY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_CURRENTLY_WORKING = false;
    private static final Boolean UPDATED_CURRENTLY_WORKING = true;

    @Autowired
    private JobExperienceRepository jobExperienceRepository;

    @Autowired
    private JobExperienceService jobExperienceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobExperienceMockMvc;

    private JobExperience jobExperience;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobExperience createEntity(EntityManager em) {
        JobExperience jobExperience = new JobExperience()
            .organizationName(DEFAULT_ORGANIZATION_NAME)
            .designation(DEFAULT_DESIGNATION)
            .jobResponsibility(DEFAULT_JOB_RESPONSIBILITY)
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .currentlyWorking(DEFAULT_CURRENTLY_WORKING);
        return jobExperience;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobExperience createUpdatedEntity(EntityManager em) {
        JobExperience jobExperience = new JobExperience()
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .designation(UPDATED_DESIGNATION)
            .jobResponsibility(UPDATED_JOB_RESPONSIBILITY)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .currentlyWorking(UPDATED_CURRENTLY_WORKING);
        return jobExperience;
    }

    @BeforeEach
    public void initTest() {
        jobExperience = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobExperience() throws Exception {
        int databaseSizeBeforeCreate = jobExperienceRepository.findAll().size();
        // Create the JobExperience
        restJobExperienceMockMvc.perform(post("/api/job-experiences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobExperience)))
            .andExpect(status().isCreated());

        // Validate the JobExperience in the database
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeCreate + 1);
        JobExperience testJobExperience = jobExperienceList.get(jobExperienceList.size() - 1);
        assertThat(testJobExperience.getOrganizationName()).isEqualTo(DEFAULT_ORGANIZATION_NAME);
        assertThat(testJobExperience.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testJobExperience.getJobResponsibility()).isEqualTo(DEFAULT_JOB_RESPONSIBILITY);
        assertThat(testJobExperience.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testJobExperience.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testJobExperience.isCurrentlyWorking()).isEqualTo(DEFAULT_CURRENTLY_WORKING);
    }

    @Test
    @Transactional
    public void createJobExperienceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobExperienceRepository.findAll().size();

        // Create the JobExperience with an existing ID
        jobExperience.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobExperienceMockMvc.perform(post("/api/job-experiences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobExperience)))
            .andExpect(status().isBadRequest());

        // Validate the JobExperience in the database
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOrganizationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobExperienceRepository.findAll().size();
        // set the field null
        jobExperience.setOrganizationName(null);

        // Create the JobExperience, which fails.


        restJobExperienceMockMvc.perform(post("/api/job-experiences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobExperience)))
            .andExpect(status().isBadRequest());

        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobExperienceRepository.findAll().size();
        // set the field null
        jobExperience.setDesignation(null);

        // Create the JobExperience, which fails.


        restJobExperienceMockMvc.perform(post("/api/job-experiences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobExperience)))
            .andExpect(status().isBadRequest());

        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFromDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobExperienceRepository.findAll().size();
        // set the field null
        jobExperience.setFromDate(null);

        // Create the JobExperience, which fails.


        restJobExperienceMockMvc.perform(post("/api/job-experiences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobExperience)))
            .andExpect(status().isBadRequest());

        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJobExperiences() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList
        restJobExperienceMockMvc.perform(get("/api/job-experiences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobExperience.getId().intValue())))
            .andExpect(jsonPath("$.[*].organizationName").value(hasItem(DEFAULT_ORGANIZATION_NAME)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].jobResponsibility").value(hasItem(DEFAULT_JOB_RESPONSIBILITY.toString())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].currentlyWorking").value(hasItem(DEFAULT_CURRENTLY_WORKING.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getJobExperience() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get the jobExperience
        restJobExperienceMockMvc.perform(get("/api/job-experiences/{id}", jobExperience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobExperience.getId().intValue()))
            .andExpect(jsonPath("$.organizationName").value(DEFAULT_ORGANIZATION_NAME))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.jobResponsibility").value(DEFAULT_JOB_RESPONSIBILITY.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.currentlyWorking").value(DEFAULT_CURRENTLY_WORKING.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingJobExperience() throws Exception {
        // Get the jobExperience
        restJobExperienceMockMvc.perform(get("/api/job-experiences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobExperience() throws Exception {
        // Initialize the database
        jobExperienceService.save(jobExperience);

        int databaseSizeBeforeUpdate = jobExperienceRepository.findAll().size();

        // Update the jobExperience
        JobExperience updatedJobExperience = jobExperienceRepository.findById(jobExperience.getId()).get();
        // Disconnect from session so that the updates on updatedJobExperience are not directly saved in db
        em.detach(updatedJobExperience);
        updatedJobExperience
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .designation(UPDATED_DESIGNATION)
            .jobResponsibility(UPDATED_JOB_RESPONSIBILITY)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .currentlyWorking(UPDATED_CURRENTLY_WORKING);

        restJobExperienceMockMvc.perform(put("/api/job-experiences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobExperience)))
            .andExpect(status().isOk());

        // Validate the JobExperience in the database
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeUpdate);
        JobExperience testJobExperience = jobExperienceList.get(jobExperienceList.size() - 1);
        assertThat(testJobExperience.getOrganizationName()).isEqualTo(UPDATED_ORGANIZATION_NAME);
        assertThat(testJobExperience.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testJobExperience.getJobResponsibility()).isEqualTo(UPDATED_JOB_RESPONSIBILITY);
        assertThat(testJobExperience.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testJobExperience.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testJobExperience.isCurrentlyWorking()).isEqualTo(UPDATED_CURRENTLY_WORKING);
    }

    @Test
    @Transactional
    public void updateNonExistingJobExperience() throws Exception {
        int databaseSizeBeforeUpdate = jobExperienceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobExperienceMockMvc.perform(put("/api/job-experiences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobExperience)))
            .andExpect(status().isBadRequest());

        // Validate the JobExperience in the database
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobExperience() throws Exception {
        // Initialize the database
        jobExperienceService.save(jobExperience);

        int databaseSizeBeforeDelete = jobExperienceRepository.findAll().size();

        // Delete the jobExperience
        restJobExperienceMockMvc.perform(delete("/api/job-experiences/{id}", jobExperience.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
