package aust.edu.web.rest;

import aust.edu.PgadmissionApp;
import aust.edu.domain.Applicant;
import aust.edu.repository.ApplicantRepository;
import aust.edu.service.ApplicantService;

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
