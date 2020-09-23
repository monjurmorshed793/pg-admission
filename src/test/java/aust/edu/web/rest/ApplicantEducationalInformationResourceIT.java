package aust.edu.web.rest;

import aust.edu.PgadmissionApp;
import aust.edu.domain.ApplicantEducationalInformation;
import aust.edu.domain.ExamType;
import aust.edu.repository.ApplicantEducationalInformationRepository;
import aust.edu.service.ApplicantEducationalInformationService;

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
 * Integration tests for the {@link ApplicantEducationalInformationResource} REST controller.
 */
@SpringBootTest(classes = PgadmissionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ApplicantEducationalInformationResourceIT {

    private static final String DEFAULT_INSTITUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INSTITUTE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BOARD = "AAAAAAAAAA";
    private static final String UPDATED_BOARD = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_MARKS_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_MARKS_GRADE = "BBBBBBBBBB";

    private static final String DEFAULT_DIVISION_CLASS_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_DIVISION_CLASS_GRADE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PASSING_YEAR = 1;
    private static final Integer UPDATED_PASSING_YEAR = 2;

    @Autowired
    private ApplicantEducationalInformationRepository applicantEducationalInformationRepository;

    @Autowired
    private ApplicantEducationalInformationService applicantEducationalInformationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicantEducationalInformationMockMvc;

    private ApplicantEducationalInformation applicantEducationalInformation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantEducationalInformation createEntity(EntityManager em) {
        ApplicantEducationalInformation applicantEducationalInformation = new ApplicantEducationalInformation()
            .instituteName(DEFAULT_INSTITUTE_NAME)
            .board(DEFAULT_BOARD)
            .totalMarksGrade(DEFAULT_TOTAL_MARKS_GRADE)
            .divisionClassGrade(DEFAULT_DIVISION_CLASS_GRADE)
            .passingYear(DEFAULT_PASSING_YEAR);
        // Add required entity
        ExamType examType;
        if (TestUtil.findAll(em, ExamType.class).isEmpty()) {
            examType = ExamTypeResourceIT.createEntity(em);
            em.persist(examType);
            em.flush();
        } else {
            examType = TestUtil.findAll(em, ExamType.class).get(0);
        }
        applicantEducationalInformation.setExamType(examType);
        return applicantEducationalInformation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantEducationalInformation createUpdatedEntity(EntityManager em) {
        ApplicantEducationalInformation applicantEducationalInformation = new ApplicantEducationalInformation()
            .instituteName(UPDATED_INSTITUTE_NAME)
            .board(UPDATED_BOARD)
            .totalMarksGrade(UPDATED_TOTAL_MARKS_GRADE)
            .divisionClassGrade(UPDATED_DIVISION_CLASS_GRADE)
            .passingYear(UPDATED_PASSING_YEAR);
        // Add required entity
        ExamType examType;
        if (TestUtil.findAll(em, ExamType.class).isEmpty()) {
            examType = ExamTypeResourceIT.createUpdatedEntity(em);
            em.persist(examType);
            em.flush();
        } else {
            examType = TestUtil.findAll(em, ExamType.class).get(0);
        }
        applicantEducationalInformation.setExamType(examType);
        return applicantEducationalInformation;
    }

    @BeforeEach
    public void initTest() {
        applicantEducationalInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicantEducationalInformation() throws Exception {
        int databaseSizeBeforeCreate = applicantEducationalInformationRepository.findAll().size();
        // Create the ApplicantEducationalInformation
        restApplicantEducationalInformationMockMvc.perform(post("/api/applicant-educational-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantEducationalInformation)))
            .andExpect(status().isCreated());

        // Validate the ApplicantEducationalInformation in the database
        List<ApplicantEducationalInformation> applicantEducationalInformationList = applicantEducationalInformationRepository.findAll();
        assertThat(applicantEducationalInformationList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicantEducationalInformation testApplicantEducationalInformation = applicantEducationalInformationList.get(applicantEducationalInformationList.size() - 1);
        assertThat(testApplicantEducationalInformation.getInstituteName()).isEqualTo(DEFAULT_INSTITUTE_NAME);
        assertThat(testApplicantEducationalInformation.getBoard()).isEqualTo(DEFAULT_BOARD);
        assertThat(testApplicantEducationalInformation.getTotalMarksGrade()).isEqualTo(DEFAULT_TOTAL_MARKS_GRADE);
        assertThat(testApplicantEducationalInformation.getDivisionClassGrade()).isEqualTo(DEFAULT_DIVISION_CLASS_GRADE);
        assertThat(testApplicantEducationalInformation.getPassingYear()).isEqualTo(DEFAULT_PASSING_YEAR);
    }

    @Test
    @Transactional
    public void createApplicantEducationalInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicantEducationalInformationRepository.findAll().size();

        // Create the ApplicantEducationalInformation with an existing ID
        applicantEducationalInformation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicantEducationalInformationMockMvc.perform(post("/api/applicant-educational-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantEducationalInformation)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicantEducationalInformation in the database
        List<ApplicantEducationalInformation> applicantEducationalInformationList = applicantEducationalInformationRepository.findAll();
        assertThat(applicantEducationalInformationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInstituteNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantEducationalInformationRepository.findAll().size();
        // set the field null
        applicantEducationalInformation.setInstituteName(null);

        // Create the ApplicantEducationalInformation, which fails.


        restApplicantEducationalInformationMockMvc.perform(post("/api/applicant-educational-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantEducationalInformation)))
            .andExpect(status().isBadRequest());

        List<ApplicantEducationalInformation> applicantEducationalInformationList = applicantEducationalInformationRepository.findAll();
        assertThat(applicantEducationalInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPassingYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantEducationalInformationRepository.findAll().size();
        // set the field null
        applicantEducationalInformation.setPassingYear(null);

        // Create the ApplicantEducationalInformation, which fails.


        restApplicantEducationalInformationMockMvc.perform(post("/api/applicant-educational-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantEducationalInformation)))
            .andExpect(status().isBadRequest());

        List<ApplicantEducationalInformation> applicantEducationalInformationList = applicantEducationalInformationRepository.findAll();
        assertThat(applicantEducationalInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformations() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList
        restApplicantEducationalInformationMockMvc.perform(get("/api/applicant-educational-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantEducationalInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].instituteName").value(hasItem(DEFAULT_INSTITUTE_NAME)))
            .andExpect(jsonPath("$.[*].board").value(hasItem(DEFAULT_BOARD)))
            .andExpect(jsonPath("$.[*].totalMarksGrade").value(hasItem(DEFAULT_TOTAL_MARKS_GRADE)))
            .andExpect(jsonPath("$.[*].divisionClassGrade").value(hasItem(DEFAULT_DIVISION_CLASS_GRADE)))
            .andExpect(jsonPath("$.[*].passingYear").value(hasItem(DEFAULT_PASSING_YEAR)));
    }
    
    @Test
    @Transactional
    public void getApplicantEducationalInformation() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get the applicantEducationalInformation
        restApplicantEducationalInformationMockMvc.perform(get("/api/applicant-educational-informations/{id}", applicantEducationalInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicantEducationalInformation.getId().intValue()))
            .andExpect(jsonPath("$.instituteName").value(DEFAULT_INSTITUTE_NAME))
            .andExpect(jsonPath("$.board").value(DEFAULT_BOARD))
            .andExpect(jsonPath("$.totalMarksGrade").value(DEFAULT_TOTAL_MARKS_GRADE))
            .andExpect(jsonPath("$.divisionClassGrade").value(DEFAULT_DIVISION_CLASS_GRADE))
            .andExpect(jsonPath("$.passingYear").value(DEFAULT_PASSING_YEAR));
    }
    @Test
    @Transactional
    public void getNonExistingApplicantEducationalInformation() throws Exception {
        // Get the applicantEducationalInformation
        restApplicantEducationalInformationMockMvc.perform(get("/api/applicant-educational-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicantEducationalInformation() throws Exception {
        // Initialize the database
        applicantEducationalInformationService.save(applicantEducationalInformation);

        int databaseSizeBeforeUpdate = applicantEducationalInformationRepository.findAll().size();

        // Update the applicantEducationalInformation
        ApplicantEducationalInformation updatedApplicantEducationalInformation = applicantEducationalInformationRepository.findById(applicantEducationalInformation.getId()).get();
        // Disconnect from session so that the updates on updatedApplicantEducationalInformation are not directly saved in db
        em.detach(updatedApplicantEducationalInformation);
        updatedApplicantEducationalInformation
            .instituteName(UPDATED_INSTITUTE_NAME)
            .board(UPDATED_BOARD)
            .totalMarksGrade(UPDATED_TOTAL_MARKS_GRADE)
            .divisionClassGrade(UPDATED_DIVISION_CLASS_GRADE)
            .passingYear(UPDATED_PASSING_YEAR);

        restApplicantEducationalInformationMockMvc.perform(put("/api/applicant-educational-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplicantEducationalInformation)))
            .andExpect(status().isOk());

        // Validate the ApplicantEducationalInformation in the database
        List<ApplicantEducationalInformation> applicantEducationalInformationList = applicantEducationalInformationRepository.findAll();
        assertThat(applicantEducationalInformationList).hasSize(databaseSizeBeforeUpdate);
        ApplicantEducationalInformation testApplicantEducationalInformation = applicantEducationalInformationList.get(applicantEducationalInformationList.size() - 1);
        assertThat(testApplicantEducationalInformation.getInstituteName()).isEqualTo(UPDATED_INSTITUTE_NAME);
        assertThat(testApplicantEducationalInformation.getBoard()).isEqualTo(UPDATED_BOARD);
        assertThat(testApplicantEducationalInformation.getTotalMarksGrade()).isEqualTo(UPDATED_TOTAL_MARKS_GRADE);
        assertThat(testApplicantEducationalInformation.getDivisionClassGrade()).isEqualTo(UPDATED_DIVISION_CLASS_GRADE);
        assertThat(testApplicantEducationalInformation.getPassingYear()).isEqualTo(UPDATED_PASSING_YEAR);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicantEducationalInformation() throws Exception {
        int databaseSizeBeforeUpdate = applicantEducationalInformationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicantEducationalInformationMockMvc.perform(put("/api/applicant-educational-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantEducationalInformation)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicantEducationalInformation in the database
        List<ApplicantEducationalInformation> applicantEducationalInformationList = applicantEducationalInformationRepository.findAll();
        assertThat(applicantEducationalInformationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicantEducationalInformation() throws Exception {
        // Initialize the database
        applicantEducationalInformationService.save(applicantEducationalInformation);

        int databaseSizeBeforeDelete = applicantEducationalInformationRepository.findAll().size();

        // Delete the applicantEducationalInformation
        restApplicantEducationalInformationMockMvc.perform(delete("/api/applicant-educational-informations/{id}", applicantEducationalInformation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicantEducationalInformation> applicantEducationalInformationList = applicantEducationalInformationRepository.findAll();
        assertThat(applicantEducationalInformationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
