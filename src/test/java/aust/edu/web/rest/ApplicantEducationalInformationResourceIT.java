package aust.edu.web.rest;

import aust.edu.PgadmissionApp;
import aust.edu.domain.ApplicantEducationalInformation;
import aust.edu.domain.ExamType;
import aust.edu.domain.Applicant;
import aust.edu.repository.ApplicantEducationalInformationRepository;
import aust.edu.service.ApplicantEducationalInformationService;
import aust.edu.service.dto.ApplicantEducationalInformationCriteria;
import aust.edu.service.ApplicantEducationalInformationQueryService;

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
    private static final Integer SMALLER_PASSING_YEAR = 1 - 1;

    @Autowired
    private ApplicantEducationalInformationRepository applicantEducationalInformationRepository;

    @Autowired
    private ApplicantEducationalInformationService applicantEducationalInformationService;

    @Autowired
    private ApplicantEducationalInformationQueryService applicantEducationalInformationQueryService;

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
    public void getApplicantEducationalInformationsByIdFiltering() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        Long id = applicantEducationalInformation.getId();

        defaultApplicantEducationalInformationShouldBeFound("id.equals=" + id);
        defaultApplicantEducationalInformationShouldNotBeFound("id.notEquals=" + id);

        defaultApplicantEducationalInformationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApplicantEducationalInformationShouldNotBeFound("id.greaterThan=" + id);

        defaultApplicantEducationalInformationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApplicantEducationalInformationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByInstituteNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where instituteName equals to DEFAULT_INSTITUTE_NAME
        defaultApplicantEducationalInformationShouldBeFound("instituteName.equals=" + DEFAULT_INSTITUTE_NAME);

        // Get all the applicantEducationalInformationList where instituteName equals to UPDATED_INSTITUTE_NAME
        defaultApplicantEducationalInformationShouldNotBeFound("instituteName.equals=" + UPDATED_INSTITUTE_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByInstituteNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where instituteName not equals to DEFAULT_INSTITUTE_NAME
        defaultApplicantEducationalInformationShouldNotBeFound("instituteName.notEquals=" + DEFAULT_INSTITUTE_NAME);

        // Get all the applicantEducationalInformationList where instituteName not equals to UPDATED_INSTITUTE_NAME
        defaultApplicantEducationalInformationShouldBeFound("instituteName.notEquals=" + UPDATED_INSTITUTE_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByInstituteNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where instituteName in DEFAULT_INSTITUTE_NAME or UPDATED_INSTITUTE_NAME
        defaultApplicantEducationalInformationShouldBeFound("instituteName.in=" + DEFAULT_INSTITUTE_NAME + "," + UPDATED_INSTITUTE_NAME);

        // Get all the applicantEducationalInformationList where instituteName equals to UPDATED_INSTITUTE_NAME
        defaultApplicantEducationalInformationShouldNotBeFound("instituteName.in=" + UPDATED_INSTITUTE_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByInstituteNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where instituteName is not null
        defaultApplicantEducationalInformationShouldBeFound("instituteName.specified=true");

        // Get all the applicantEducationalInformationList where instituteName is null
        defaultApplicantEducationalInformationShouldNotBeFound("instituteName.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByInstituteNameContainsSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where instituteName contains DEFAULT_INSTITUTE_NAME
        defaultApplicantEducationalInformationShouldBeFound("instituteName.contains=" + DEFAULT_INSTITUTE_NAME);

        // Get all the applicantEducationalInformationList where instituteName contains UPDATED_INSTITUTE_NAME
        defaultApplicantEducationalInformationShouldNotBeFound("instituteName.contains=" + UPDATED_INSTITUTE_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByInstituteNameNotContainsSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where instituteName does not contain DEFAULT_INSTITUTE_NAME
        defaultApplicantEducationalInformationShouldNotBeFound("instituteName.doesNotContain=" + DEFAULT_INSTITUTE_NAME);

        // Get all the applicantEducationalInformationList where instituteName does not contain UPDATED_INSTITUTE_NAME
        defaultApplicantEducationalInformationShouldBeFound("instituteName.doesNotContain=" + UPDATED_INSTITUTE_NAME);
    }


    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByBoardIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where board equals to DEFAULT_BOARD
        defaultApplicantEducationalInformationShouldBeFound("board.equals=" + DEFAULT_BOARD);

        // Get all the applicantEducationalInformationList where board equals to UPDATED_BOARD
        defaultApplicantEducationalInformationShouldNotBeFound("board.equals=" + UPDATED_BOARD);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByBoardIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where board not equals to DEFAULT_BOARD
        defaultApplicantEducationalInformationShouldNotBeFound("board.notEquals=" + DEFAULT_BOARD);

        // Get all the applicantEducationalInformationList where board not equals to UPDATED_BOARD
        defaultApplicantEducationalInformationShouldBeFound("board.notEquals=" + UPDATED_BOARD);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByBoardIsInShouldWork() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where board in DEFAULT_BOARD or UPDATED_BOARD
        defaultApplicantEducationalInformationShouldBeFound("board.in=" + DEFAULT_BOARD + "," + UPDATED_BOARD);

        // Get all the applicantEducationalInformationList where board equals to UPDATED_BOARD
        defaultApplicantEducationalInformationShouldNotBeFound("board.in=" + UPDATED_BOARD);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByBoardIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where board is not null
        defaultApplicantEducationalInformationShouldBeFound("board.specified=true");

        // Get all the applicantEducationalInformationList where board is null
        defaultApplicantEducationalInformationShouldNotBeFound("board.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByBoardContainsSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where board contains DEFAULT_BOARD
        defaultApplicantEducationalInformationShouldBeFound("board.contains=" + DEFAULT_BOARD);

        // Get all the applicantEducationalInformationList where board contains UPDATED_BOARD
        defaultApplicantEducationalInformationShouldNotBeFound("board.contains=" + UPDATED_BOARD);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByBoardNotContainsSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where board does not contain DEFAULT_BOARD
        defaultApplicantEducationalInformationShouldNotBeFound("board.doesNotContain=" + DEFAULT_BOARD);

        // Get all the applicantEducationalInformationList where board does not contain UPDATED_BOARD
        defaultApplicantEducationalInformationShouldBeFound("board.doesNotContain=" + UPDATED_BOARD);
    }


    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByTotalMarksGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where totalMarksGrade equals to DEFAULT_TOTAL_MARKS_GRADE
        defaultApplicantEducationalInformationShouldBeFound("totalMarksGrade.equals=" + DEFAULT_TOTAL_MARKS_GRADE);

        // Get all the applicantEducationalInformationList where totalMarksGrade equals to UPDATED_TOTAL_MARKS_GRADE
        defaultApplicantEducationalInformationShouldNotBeFound("totalMarksGrade.equals=" + UPDATED_TOTAL_MARKS_GRADE);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByTotalMarksGradeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where totalMarksGrade not equals to DEFAULT_TOTAL_MARKS_GRADE
        defaultApplicantEducationalInformationShouldNotBeFound("totalMarksGrade.notEquals=" + DEFAULT_TOTAL_MARKS_GRADE);

        // Get all the applicantEducationalInformationList where totalMarksGrade not equals to UPDATED_TOTAL_MARKS_GRADE
        defaultApplicantEducationalInformationShouldBeFound("totalMarksGrade.notEquals=" + UPDATED_TOTAL_MARKS_GRADE);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByTotalMarksGradeIsInShouldWork() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where totalMarksGrade in DEFAULT_TOTAL_MARKS_GRADE or UPDATED_TOTAL_MARKS_GRADE
        defaultApplicantEducationalInformationShouldBeFound("totalMarksGrade.in=" + DEFAULT_TOTAL_MARKS_GRADE + "," + UPDATED_TOTAL_MARKS_GRADE);

        // Get all the applicantEducationalInformationList where totalMarksGrade equals to UPDATED_TOTAL_MARKS_GRADE
        defaultApplicantEducationalInformationShouldNotBeFound("totalMarksGrade.in=" + UPDATED_TOTAL_MARKS_GRADE);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByTotalMarksGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where totalMarksGrade is not null
        defaultApplicantEducationalInformationShouldBeFound("totalMarksGrade.specified=true");

        // Get all the applicantEducationalInformationList where totalMarksGrade is null
        defaultApplicantEducationalInformationShouldNotBeFound("totalMarksGrade.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByTotalMarksGradeContainsSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where totalMarksGrade contains DEFAULT_TOTAL_MARKS_GRADE
        defaultApplicantEducationalInformationShouldBeFound("totalMarksGrade.contains=" + DEFAULT_TOTAL_MARKS_GRADE);

        // Get all the applicantEducationalInformationList where totalMarksGrade contains UPDATED_TOTAL_MARKS_GRADE
        defaultApplicantEducationalInformationShouldNotBeFound("totalMarksGrade.contains=" + UPDATED_TOTAL_MARKS_GRADE);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByTotalMarksGradeNotContainsSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where totalMarksGrade does not contain DEFAULT_TOTAL_MARKS_GRADE
        defaultApplicantEducationalInformationShouldNotBeFound("totalMarksGrade.doesNotContain=" + DEFAULT_TOTAL_MARKS_GRADE);

        // Get all the applicantEducationalInformationList where totalMarksGrade does not contain UPDATED_TOTAL_MARKS_GRADE
        defaultApplicantEducationalInformationShouldBeFound("totalMarksGrade.doesNotContain=" + UPDATED_TOTAL_MARKS_GRADE);
    }


    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByDivisionClassGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where divisionClassGrade equals to DEFAULT_DIVISION_CLASS_GRADE
        defaultApplicantEducationalInformationShouldBeFound("divisionClassGrade.equals=" + DEFAULT_DIVISION_CLASS_GRADE);

        // Get all the applicantEducationalInformationList where divisionClassGrade equals to UPDATED_DIVISION_CLASS_GRADE
        defaultApplicantEducationalInformationShouldNotBeFound("divisionClassGrade.equals=" + UPDATED_DIVISION_CLASS_GRADE);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByDivisionClassGradeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where divisionClassGrade not equals to DEFAULT_DIVISION_CLASS_GRADE
        defaultApplicantEducationalInformationShouldNotBeFound("divisionClassGrade.notEquals=" + DEFAULT_DIVISION_CLASS_GRADE);

        // Get all the applicantEducationalInformationList where divisionClassGrade not equals to UPDATED_DIVISION_CLASS_GRADE
        defaultApplicantEducationalInformationShouldBeFound("divisionClassGrade.notEquals=" + UPDATED_DIVISION_CLASS_GRADE);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByDivisionClassGradeIsInShouldWork() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where divisionClassGrade in DEFAULT_DIVISION_CLASS_GRADE or UPDATED_DIVISION_CLASS_GRADE
        defaultApplicantEducationalInformationShouldBeFound("divisionClassGrade.in=" + DEFAULT_DIVISION_CLASS_GRADE + "," + UPDATED_DIVISION_CLASS_GRADE);

        // Get all the applicantEducationalInformationList where divisionClassGrade equals to UPDATED_DIVISION_CLASS_GRADE
        defaultApplicantEducationalInformationShouldNotBeFound("divisionClassGrade.in=" + UPDATED_DIVISION_CLASS_GRADE);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByDivisionClassGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where divisionClassGrade is not null
        defaultApplicantEducationalInformationShouldBeFound("divisionClassGrade.specified=true");

        // Get all the applicantEducationalInformationList where divisionClassGrade is null
        defaultApplicantEducationalInformationShouldNotBeFound("divisionClassGrade.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByDivisionClassGradeContainsSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where divisionClassGrade contains DEFAULT_DIVISION_CLASS_GRADE
        defaultApplicantEducationalInformationShouldBeFound("divisionClassGrade.contains=" + DEFAULT_DIVISION_CLASS_GRADE);

        // Get all the applicantEducationalInformationList where divisionClassGrade contains UPDATED_DIVISION_CLASS_GRADE
        defaultApplicantEducationalInformationShouldNotBeFound("divisionClassGrade.contains=" + UPDATED_DIVISION_CLASS_GRADE);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByDivisionClassGradeNotContainsSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where divisionClassGrade does not contain DEFAULT_DIVISION_CLASS_GRADE
        defaultApplicantEducationalInformationShouldNotBeFound("divisionClassGrade.doesNotContain=" + DEFAULT_DIVISION_CLASS_GRADE);

        // Get all the applicantEducationalInformationList where divisionClassGrade does not contain UPDATED_DIVISION_CLASS_GRADE
        defaultApplicantEducationalInformationShouldBeFound("divisionClassGrade.doesNotContain=" + UPDATED_DIVISION_CLASS_GRADE);
    }


    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByPassingYearIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where passingYear equals to DEFAULT_PASSING_YEAR
        defaultApplicantEducationalInformationShouldBeFound("passingYear.equals=" + DEFAULT_PASSING_YEAR);

        // Get all the applicantEducationalInformationList where passingYear equals to UPDATED_PASSING_YEAR
        defaultApplicantEducationalInformationShouldNotBeFound("passingYear.equals=" + UPDATED_PASSING_YEAR);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByPassingYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where passingYear not equals to DEFAULT_PASSING_YEAR
        defaultApplicantEducationalInformationShouldNotBeFound("passingYear.notEquals=" + DEFAULT_PASSING_YEAR);

        // Get all the applicantEducationalInformationList where passingYear not equals to UPDATED_PASSING_YEAR
        defaultApplicantEducationalInformationShouldBeFound("passingYear.notEquals=" + UPDATED_PASSING_YEAR);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByPassingYearIsInShouldWork() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where passingYear in DEFAULT_PASSING_YEAR or UPDATED_PASSING_YEAR
        defaultApplicantEducationalInformationShouldBeFound("passingYear.in=" + DEFAULT_PASSING_YEAR + "," + UPDATED_PASSING_YEAR);

        // Get all the applicantEducationalInformationList where passingYear equals to UPDATED_PASSING_YEAR
        defaultApplicantEducationalInformationShouldNotBeFound("passingYear.in=" + UPDATED_PASSING_YEAR);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByPassingYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where passingYear is not null
        defaultApplicantEducationalInformationShouldBeFound("passingYear.specified=true");

        // Get all the applicantEducationalInformationList where passingYear is null
        defaultApplicantEducationalInformationShouldNotBeFound("passingYear.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByPassingYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where passingYear is greater than or equal to DEFAULT_PASSING_YEAR
        defaultApplicantEducationalInformationShouldBeFound("passingYear.greaterThanOrEqual=" + DEFAULT_PASSING_YEAR);

        // Get all the applicantEducationalInformationList where passingYear is greater than or equal to UPDATED_PASSING_YEAR
        defaultApplicantEducationalInformationShouldNotBeFound("passingYear.greaterThanOrEqual=" + UPDATED_PASSING_YEAR);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByPassingYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where passingYear is less than or equal to DEFAULT_PASSING_YEAR
        defaultApplicantEducationalInformationShouldBeFound("passingYear.lessThanOrEqual=" + DEFAULT_PASSING_YEAR);

        // Get all the applicantEducationalInformationList where passingYear is less than or equal to SMALLER_PASSING_YEAR
        defaultApplicantEducationalInformationShouldNotBeFound("passingYear.lessThanOrEqual=" + SMALLER_PASSING_YEAR);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByPassingYearIsLessThanSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where passingYear is less than DEFAULT_PASSING_YEAR
        defaultApplicantEducationalInformationShouldNotBeFound("passingYear.lessThan=" + DEFAULT_PASSING_YEAR);

        // Get all the applicantEducationalInformationList where passingYear is less than UPDATED_PASSING_YEAR
        defaultApplicantEducationalInformationShouldBeFound("passingYear.lessThan=" + UPDATED_PASSING_YEAR);
    }

    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByPassingYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);

        // Get all the applicantEducationalInformationList where passingYear is greater than DEFAULT_PASSING_YEAR
        defaultApplicantEducationalInformationShouldNotBeFound("passingYear.greaterThan=" + DEFAULT_PASSING_YEAR);

        // Get all the applicantEducationalInformationList where passingYear is greater than SMALLER_PASSING_YEAR
        defaultApplicantEducationalInformationShouldBeFound("passingYear.greaterThan=" + SMALLER_PASSING_YEAR);
    }


    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByExamTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        ExamType examType = applicantEducationalInformation.getExamType();
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);
        Long examTypeId = examType.getId();

        // Get all the applicantEducationalInformationList where examType equals to examTypeId
        defaultApplicantEducationalInformationShouldBeFound("examTypeId.equals=" + examTypeId);

        // Get all the applicantEducationalInformationList where examType equals to examTypeId + 1
        defaultApplicantEducationalInformationShouldNotBeFound("examTypeId.equals=" + (examTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicantEducationalInformationsByApplicantIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);
        Applicant applicant = ApplicantResourceIT.createEntity(em);
        em.persist(applicant);
        em.flush();
        applicantEducationalInformation.setApplicant(applicant);
        applicantEducationalInformationRepository.saveAndFlush(applicantEducationalInformation);
        Long applicantId = applicant.getId();

        // Get all the applicantEducationalInformationList where applicant equals to applicantId
        defaultApplicantEducationalInformationShouldBeFound("applicantId.equals=" + applicantId);

        // Get all the applicantEducationalInformationList where applicant equals to applicantId + 1
        defaultApplicantEducationalInformationShouldNotBeFound("applicantId.equals=" + (applicantId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicantEducationalInformationShouldBeFound(String filter) throws Exception {
        restApplicantEducationalInformationMockMvc.perform(get("/api/applicant-educational-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantEducationalInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].instituteName").value(hasItem(DEFAULT_INSTITUTE_NAME)))
            .andExpect(jsonPath("$.[*].board").value(hasItem(DEFAULT_BOARD)))
            .andExpect(jsonPath("$.[*].totalMarksGrade").value(hasItem(DEFAULT_TOTAL_MARKS_GRADE)))
            .andExpect(jsonPath("$.[*].divisionClassGrade").value(hasItem(DEFAULT_DIVISION_CLASS_GRADE)))
            .andExpect(jsonPath("$.[*].passingYear").value(hasItem(DEFAULT_PASSING_YEAR)));

        // Check, that the count call also returns 1
        restApplicantEducationalInformationMockMvc.perform(get("/api/applicant-educational-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicantEducationalInformationShouldNotBeFound(String filter) throws Exception {
        restApplicantEducationalInformationMockMvc.perform(get("/api/applicant-educational-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicantEducationalInformationMockMvc.perform(get("/api/applicant-educational-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
