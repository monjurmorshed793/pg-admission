package aust.edu.web.rest;

import aust.edu.PgadmissionApp;
import aust.edu.domain.Applicant;
import aust.edu.domain.ApplicantEducationalInformation;
import aust.edu.domain.JobExperience;
import aust.edu.domain.ApplicantAddress;
import aust.edu.domain.Semester;
import aust.edu.domain.Program;
import aust.edu.domain.ApplicantPersonalInfo;
import aust.edu.repository.ApplicantRepository;
import aust.edu.service.ApplicantService;
import aust.edu.service.dto.ApplicantCriteria;
import aust.edu.service.ApplicantQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import aust.edu.domain.enumeration.ApplicationStatus;
/**
 * Integration tests for the {@link ApplicantResource} REST controller.
 */
@SpringBootTest(classes = PgadmissionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ApplicantResourceIT {

    private static final Integer DEFAULT_APPLICATION_SERIAL = 1;
    private static final Integer UPDATED_APPLICATION_SERIAL = 2;
    private static final Integer SMALLER_APPLICATION_SERIAL = 1 - 1;

    private static final ApplicationStatus DEFAULT_STATUS = ApplicationStatus.NOT_APPLIED;
    private static final ApplicationStatus UPDATED_STATUS = ApplicationStatus.APPLIED;

    private static final Instant DEFAULT_APPLIED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPLIED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_APPLICATION_FEE_PAID_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPLICATION_FEE_PAID_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SELECTED_REJECTED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SELECTED_REJECTED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private ApplicantQueryService applicantQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicantMockMvc;

    private Applicant applicant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Applicant createEntity(EntityManager em) {
        Applicant applicant = new Applicant()
            .applicationSerial(DEFAULT_APPLICATION_SERIAL)
            .status(DEFAULT_STATUS)
            .appliedOn(DEFAULT_APPLIED_ON)
            .applicationFeePaidOn(DEFAULT_APPLICATION_FEE_PAID_ON)
            .selectedRejectedOn(DEFAULT_SELECTED_REJECTED_ON);
        // Add required entity
        Semester semester;
        if (TestUtil.findAll(em, Semester.class).isEmpty()) {
            semester = SemesterResourceIT.createEntity(em);
            em.persist(semester);
            em.flush();
        } else {
            semester = TestUtil.findAll(em, Semester.class).get(0);
        }
        applicant.setSemester(semester);
        // Add required entity
        Program program;
        if (TestUtil.findAll(em, Program.class).isEmpty()) {
            program = ProgramResourceIT.createEntity(em);
            em.persist(program);
            em.flush();
        } else {
            program = TestUtil.findAll(em, Program.class).get(0);
        }
        applicant.setProgram(program);
        return applicant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Applicant createUpdatedEntity(EntityManager em) {
        Applicant applicant = new Applicant()
            .applicationSerial(UPDATED_APPLICATION_SERIAL)
            .status(UPDATED_STATUS)
            .appliedOn(UPDATED_APPLIED_ON)
            .applicationFeePaidOn(UPDATED_APPLICATION_FEE_PAID_ON)
            .selectedRejectedOn(UPDATED_SELECTED_REJECTED_ON);
        // Add required entity
        Semester semester;
        if (TestUtil.findAll(em, Semester.class).isEmpty()) {
            semester = SemesterResourceIT.createUpdatedEntity(em);
            em.persist(semester);
            em.flush();
        } else {
            semester = TestUtil.findAll(em, Semester.class).get(0);
        }
        applicant.setSemester(semester);
        // Add required entity
        Program program;
        if (TestUtil.findAll(em, Program.class).isEmpty()) {
            program = ProgramResourceIT.createUpdatedEntity(em);
            em.persist(program);
            em.flush();
        } else {
            program = TestUtil.findAll(em, Program.class).get(0);
        }
        applicant.setProgram(program);
        return applicant;
    }

    @BeforeEach
    public void initTest() {
        applicant = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicant() throws Exception {
        int databaseSizeBeforeCreate = applicantRepository.findAll().size();
        // Create the Applicant
        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicant)))
            .andExpect(status().isCreated());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeCreate + 1);
        Applicant testApplicant = applicantList.get(applicantList.size() - 1);
        assertThat(testApplicant.getApplicationSerial()).isEqualTo(DEFAULT_APPLICATION_SERIAL);
        assertThat(testApplicant.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testApplicant.getAppliedOn()).isEqualTo(DEFAULT_APPLIED_ON);
        assertThat(testApplicant.getApplicationFeePaidOn()).isEqualTo(DEFAULT_APPLICATION_FEE_PAID_ON);
        assertThat(testApplicant.getSelectedRejectedOn()).isEqualTo(DEFAULT_SELECTED_REJECTED_ON);
    }

    @Test
    @Transactional
    public void createApplicantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicantRepository.findAll().size();

        // Create the Applicant with an existing ID
        applicant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicant)))
            .andExpect(status().isBadRequest());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkApplicationSerialIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantRepository.findAll().size();
        // set the field null
        applicant.setApplicationSerial(null);

        // Create the Applicant, which fails.


        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicant)))
            .andExpect(status().isBadRequest());

        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicants() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList
        restApplicantMockMvc.perform(get("/api/applicants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicant.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicationSerial").value(hasItem(DEFAULT_APPLICATION_SERIAL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].appliedOn").value(hasItem(DEFAULT_APPLIED_ON.toString())))
            .andExpect(jsonPath("$.[*].applicationFeePaidOn").value(hasItem(DEFAULT_APPLICATION_FEE_PAID_ON.toString())))
            .andExpect(jsonPath("$.[*].selectedRejectedOn").value(hasItem(DEFAULT_SELECTED_REJECTED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getApplicant() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get the applicant
        restApplicantMockMvc.perform(get("/api/applicants/{id}", applicant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicant.getId().intValue()))
            .andExpect(jsonPath("$.applicationSerial").value(DEFAULT_APPLICATION_SERIAL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.appliedOn").value(DEFAULT_APPLIED_ON.toString()))
            .andExpect(jsonPath("$.applicationFeePaidOn").value(DEFAULT_APPLICATION_FEE_PAID_ON.toString()))
            .andExpect(jsonPath("$.selectedRejectedOn").value(DEFAULT_SELECTED_REJECTED_ON.toString()));
    }


    @Test
    @Transactional
    public void getApplicantsByIdFiltering() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        Long id = applicant.getId();

        defaultApplicantShouldBeFound("id.equals=" + id);
        defaultApplicantShouldNotBeFound("id.notEquals=" + id);

        defaultApplicantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApplicantShouldNotBeFound("id.greaterThan=" + id);

        defaultApplicantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApplicantShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllApplicantsByApplicationSerialIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where applicationSerial equals to DEFAULT_APPLICATION_SERIAL
        defaultApplicantShouldBeFound("applicationSerial.equals=" + DEFAULT_APPLICATION_SERIAL);

        // Get all the applicantList where applicationSerial equals to UPDATED_APPLICATION_SERIAL
        defaultApplicantShouldNotBeFound("applicationSerial.equals=" + UPDATED_APPLICATION_SERIAL);
    }

    @Test
    @Transactional
    public void getAllApplicantsByApplicationSerialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where applicationSerial not equals to DEFAULT_APPLICATION_SERIAL
        defaultApplicantShouldNotBeFound("applicationSerial.notEquals=" + DEFAULT_APPLICATION_SERIAL);

        // Get all the applicantList where applicationSerial not equals to UPDATED_APPLICATION_SERIAL
        defaultApplicantShouldBeFound("applicationSerial.notEquals=" + UPDATED_APPLICATION_SERIAL);
    }

    @Test
    @Transactional
    public void getAllApplicantsByApplicationSerialIsInShouldWork() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where applicationSerial in DEFAULT_APPLICATION_SERIAL or UPDATED_APPLICATION_SERIAL
        defaultApplicantShouldBeFound("applicationSerial.in=" + DEFAULT_APPLICATION_SERIAL + "," + UPDATED_APPLICATION_SERIAL);

        // Get all the applicantList where applicationSerial equals to UPDATED_APPLICATION_SERIAL
        defaultApplicantShouldNotBeFound("applicationSerial.in=" + UPDATED_APPLICATION_SERIAL);
    }

    @Test
    @Transactional
    public void getAllApplicantsByApplicationSerialIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where applicationSerial is not null
        defaultApplicantShouldBeFound("applicationSerial.specified=true");

        // Get all the applicantList where applicationSerial is null
        defaultApplicantShouldNotBeFound("applicationSerial.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicantsByApplicationSerialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where applicationSerial is greater than or equal to DEFAULT_APPLICATION_SERIAL
        defaultApplicantShouldBeFound("applicationSerial.greaterThanOrEqual=" + DEFAULT_APPLICATION_SERIAL);

        // Get all the applicantList where applicationSerial is greater than or equal to UPDATED_APPLICATION_SERIAL
        defaultApplicantShouldNotBeFound("applicationSerial.greaterThanOrEqual=" + UPDATED_APPLICATION_SERIAL);
    }

    @Test
    @Transactional
    public void getAllApplicantsByApplicationSerialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where applicationSerial is less than or equal to DEFAULT_APPLICATION_SERIAL
        defaultApplicantShouldBeFound("applicationSerial.lessThanOrEqual=" + DEFAULT_APPLICATION_SERIAL);

        // Get all the applicantList where applicationSerial is less than or equal to SMALLER_APPLICATION_SERIAL
        defaultApplicantShouldNotBeFound("applicationSerial.lessThanOrEqual=" + SMALLER_APPLICATION_SERIAL);
    }

    @Test
    @Transactional
    public void getAllApplicantsByApplicationSerialIsLessThanSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where applicationSerial is less than DEFAULT_APPLICATION_SERIAL
        defaultApplicantShouldNotBeFound("applicationSerial.lessThan=" + DEFAULT_APPLICATION_SERIAL);

        // Get all the applicantList where applicationSerial is less than UPDATED_APPLICATION_SERIAL
        defaultApplicantShouldBeFound("applicationSerial.lessThan=" + UPDATED_APPLICATION_SERIAL);
    }

    @Test
    @Transactional
    public void getAllApplicantsByApplicationSerialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where applicationSerial is greater than DEFAULT_APPLICATION_SERIAL
        defaultApplicantShouldNotBeFound("applicationSerial.greaterThan=" + DEFAULT_APPLICATION_SERIAL);

        // Get all the applicantList where applicationSerial is greater than SMALLER_APPLICATION_SERIAL
        defaultApplicantShouldBeFound("applicationSerial.greaterThan=" + SMALLER_APPLICATION_SERIAL);
    }


    @Test
    @Transactional
    public void getAllApplicantsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where status equals to DEFAULT_STATUS
        defaultApplicantShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the applicantList where status equals to UPDATED_STATUS
        defaultApplicantShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllApplicantsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where status not equals to DEFAULT_STATUS
        defaultApplicantShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the applicantList where status not equals to UPDATED_STATUS
        defaultApplicantShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllApplicantsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultApplicantShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the applicantList where status equals to UPDATED_STATUS
        defaultApplicantShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllApplicantsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where status is not null
        defaultApplicantShouldBeFound("status.specified=true");

        // Get all the applicantList where status is null
        defaultApplicantShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicantsByAppliedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where appliedOn equals to DEFAULT_APPLIED_ON
        defaultApplicantShouldBeFound("appliedOn.equals=" + DEFAULT_APPLIED_ON);

        // Get all the applicantList where appliedOn equals to UPDATED_APPLIED_ON
        defaultApplicantShouldNotBeFound("appliedOn.equals=" + UPDATED_APPLIED_ON);
    }

    @Test
    @Transactional
    public void getAllApplicantsByAppliedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where appliedOn not equals to DEFAULT_APPLIED_ON
        defaultApplicantShouldNotBeFound("appliedOn.notEquals=" + DEFAULT_APPLIED_ON);

        // Get all the applicantList where appliedOn not equals to UPDATED_APPLIED_ON
        defaultApplicantShouldBeFound("appliedOn.notEquals=" + UPDATED_APPLIED_ON);
    }

    @Test
    @Transactional
    public void getAllApplicantsByAppliedOnIsInShouldWork() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where appliedOn in DEFAULT_APPLIED_ON or UPDATED_APPLIED_ON
        defaultApplicantShouldBeFound("appliedOn.in=" + DEFAULT_APPLIED_ON + "," + UPDATED_APPLIED_ON);

        // Get all the applicantList where appliedOn equals to UPDATED_APPLIED_ON
        defaultApplicantShouldNotBeFound("appliedOn.in=" + UPDATED_APPLIED_ON);
    }

    @Test
    @Transactional
    public void getAllApplicantsByAppliedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where appliedOn is not null
        defaultApplicantShouldBeFound("appliedOn.specified=true");

        // Get all the applicantList where appliedOn is null
        defaultApplicantShouldNotBeFound("appliedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicantsByApplicationFeePaidOnIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where applicationFeePaidOn equals to DEFAULT_APPLICATION_FEE_PAID_ON
        defaultApplicantShouldBeFound("applicationFeePaidOn.equals=" + DEFAULT_APPLICATION_FEE_PAID_ON);

        // Get all the applicantList where applicationFeePaidOn equals to UPDATED_APPLICATION_FEE_PAID_ON
        defaultApplicantShouldNotBeFound("applicationFeePaidOn.equals=" + UPDATED_APPLICATION_FEE_PAID_ON);
    }

    @Test
    @Transactional
    public void getAllApplicantsByApplicationFeePaidOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where applicationFeePaidOn not equals to DEFAULT_APPLICATION_FEE_PAID_ON
        defaultApplicantShouldNotBeFound("applicationFeePaidOn.notEquals=" + DEFAULT_APPLICATION_FEE_PAID_ON);

        // Get all the applicantList where applicationFeePaidOn not equals to UPDATED_APPLICATION_FEE_PAID_ON
        defaultApplicantShouldBeFound("applicationFeePaidOn.notEquals=" + UPDATED_APPLICATION_FEE_PAID_ON);
    }

    @Test
    @Transactional
    public void getAllApplicantsByApplicationFeePaidOnIsInShouldWork() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where applicationFeePaidOn in DEFAULT_APPLICATION_FEE_PAID_ON or UPDATED_APPLICATION_FEE_PAID_ON
        defaultApplicantShouldBeFound("applicationFeePaidOn.in=" + DEFAULT_APPLICATION_FEE_PAID_ON + "," + UPDATED_APPLICATION_FEE_PAID_ON);

        // Get all the applicantList where applicationFeePaidOn equals to UPDATED_APPLICATION_FEE_PAID_ON
        defaultApplicantShouldNotBeFound("applicationFeePaidOn.in=" + UPDATED_APPLICATION_FEE_PAID_ON);
    }

    @Test
    @Transactional
    public void getAllApplicantsByApplicationFeePaidOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where applicationFeePaidOn is not null
        defaultApplicantShouldBeFound("applicationFeePaidOn.specified=true");

        // Get all the applicantList where applicationFeePaidOn is null
        defaultApplicantShouldNotBeFound("applicationFeePaidOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicantsBySelectedRejectedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where selectedRejectedOn equals to DEFAULT_SELECTED_REJECTED_ON
        defaultApplicantShouldBeFound("selectedRejectedOn.equals=" + DEFAULT_SELECTED_REJECTED_ON);

        // Get all the applicantList where selectedRejectedOn equals to UPDATED_SELECTED_REJECTED_ON
        defaultApplicantShouldNotBeFound("selectedRejectedOn.equals=" + UPDATED_SELECTED_REJECTED_ON);
    }

    @Test
    @Transactional
    public void getAllApplicantsBySelectedRejectedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where selectedRejectedOn not equals to DEFAULT_SELECTED_REJECTED_ON
        defaultApplicantShouldNotBeFound("selectedRejectedOn.notEquals=" + DEFAULT_SELECTED_REJECTED_ON);

        // Get all the applicantList where selectedRejectedOn not equals to UPDATED_SELECTED_REJECTED_ON
        defaultApplicantShouldBeFound("selectedRejectedOn.notEquals=" + UPDATED_SELECTED_REJECTED_ON);
    }

    @Test
    @Transactional
    public void getAllApplicantsBySelectedRejectedOnIsInShouldWork() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where selectedRejectedOn in DEFAULT_SELECTED_REJECTED_ON or UPDATED_SELECTED_REJECTED_ON
        defaultApplicantShouldBeFound("selectedRejectedOn.in=" + DEFAULT_SELECTED_REJECTED_ON + "," + UPDATED_SELECTED_REJECTED_ON);

        // Get all the applicantList where selectedRejectedOn equals to UPDATED_SELECTED_REJECTED_ON
        defaultApplicantShouldNotBeFound("selectedRejectedOn.in=" + UPDATED_SELECTED_REJECTED_ON);
    }

    @Test
    @Transactional
    public void getAllApplicantsBySelectedRejectedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList where selectedRejectedOn is not null
        defaultApplicantShouldBeFound("selectedRejectedOn.specified=true");

        // Get all the applicantList where selectedRejectedOn is null
        defaultApplicantShouldNotBeFound("selectedRejectedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicantsByApplicantEducationalInformationIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);
        ApplicantEducationalInformation applicantEducationalInformation = ApplicantEducationalInformationResourceIT.createEntity(em);
        em.persist(applicantEducationalInformation);
        em.flush();
        applicant.addApplicantEducationalInformation(applicantEducationalInformation);
        applicantRepository.saveAndFlush(applicant);
        Long applicantEducationalInformationId = applicantEducationalInformation.getId();

        // Get all the applicantList where applicantEducationalInformation equals to applicantEducationalInformationId
        defaultApplicantShouldBeFound("applicantEducationalInformationId.equals=" + applicantEducationalInformationId);

        // Get all the applicantList where applicantEducationalInformation equals to applicantEducationalInformationId + 1
        defaultApplicantShouldNotBeFound("applicantEducationalInformationId.equals=" + (applicantEducationalInformationId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicantsByJobExperienceIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);
        JobExperience jobExperience = JobExperienceResourceIT.createEntity(em);
        em.persist(jobExperience);
        em.flush();
        applicant.addJobExperience(jobExperience);
        applicantRepository.saveAndFlush(applicant);
        Long jobExperienceId = jobExperience.getId();

        // Get all the applicantList where jobExperience equals to jobExperienceId
        defaultApplicantShouldBeFound("jobExperienceId.equals=" + jobExperienceId);

        // Get all the applicantList where jobExperience equals to jobExperienceId + 1
        defaultApplicantShouldNotBeFound("jobExperienceId.equals=" + (jobExperienceId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicantsByApplicantAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);
        ApplicantAddress applicantAddress = ApplicantAddressResourceIT.createEntity(em);
        em.persist(applicantAddress);
        em.flush();
        applicant.addApplicantAddress(applicantAddress);
        applicantRepository.saveAndFlush(applicant);
        Long applicantAddressId = applicantAddress.getId();

        // Get all the applicantList where applicantAddress equals to applicantAddressId
        defaultApplicantShouldBeFound("applicantAddressId.equals=" + applicantAddressId);

        // Get all the applicantList where applicantAddress equals to applicantAddressId + 1
        defaultApplicantShouldNotBeFound("applicantAddressId.equals=" + (applicantAddressId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicantsBySemesterIsEqualToSomething() throws Exception {
        // Get already existing entity
        Semester semester = applicant.getSemester();
        applicantRepository.saveAndFlush(applicant);
        Long semesterId = semester.getId();

        // Get all the applicantList where semester equals to semesterId
        defaultApplicantShouldBeFound("semesterId.equals=" + semesterId);

        // Get all the applicantList where semester equals to semesterId + 1
        defaultApplicantShouldNotBeFound("semesterId.equals=" + (semesterId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicantsByProgramIsEqualToSomething() throws Exception {
        // Get already existing entity
        Program program = applicant.getProgram();
        applicantRepository.saveAndFlush(applicant);
        Long programId = program.getId();

        // Get all the applicantList where program equals to programId
        defaultApplicantShouldBeFound("programId.equals=" + programId);

        // Get all the applicantList where program equals to programId + 1
        defaultApplicantShouldNotBeFound("programId.equals=" + (programId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicantsByApplicantPersonalInformationIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);
        ApplicantPersonalInfo applicantPersonalInformation = ApplicantPersonalInfoResourceIT.createEntity(em);
        em.persist(applicantPersonalInformation);
        em.flush();
        applicant.setApplicantPersonalInformation(applicantPersonalInformation);
        applicantPersonalInformation.setApplicant(applicant);
        applicantRepository.saveAndFlush(applicant);
        Long applicantPersonalInformationId = applicantPersonalInformation.getId();

        // Get all the applicantList where applicantPersonalInformation equals to applicantPersonalInformationId
        defaultApplicantShouldBeFound("applicantPersonalInformationId.equals=" + applicantPersonalInformationId);

        // Get all the applicantList where applicantPersonalInformation equals to applicantPersonalInformationId + 1
        defaultApplicantShouldNotBeFound("applicantPersonalInformationId.equals=" + (applicantPersonalInformationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicantShouldBeFound(String filter) throws Exception {
        restApplicantMockMvc.perform(get("/api/applicants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicant.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicationSerial").value(hasItem(DEFAULT_APPLICATION_SERIAL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].appliedOn").value(hasItem(DEFAULT_APPLIED_ON.toString())))
            .andExpect(jsonPath("$.[*].applicationFeePaidOn").value(hasItem(DEFAULT_APPLICATION_FEE_PAID_ON.toString())))
            .andExpect(jsonPath("$.[*].selectedRejectedOn").value(hasItem(DEFAULT_SELECTED_REJECTED_ON.toString())));

        // Check, that the count call also returns 1
        restApplicantMockMvc.perform(get("/api/applicants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicantShouldNotBeFound(String filter) throws Exception {
        restApplicantMockMvc.perform(get("/api/applicants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicantMockMvc.perform(get("/api/applicants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingApplicant() throws Exception {
        // Get the applicant
        restApplicantMockMvc.perform(get("/api/applicants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicant() throws Exception {
        // Initialize the database
        applicantService.save(applicant);

        int databaseSizeBeforeUpdate = applicantRepository.findAll().size();

        // Update the applicant
        Applicant updatedApplicant = applicantRepository.findById(applicant.getId()).get();
        // Disconnect from session so that the updates on updatedApplicant are not directly saved in db
        em.detach(updatedApplicant);
        updatedApplicant
            .applicationSerial(UPDATED_APPLICATION_SERIAL)
            .status(UPDATED_STATUS)
            .appliedOn(UPDATED_APPLIED_ON)
            .applicationFeePaidOn(UPDATED_APPLICATION_FEE_PAID_ON)
            .selectedRejectedOn(UPDATED_SELECTED_REJECTED_ON);

        restApplicantMockMvc.perform(put("/api/applicants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplicant)))
            .andExpect(status().isOk());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeUpdate);
        Applicant testApplicant = applicantList.get(applicantList.size() - 1);
        assertThat(testApplicant.getApplicationSerial()).isEqualTo(UPDATED_APPLICATION_SERIAL);
        assertThat(testApplicant.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApplicant.getAppliedOn()).isEqualTo(UPDATED_APPLIED_ON);
        assertThat(testApplicant.getApplicationFeePaidOn()).isEqualTo(UPDATED_APPLICATION_FEE_PAID_ON);
        assertThat(testApplicant.getSelectedRejectedOn()).isEqualTo(UPDATED_SELECTED_REJECTED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicant() throws Exception {
        int databaseSizeBeforeUpdate = applicantRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicantMockMvc.perform(put("/api/applicants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicant)))
            .andExpect(status().isBadRequest());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicant() throws Exception {
        // Initialize the database
        applicantService.save(applicant);

        int databaseSizeBeforeDelete = applicantRepository.findAll().size();

        // Delete the applicant
        restApplicantMockMvc.perform(delete("/api/applicants/{id}", applicant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
