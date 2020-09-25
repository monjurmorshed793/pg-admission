package aust.edu.web.rest;

import aust.edu.PgadmissionApp;
import aust.edu.domain.JobExperience;
import aust.edu.domain.Applicant;
import aust.edu.repository.JobExperienceRepository;
import aust.edu.service.JobExperienceService;
import aust.edu.service.dto.JobExperienceCriteria;
import aust.edu.service.JobExperienceQueryService;

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
    private static final LocalDate SMALLER_FROM_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TO_DATE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_CURRENTLY_WORKING = false;
    private static final Boolean UPDATED_CURRENTLY_WORKING = true;

    @Autowired
    private JobExperienceRepository jobExperienceRepository;

    @Autowired
    private JobExperienceService jobExperienceService;

    @Autowired
    private JobExperienceQueryService jobExperienceQueryService;

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
    public void getJobExperiencesByIdFiltering() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        Long id = jobExperience.getId();

        defaultJobExperienceShouldBeFound("id.equals=" + id);
        defaultJobExperienceShouldNotBeFound("id.notEquals=" + id);

        defaultJobExperienceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultJobExperienceShouldNotBeFound("id.greaterThan=" + id);

        defaultJobExperienceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultJobExperienceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllJobExperiencesByOrganizationNameIsEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where organizationName equals to DEFAULT_ORGANIZATION_NAME
        defaultJobExperienceShouldBeFound("organizationName.equals=" + DEFAULT_ORGANIZATION_NAME);

        // Get all the jobExperienceList where organizationName equals to UPDATED_ORGANIZATION_NAME
        defaultJobExperienceShouldNotBeFound("organizationName.equals=" + UPDATED_ORGANIZATION_NAME);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByOrganizationNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where organizationName not equals to DEFAULT_ORGANIZATION_NAME
        defaultJobExperienceShouldNotBeFound("organizationName.notEquals=" + DEFAULT_ORGANIZATION_NAME);

        // Get all the jobExperienceList where organizationName not equals to UPDATED_ORGANIZATION_NAME
        defaultJobExperienceShouldBeFound("organizationName.notEquals=" + UPDATED_ORGANIZATION_NAME);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByOrganizationNameIsInShouldWork() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where organizationName in DEFAULT_ORGANIZATION_NAME or UPDATED_ORGANIZATION_NAME
        defaultJobExperienceShouldBeFound("organizationName.in=" + DEFAULT_ORGANIZATION_NAME + "," + UPDATED_ORGANIZATION_NAME);

        // Get all the jobExperienceList where organizationName equals to UPDATED_ORGANIZATION_NAME
        defaultJobExperienceShouldNotBeFound("organizationName.in=" + UPDATED_ORGANIZATION_NAME);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByOrganizationNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where organizationName is not null
        defaultJobExperienceShouldBeFound("organizationName.specified=true");

        // Get all the jobExperienceList where organizationName is null
        defaultJobExperienceShouldNotBeFound("organizationName.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobExperiencesByOrganizationNameContainsSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where organizationName contains DEFAULT_ORGANIZATION_NAME
        defaultJobExperienceShouldBeFound("organizationName.contains=" + DEFAULT_ORGANIZATION_NAME);

        // Get all the jobExperienceList where organizationName contains UPDATED_ORGANIZATION_NAME
        defaultJobExperienceShouldNotBeFound("organizationName.contains=" + UPDATED_ORGANIZATION_NAME);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByOrganizationNameNotContainsSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where organizationName does not contain DEFAULT_ORGANIZATION_NAME
        defaultJobExperienceShouldNotBeFound("organizationName.doesNotContain=" + DEFAULT_ORGANIZATION_NAME);

        // Get all the jobExperienceList where organizationName does not contain UPDATED_ORGANIZATION_NAME
        defaultJobExperienceShouldBeFound("organizationName.doesNotContain=" + UPDATED_ORGANIZATION_NAME);
    }


    @Test
    @Transactional
    public void getAllJobExperiencesByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where designation equals to DEFAULT_DESIGNATION
        defaultJobExperienceShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the jobExperienceList where designation equals to UPDATED_DESIGNATION
        defaultJobExperienceShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByDesignationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where designation not equals to DEFAULT_DESIGNATION
        defaultJobExperienceShouldNotBeFound("designation.notEquals=" + DEFAULT_DESIGNATION);

        // Get all the jobExperienceList where designation not equals to UPDATED_DESIGNATION
        defaultJobExperienceShouldBeFound("designation.notEquals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultJobExperienceShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the jobExperienceList where designation equals to UPDATED_DESIGNATION
        defaultJobExperienceShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where designation is not null
        defaultJobExperienceShouldBeFound("designation.specified=true");

        // Get all the jobExperienceList where designation is null
        defaultJobExperienceShouldNotBeFound("designation.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobExperiencesByDesignationContainsSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where designation contains DEFAULT_DESIGNATION
        defaultJobExperienceShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the jobExperienceList where designation contains UPDATED_DESIGNATION
        defaultJobExperienceShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where designation does not contain DEFAULT_DESIGNATION
        defaultJobExperienceShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the jobExperienceList where designation does not contain UPDATED_DESIGNATION
        defaultJobExperienceShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }


    @Test
    @Transactional
    public void getAllJobExperiencesByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where fromDate equals to DEFAULT_FROM_DATE
        defaultJobExperienceShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the jobExperienceList where fromDate equals to UPDATED_FROM_DATE
        defaultJobExperienceShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByFromDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where fromDate not equals to DEFAULT_FROM_DATE
        defaultJobExperienceShouldNotBeFound("fromDate.notEquals=" + DEFAULT_FROM_DATE);

        // Get all the jobExperienceList where fromDate not equals to UPDATED_FROM_DATE
        defaultJobExperienceShouldBeFound("fromDate.notEquals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultJobExperienceShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the jobExperienceList where fromDate equals to UPDATED_FROM_DATE
        defaultJobExperienceShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where fromDate is not null
        defaultJobExperienceShouldBeFound("fromDate.specified=true");

        // Get all the jobExperienceList where fromDate is null
        defaultJobExperienceShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByFromDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where fromDate is greater than or equal to DEFAULT_FROM_DATE
        defaultJobExperienceShouldBeFound("fromDate.greaterThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the jobExperienceList where fromDate is greater than or equal to UPDATED_FROM_DATE
        defaultJobExperienceShouldNotBeFound("fromDate.greaterThanOrEqual=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByFromDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where fromDate is less than or equal to DEFAULT_FROM_DATE
        defaultJobExperienceShouldBeFound("fromDate.lessThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the jobExperienceList where fromDate is less than or equal to SMALLER_FROM_DATE
        defaultJobExperienceShouldNotBeFound("fromDate.lessThanOrEqual=" + SMALLER_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByFromDateIsLessThanSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where fromDate is less than DEFAULT_FROM_DATE
        defaultJobExperienceShouldNotBeFound("fromDate.lessThan=" + DEFAULT_FROM_DATE);

        // Get all the jobExperienceList where fromDate is less than UPDATED_FROM_DATE
        defaultJobExperienceShouldBeFound("fromDate.lessThan=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByFromDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where fromDate is greater than DEFAULT_FROM_DATE
        defaultJobExperienceShouldNotBeFound("fromDate.greaterThan=" + DEFAULT_FROM_DATE);

        // Get all the jobExperienceList where fromDate is greater than SMALLER_FROM_DATE
        defaultJobExperienceShouldBeFound("fromDate.greaterThan=" + SMALLER_FROM_DATE);
    }


    @Test
    @Transactional
    public void getAllJobExperiencesByToDateIsEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where toDate equals to DEFAULT_TO_DATE
        defaultJobExperienceShouldBeFound("toDate.equals=" + DEFAULT_TO_DATE);

        // Get all the jobExperienceList where toDate equals to UPDATED_TO_DATE
        defaultJobExperienceShouldNotBeFound("toDate.equals=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByToDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where toDate not equals to DEFAULT_TO_DATE
        defaultJobExperienceShouldNotBeFound("toDate.notEquals=" + DEFAULT_TO_DATE);

        // Get all the jobExperienceList where toDate not equals to UPDATED_TO_DATE
        defaultJobExperienceShouldBeFound("toDate.notEquals=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByToDateIsInShouldWork() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where toDate in DEFAULT_TO_DATE or UPDATED_TO_DATE
        defaultJobExperienceShouldBeFound("toDate.in=" + DEFAULT_TO_DATE + "," + UPDATED_TO_DATE);

        // Get all the jobExperienceList where toDate equals to UPDATED_TO_DATE
        defaultJobExperienceShouldNotBeFound("toDate.in=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByToDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where toDate is not null
        defaultJobExperienceShouldBeFound("toDate.specified=true");

        // Get all the jobExperienceList where toDate is null
        defaultJobExperienceShouldNotBeFound("toDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByToDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where toDate is greater than or equal to DEFAULT_TO_DATE
        defaultJobExperienceShouldBeFound("toDate.greaterThanOrEqual=" + DEFAULT_TO_DATE);

        // Get all the jobExperienceList where toDate is greater than or equal to UPDATED_TO_DATE
        defaultJobExperienceShouldNotBeFound("toDate.greaterThanOrEqual=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByToDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where toDate is less than or equal to DEFAULT_TO_DATE
        defaultJobExperienceShouldBeFound("toDate.lessThanOrEqual=" + DEFAULT_TO_DATE);

        // Get all the jobExperienceList where toDate is less than or equal to SMALLER_TO_DATE
        defaultJobExperienceShouldNotBeFound("toDate.lessThanOrEqual=" + SMALLER_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByToDateIsLessThanSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where toDate is less than DEFAULT_TO_DATE
        defaultJobExperienceShouldNotBeFound("toDate.lessThan=" + DEFAULT_TO_DATE);

        // Get all the jobExperienceList where toDate is less than UPDATED_TO_DATE
        defaultJobExperienceShouldBeFound("toDate.lessThan=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByToDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where toDate is greater than DEFAULT_TO_DATE
        defaultJobExperienceShouldNotBeFound("toDate.greaterThan=" + DEFAULT_TO_DATE);

        // Get all the jobExperienceList where toDate is greater than SMALLER_TO_DATE
        defaultJobExperienceShouldBeFound("toDate.greaterThan=" + SMALLER_TO_DATE);
    }


    @Test
    @Transactional
    public void getAllJobExperiencesByCurrentlyWorkingIsEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where currentlyWorking equals to DEFAULT_CURRENTLY_WORKING
        defaultJobExperienceShouldBeFound("currentlyWorking.equals=" + DEFAULT_CURRENTLY_WORKING);

        // Get all the jobExperienceList where currentlyWorking equals to UPDATED_CURRENTLY_WORKING
        defaultJobExperienceShouldNotBeFound("currentlyWorking.equals=" + UPDATED_CURRENTLY_WORKING);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByCurrentlyWorkingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where currentlyWorking not equals to DEFAULT_CURRENTLY_WORKING
        defaultJobExperienceShouldNotBeFound("currentlyWorking.notEquals=" + DEFAULT_CURRENTLY_WORKING);

        // Get all the jobExperienceList where currentlyWorking not equals to UPDATED_CURRENTLY_WORKING
        defaultJobExperienceShouldBeFound("currentlyWorking.notEquals=" + UPDATED_CURRENTLY_WORKING);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByCurrentlyWorkingIsInShouldWork() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where currentlyWorking in DEFAULT_CURRENTLY_WORKING or UPDATED_CURRENTLY_WORKING
        defaultJobExperienceShouldBeFound("currentlyWorking.in=" + DEFAULT_CURRENTLY_WORKING + "," + UPDATED_CURRENTLY_WORKING);

        // Get all the jobExperienceList where currentlyWorking equals to UPDATED_CURRENTLY_WORKING
        defaultJobExperienceShouldNotBeFound("currentlyWorking.in=" + UPDATED_CURRENTLY_WORKING);
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByCurrentlyWorkingIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where currentlyWorking is not null
        defaultJobExperienceShouldBeFound("currentlyWorking.specified=true");

        // Get all the jobExperienceList where currentlyWorking is null
        defaultJobExperienceShouldNotBeFound("currentlyWorking.specified=false");
    }

    @Test
    @Transactional
    public void getAllJobExperiencesByApplicantIsEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);
        Applicant applicant = ApplicantResourceIT.createEntity(em);
        em.persist(applicant);
        em.flush();
        jobExperience.setApplicant(applicant);
        jobExperienceRepository.saveAndFlush(jobExperience);
        Long applicantId = applicant.getId();

        // Get all the jobExperienceList where applicant equals to applicantId
        defaultJobExperienceShouldBeFound("applicantId.equals=" + applicantId);

        // Get all the jobExperienceList where applicant equals to applicantId + 1
        defaultJobExperienceShouldNotBeFound("applicantId.equals=" + (applicantId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultJobExperienceShouldBeFound(String filter) throws Exception {
        restJobExperienceMockMvc.perform(get("/api/job-experiences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobExperience.getId().intValue())))
            .andExpect(jsonPath("$.[*].organizationName").value(hasItem(DEFAULT_ORGANIZATION_NAME)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].jobResponsibility").value(hasItem(DEFAULT_JOB_RESPONSIBILITY.toString())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].currentlyWorking").value(hasItem(DEFAULT_CURRENTLY_WORKING.booleanValue())));

        // Check, that the count call also returns 1
        restJobExperienceMockMvc.perform(get("/api/job-experiences/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultJobExperienceShouldNotBeFound(String filter) throws Exception {
        restJobExperienceMockMvc.perform(get("/api/job-experiences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restJobExperienceMockMvc.perform(get("/api/job-experiences/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
