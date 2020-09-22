package aust.edu.web.rest;

import aust.edu.PgadmissionApp;
import aust.edu.domain.ApplicantPersonalInfo;
import aust.edu.domain.Applicant;
import aust.edu.repository.ApplicantPersonalInfoRepository;
import aust.edu.service.ApplicantPersonalInfoService;
import aust.edu.service.dto.ApplicantPersonalInfoCriteria;
import aust.edu.service.ApplicantPersonalInfoQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import aust.edu.domain.enumeration.Gender;
import aust.edu.domain.enumeration.Religion;
import aust.edu.domain.enumeration.MaritalStatus;
/**
 * Integration tests for the {@link ApplicantPersonalInfoResource} REST controller.
 */
@SpringBootTest(classes = PgadmissionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ApplicantPersonalInfoResourceIT {

    private static final Integer DEFAULT_APPLICATION_SERIAL = 1;
    private static final Integer UPDATED_APPLICATION_SERIAL = 2;
    private static final Integer SMALLER_APPLICATION_SERIAL = 1 - 1;

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FATHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOTHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHER_NAME = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final Religion DEFAULT_RELIGION = Religion.ISLAM;
    private static final Religion UPDATED_RELIGION = Religion.HINDU;

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_OF_BIRTH = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PLACE_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_OF_BIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final MaritalStatus DEFAULT_MARITAL_STATUS = MaritalStatus.MARRIED;
    private static final MaritalStatus UPDATED_MARITAL_STATUS = MaritalStatus.UNMARRIED;

    @Autowired
    private ApplicantPersonalInfoRepository applicantPersonalInfoRepository;

    @Autowired
    private ApplicantPersonalInfoService applicantPersonalInfoService;

    @Autowired
    private ApplicantPersonalInfoQueryService applicantPersonalInfoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicantPersonalInfoMockMvc;

    private ApplicantPersonalInfo applicantPersonalInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantPersonalInfo createEntity(EntityManager em) {
        ApplicantPersonalInfo applicantPersonalInfo = new ApplicantPersonalInfo()
            .applicationSerial(DEFAULT_APPLICATION_SERIAL)
            .fullName(DEFAULT_FULL_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .fatherName(DEFAULT_FATHER_NAME)
            .motherName(DEFAULT_MOTHER_NAME)
            .gender(DEFAULT_GENDER)
            .religion(DEFAULT_RELIGION)
            .nationality(DEFAULT_NATIONALITY)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .placeOfBirth(DEFAULT_PLACE_OF_BIRTH)
            .mobileNumber(DEFAULT_MOBILE_NUMBER)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .maritalStatus(DEFAULT_MARITAL_STATUS);
        return applicantPersonalInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantPersonalInfo createUpdatedEntity(EntityManager em) {
        ApplicantPersonalInfo applicantPersonalInfo = new ApplicantPersonalInfo()
            .applicationSerial(UPDATED_APPLICATION_SERIAL)
            .fullName(UPDATED_FULL_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .gender(UPDATED_GENDER)
            .religion(UPDATED_RELIGION)
            .nationality(UPDATED_NATIONALITY)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .maritalStatus(UPDATED_MARITAL_STATUS);
        return applicantPersonalInfo;
    }

    @BeforeEach
    public void initTest() {
        applicantPersonalInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicantPersonalInfo() throws Exception {
        int databaseSizeBeforeCreate = applicantPersonalInfoRepository.findAll().size();
        // Create the ApplicantPersonalInfo
        restApplicantPersonalInfoMockMvc.perform(post("/api/applicant-personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantPersonalInfo)))
            .andExpect(status().isCreated());

        // Validate the ApplicantPersonalInfo in the database
        List<ApplicantPersonalInfo> applicantPersonalInfoList = applicantPersonalInfoRepository.findAll();
        assertThat(applicantPersonalInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicantPersonalInfo testApplicantPersonalInfo = applicantPersonalInfoList.get(applicantPersonalInfoList.size() - 1);
        assertThat(testApplicantPersonalInfo.getApplicationSerial()).isEqualTo(DEFAULT_APPLICATION_SERIAL);
        assertThat(testApplicantPersonalInfo.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testApplicantPersonalInfo.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testApplicantPersonalInfo.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testApplicantPersonalInfo.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testApplicantPersonalInfo.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testApplicantPersonalInfo.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testApplicantPersonalInfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testApplicantPersonalInfo.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testApplicantPersonalInfo.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testApplicantPersonalInfo.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testApplicantPersonalInfo.getPlaceOfBirth()).isEqualTo(DEFAULT_PLACE_OF_BIRTH);
        assertThat(testApplicantPersonalInfo.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testApplicantPersonalInfo.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testApplicantPersonalInfo.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void createApplicantPersonalInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicantPersonalInfoRepository.findAll().size();

        // Create the ApplicantPersonalInfo with an existing ID
        applicantPersonalInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicantPersonalInfoMockMvc.perform(post("/api/applicant-personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantPersonalInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicantPersonalInfo in the database
        List<ApplicantPersonalInfo> applicantPersonalInfoList = applicantPersonalInfoRepository.findAll();
        assertThat(applicantPersonalInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantPersonalInfoRepository.findAll().size();
        // set the field null
        applicantPersonalInfo.setFullName(null);

        // Create the ApplicantPersonalInfo, which fails.


        restApplicantPersonalInfoMockMvc.perform(post("/api/applicant-personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantPersonalInfo)))
            .andExpect(status().isBadRequest());

        List<ApplicantPersonalInfo> applicantPersonalInfoList = applicantPersonalInfoRepository.findAll();
        assertThat(applicantPersonalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantPersonalInfoRepository.findAll().size();
        // set the field null
        applicantPersonalInfo.setFirstName(null);

        // Create the ApplicantPersonalInfo, which fails.


        restApplicantPersonalInfoMockMvc.perform(post("/api/applicant-personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantPersonalInfo)))
            .andExpect(status().isBadRequest());

        List<ApplicantPersonalInfo> applicantPersonalInfoList = applicantPersonalInfoRepository.findAll();
        assertThat(applicantPersonalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFatherNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantPersonalInfoRepository.findAll().size();
        // set the field null
        applicantPersonalInfo.setFatherName(null);

        // Create the ApplicantPersonalInfo, which fails.


        restApplicantPersonalInfoMockMvc.perform(post("/api/applicant-personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantPersonalInfo)))
            .andExpect(status().isBadRequest());

        List<ApplicantPersonalInfo> applicantPersonalInfoList = applicantPersonalInfoRepository.findAll();
        assertThat(applicantPersonalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMotherNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantPersonalInfoRepository.findAll().size();
        // set the field null
        applicantPersonalInfo.setMotherName(null);

        // Create the ApplicantPersonalInfo, which fails.


        restApplicantPersonalInfoMockMvc.perform(post("/api/applicant-personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantPersonalInfo)))
            .andExpect(status().isBadRequest());

        List<ApplicantPersonalInfo> applicantPersonalInfoList = applicantPersonalInfoRepository.findAll();
        assertThat(applicantPersonalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantPersonalInfoRepository.findAll().size();
        // set the field null
        applicantPersonalInfo.setGender(null);

        // Create the ApplicantPersonalInfo, which fails.


        restApplicantPersonalInfoMockMvc.perform(post("/api/applicant-personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantPersonalInfo)))
            .andExpect(status().isBadRequest());

        List<ApplicantPersonalInfo> applicantPersonalInfoList = applicantPersonalInfoRepository.findAll();
        assertThat(applicantPersonalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReligionIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantPersonalInfoRepository.findAll().size();
        // set the field null
        applicantPersonalInfo.setReligion(null);

        // Create the ApplicantPersonalInfo, which fails.


        restApplicantPersonalInfoMockMvc.perform(post("/api/applicant-personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantPersonalInfo)))
            .andExpect(status().isBadRequest());

        List<ApplicantPersonalInfo> applicantPersonalInfoList = applicantPersonalInfoRepository.findAll();
        assertThat(applicantPersonalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNationalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantPersonalInfoRepository.findAll().size();
        // set the field null
        applicantPersonalInfo.setNationality(null);

        // Create the ApplicantPersonalInfo, which fails.


        restApplicantPersonalInfoMockMvc.perform(post("/api/applicant-personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantPersonalInfo)))
            .andExpect(status().isBadRequest());

        List<ApplicantPersonalInfo> applicantPersonalInfoList = applicantPersonalInfoRepository.findAll();
        assertThat(applicantPersonalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantPersonalInfoRepository.findAll().size();
        // set the field null
        applicantPersonalInfo.setDateOfBirth(null);

        // Create the ApplicantPersonalInfo, which fails.


        restApplicantPersonalInfoMockMvc.perform(post("/api/applicant-personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantPersonalInfo)))
            .andExpect(status().isBadRequest());

        List<ApplicantPersonalInfo> applicantPersonalInfoList = applicantPersonalInfoRepository.findAll();
        assertThat(applicantPersonalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMobileNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantPersonalInfoRepository.findAll().size();
        // set the field null
        applicantPersonalInfo.setMobileNumber(null);

        // Create the ApplicantPersonalInfo, which fails.


        restApplicantPersonalInfoMockMvc.perform(post("/api/applicant-personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantPersonalInfo)))
            .andExpect(status().isBadRequest());

        List<ApplicantPersonalInfo> applicantPersonalInfoList = applicantPersonalInfoRepository.findAll();
        assertThat(applicantPersonalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantPersonalInfoRepository.findAll().size();
        // set the field null
        applicantPersonalInfo.setEmailAddress(null);

        // Create the ApplicantPersonalInfo, which fails.


        restApplicantPersonalInfoMockMvc.perform(post("/api/applicant-personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantPersonalInfo)))
            .andExpect(status().isBadRequest());

        List<ApplicantPersonalInfo> applicantPersonalInfoList = applicantPersonalInfoRepository.findAll();
        assertThat(applicantPersonalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaritalStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantPersonalInfoRepository.findAll().size();
        // set the field null
        applicantPersonalInfo.setMaritalStatus(null);

        // Create the ApplicantPersonalInfo, which fails.


        restApplicantPersonalInfoMockMvc.perform(post("/api/applicant-personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantPersonalInfo)))
            .andExpect(status().isBadRequest());

        List<ApplicantPersonalInfo> applicantPersonalInfoList = applicantPersonalInfoRepository.findAll();
        assertThat(applicantPersonalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfos() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList
        restApplicantPersonalInfoMockMvc.perform(get("/api/applicant-personal-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantPersonalInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicationSerial").value(hasItem(DEFAULT_APPLICATION_SERIAL)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].placeOfBirth").value(hasItem(DEFAULT_PLACE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getApplicantPersonalInfo() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get the applicantPersonalInfo
        restApplicantPersonalInfoMockMvc.perform(get("/api/applicant-personal-infos/{id}", applicantPersonalInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicantPersonalInfo.getId().intValue()))
            .andExpect(jsonPath("$.applicationSerial").value(DEFAULT_APPLICATION_SERIAL))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME))
            .andExpect(jsonPath("$.motherName").value(DEFAULT_MOTHER_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.placeOfBirth").value(DEFAULT_PLACE_OF_BIRTH))
            .andExpect(jsonPath("$.mobileNumber").value(DEFAULT_MOBILE_NUMBER))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getApplicantPersonalInfosByIdFiltering() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        Long id = applicantPersonalInfo.getId();

        defaultApplicantPersonalInfoShouldBeFound("id.equals=" + id);
        defaultApplicantPersonalInfoShouldNotBeFound("id.notEquals=" + id);

        defaultApplicantPersonalInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApplicantPersonalInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultApplicantPersonalInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApplicantPersonalInfoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByApplicationSerialIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where applicationSerial equals to DEFAULT_APPLICATION_SERIAL
        defaultApplicantPersonalInfoShouldBeFound("applicationSerial.equals=" + DEFAULT_APPLICATION_SERIAL);

        // Get all the applicantPersonalInfoList where applicationSerial equals to UPDATED_APPLICATION_SERIAL
        defaultApplicantPersonalInfoShouldNotBeFound("applicationSerial.equals=" + UPDATED_APPLICATION_SERIAL);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByApplicationSerialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where applicationSerial not equals to DEFAULT_APPLICATION_SERIAL
        defaultApplicantPersonalInfoShouldNotBeFound("applicationSerial.notEquals=" + DEFAULT_APPLICATION_SERIAL);

        // Get all the applicantPersonalInfoList where applicationSerial not equals to UPDATED_APPLICATION_SERIAL
        defaultApplicantPersonalInfoShouldBeFound("applicationSerial.notEquals=" + UPDATED_APPLICATION_SERIAL);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByApplicationSerialIsInShouldWork() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where applicationSerial in DEFAULT_APPLICATION_SERIAL or UPDATED_APPLICATION_SERIAL
        defaultApplicantPersonalInfoShouldBeFound("applicationSerial.in=" + DEFAULT_APPLICATION_SERIAL + "," + UPDATED_APPLICATION_SERIAL);

        // Get all the applicantPersonalInfoList where applicationSerial equals to UPDATED_APPLICATION_SERIAL
        defaultApplicantPersonalInfoShouldNotBeFound("applicationSerial.in=" + UPDATED_APPLICATION_SERIAL);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByApplicationSerialIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where applicationSerial is not null
        defaultApplicantPersonalInfoShouldBeFound("applicationSerial.specified=true");

        // Get all the applicantPersonalInfoList where applicationSerial is null
        defaultApplicantPersonalInfoShouldNotBeFound("applicationSerial.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByApplicationSerialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where applicationSerial is greater than or equal to DEFAULT_APPLICATION_SERIAL
        defaultApplicantPersonalInfoShouldBeFound("applicationSerial.greaterThanOrEqual=" + DEFAULT_APPLICATION_SERIAL);

        // Get all the applicantPersonalInfoList where applicationSerial is greater than or equal to UPDATED_APPLICATION_SERIAL
        defaultApplicantPersonalInfoShouldNotBeFound("applicationSerial.greaterThanOrEqual=" + UPDATED_APPLICATION_SERIAL);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByApplicationSerialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where applicationSerial is less than or equal to DEFAULT_APPLICATION_SERIAL
        defaultApplicantPersonalInfoShouldBeFound("applicationSerial.lessThanOrEqual=" + DEFAULT_APPLICATION_SERIAL);

        // Get all the applicantPersonalInfoList where applicationSerial is less than or equal to SMALLER_APPLICATION_SERIAL
        defaultApplicantPersonalInfoShouldNotBeFound("applicationSerial.lessThanOrEqual=" + SMALLER_APPLICATION_SERIAL);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByApplicationSerialIsLessThanSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where applicationSerial is less than DEFAULT_APPLICATION_SERIAL
        defaultApplicantPersonalInfoShouldNotBeFound("applicationSerial.lessThan=" + DEFAULT_APPLICATION_SERIAL);

        // Get all the applicantPersonalInfoList where applicationSerial is less than UPDATED_APPLICATION_SERIAL
        defaultApplicantPersonalInfoShouldBeFound("applicationSerial.lessThan=" + UPDATED_APPLICATION_SERIAL);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByApplicationSerialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where applicationSerial is greater than DEFAULT_APPLICATION_SERIAL
        defaultApplicantPersonalInfoShouldNotBeFound("applicationSerial.greaterThan=" + DEFAULT_APPLICATION_SERIAL);

        // Get all the applicantPersonalInfoList where applicationSerial is greater than SMALLER_APPLICATION_SERIAL
        defaultApplicantPersonalInfoShouldBeFound("applicationSerial.greaterThan=" + SMALLER_APPLICATION_SERIAL);
    }


    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where fullName equals to DEFAULT_FULL_NAME
        defaultApplicantPersonalInfoShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the applicantPersonalInfoList where fullName equals to UPDATED_FULL_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByFullNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where fullName not equals to DEFAULT_FULL_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("fullName.notEquals=" + DEFAULT_FULL_NAME);

        // Get all the applicantPersonalInfoList where fullName not equals to UPDATED_FULL_NAME
        defaultApplicantPersonalInfoShouldBeFound("fullName.notEquals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultApplicantPersonalInfoShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the applicantPersonalInfoList where fullName equals to UPDATED_FULL_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where fullName is not null
        defaultApplicantPersonalInfoShouldBeFound("fullName.specified=true");

        // Get all the applicantPersonalInfoList where fullName is null
        defaultApplicantPersonalInfoShouldNotBeFound("fullName.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicantPersonalInfosByFullNameContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where fullName contains DEFAULT_FULL_NAME
        defaultApplicantPersonalInfoShouldBeFound("fullName.contains=" + DEFAULT_FULL_NAME);

        // Get all the applicantPersonalInfoList where fullName contains UPDATED_FULL_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where fullName does not contain DEFAULT_FULL_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("fullName.doesNotContain=" + DEFAULT_FULL_NAME);

        // Get all the applicantPersonalInfoList where fullName does not contain UPDATED_FULL_NAME
        defaultApplicantPersonalInfoShouldBeFound("fullName.doesNotContain=" + UPDATED_FULL_NAME);
    }


    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where firstName equals to DEFAULT_FIRST_NAME
        defaultApplicantPersonalInfoShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the applicantPersonalInfoList where firstName equals to UPDATED_FIRST_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where firstName not equals to DEFAULT_FIRST_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the applicantPersonalInfoList where firstName not equals to UPDATED_FIRST_NAME
        defaultApplicantPersonalInfoShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultApplicantPersonalInfoShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the applicantPersonalInfoList where firstName equals to UPDATED_FIRST_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where firstName is not null
        defaultApplicantPersonalInfoShouldBeFound("firstName.specified=true");

        // Get all the applicantPersonalInfoList where firstName is null
        defaultApplicantPersonalInfoShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicantPersonalInfosByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where firstName contains DEFAULT_FIRST_NAME
        defaultApplicantPersonalInfoShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the applicantPersonalInfoList where firstName contains UPDATED_FIRST_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where firstName does not contain DEFAULT_FIRST_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the applicantPersonalInfoList where firstName does not contain UPDATED_FIRST_NAME
        defaultApplicantPersonalInfoShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultApplicantPersonalInfoShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the applicantPersonalInfoList where middleName equals to UPDATED_MIDDLE_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMiddleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where middleName not equals to DEFAULT_MIDDLE_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("middleName.notEquals=" + DEFAULT_MIDDLE_NAME);

        // Get all the applicantPersonalInfoList where middleName not equals to UPDATED_MIDDLE_NAME
        defaultApplicantPersonalInfoShouldBeFound("middleName.notEquals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultApplicantPersonalInfoShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the applicantPersonalInfoList where middleName equals to UPDATED_MIDDLE_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where middleName is not null
        defaultApplicantPersonalInfoShouldBeFound("middleName.specified=true");

        // Get all the applicantPersonalInfoList where middleName is null
        defaultApplicantPersonalInfoShouldNotBeFound("middleName.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where middleName contains DEFAULT_MIDDLE_NAME
        defaultApplicantPersonalInfoShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the applicantPersonalInfoList where middleName contains UPDATED_MIDDLE_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the applicantPersonalInfoList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultApplicantPersonalInfoShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }


    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where lastName equals to DEFAULT_LAST_NAME
        defaultApplicantPersonalInfoShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the applicantPersonalInfoList where lastName equals to UPDATED_LAST_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where lastName not equals to DEFAULT_LAST_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the applicantPersonalInfoList where lastName not equals to UPDATED_LAST_NAME
        defaultApplicantPersonalInfoShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultApplicantPersonalInfoShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the applicantPersonalInfoList where lastName equals to UPDATED_LAST_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where lastName is not null
        defaultApplicantPersonalInfoShouldBeFound("lastName.specified=true");

        // Get all the applicantPersonalInfoList where lastName is null
        defaultApplicantPersonalInfoShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicantPersonalInfosByLastNameContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where lastName contains DEFAULT_LAST_NAME
        defaultApplicantPersonalInfoShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the applicantPersonalInfoList where lastName contains UPDATED_LAST_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where lastName does not contain DEFAULT_LAST_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the applicantPersonalInfoList where lastName does not contain UPDATED_LAST_NAME
        defaultApplicantPersonalInfoShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByFatherNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where fatherName equals to DEFAULT_FATHER_NAME
        defaultApplicantPersonalInfoShouldBeFound("fatherName.equals=" + DEFAULT_FATHER_NAME);

        // Get all the applicantPersonalInfoList where fatherName equals to UPDATED_FATHER_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("fatherName.equals=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByFatherNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where fatherName not equals to DEFAULT_FATHER_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("fatherName.notEquals=" + DEFAULT_FATHER_NAME);

        // Get all the applicantPersonalInfoList where fatherName not equals to UPDATED_FATHER_NAME
        defaultApplicantPersonalInfoShouldBeFound("fatherName.notEquals=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByFatherNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where fatherName in DEFAULT_FATHER_NAME or UPDATED_FATHER_NAME
        defaultApplicantPersonalInfoShouldBeFound("fatherName.in=" + DEFAULT_FATHER_NAME + "," + UPDATED_FATHER_NAME);

        // Get all the applicantPersonalInfoList where fatherName equals to UPDATED_FATHER_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("fatherName.in=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByFatherNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where fatherName is not null
        defaultApplicantPersonalInfoShouldBeFound("fatherName.specified=true");

        // Get all the applicantPersonalInfoList where fatherName is null
        defaultApplicantPersonalInfoShouldNotBeFound("fatherName.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicantPersonalInfosByFatherNameContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where fatherName contains DEFAULT_FATHER_NAME
        defaultApplicantPersonalInfoShouldBeFound("fatherName.contains=" + DEFAULT_FATHER_NAME);

        // Get all the applicantPersonalInfoList where fatherName contains UPDATED_FATHER_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("fatherName.contains=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByFatherNameNotContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where fatherName does not contain DEFAULT_FATHER_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("fatherName.doesNotContain=" + DEFAULT_FATHER_NAME);

        // Get all the applicantPersonalInfoList where fatherName does not contain UPDATED_FATHER_NAME
        defaultApplicantPersonalInfoShouldBeFound("fatherName.doesNotContain=" + UPDATED_FATHER_NAME);
    }


    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMotherNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where motherName equals to DEFAULT_MOTHER_NAME
        defaultApplicantPersonalInfoShouldBeFound("motherName.equals=" + DEFAULT_MOTHER_NAME);

        // Get all the applicantPersonalInfoList where motherName equals to UPDATED_MOTHER_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("motherName.equals=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMotherNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where motherName not equals to DEFAULT_MOTHER_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("motherName.notEquals=" + DEFAULT_MOTHER_NAME);

        // Get all the applicantPersonalInfoList where motherName not equals to UPDATED_MOTHER_NAME
        defaultApplicantPersonalInfoShouldBeFound("motherName.notEquals=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMotherNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where motherName in DEFAULT_MOTHER_NAME or UPDATED_MOTHER_NAME
        defaultApplicantPersonalInfoShouldBeFound("motherName.in=" + DEFAULT_MOTHER_NAME + "," + UPDATED_MOTHER_NAME);

        // Get all the applicantPersonalInfoList where motherName equals to UPDATED_MOTHER_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("motherName.in=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMotherNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where motherName is not null
        defaultApplicantPersonalInfoShouldBeFound("motherName.specified=true");

        // Get all the applicantPersonalInfoList where motherName is null
        defaultApplicantPersonalInfoShouldNotBeFound("motherName.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMotherNameContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where motherName contains DEFAULT_MOTHER_NAME
        defaultApplicantPersonalInfoShouldBeFound("motherName.contains=" + DEFAULT_MOTHER_NAME);

        // Get all the applicantPersonalInfoList where motherName contains UPDATED_MOTHER_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("motherName.contains=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMotherNameNotContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where motherName does not contain DEFAULT_MOTHER_NAME
        defaultApplicantPersonalInfoShouldNotBeFound("motherName.doesNotContain=" + DEFAULT_MOTHER_NAME);

        // Get all the applicantPersonalInfoList where motherName does not contain UPDATED_MOTHER_NAME
        defaultApplicantPersonalInfoShouldBeFound("motherName.doesNotContain=" + UPDATED_MOTHER_NAME);
    }


    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where gender equals to DEFAULT_GENDER
        defaultApplicantPersonalInfoShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the applicantPersonalInfoList where gender equals to UPDATED_GENDER
        defaultApplicantPersonalInfoShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where gender not equals to DEFAULT_GENDER
        defaultApplicantPersonalInfoShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the applicantPersonalInfoList where gender not equals to UPDATED_GENDER
        defaultApplicantPersonalInfoShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultApplicantPersonalInfoShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the applicantPersonalInfoList where gender equals to UPDATED_GENDER
        defaultApplicantPersonalInfoShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where gender is not null
        defaultApplicantPersonalInfoShouldBeFound("gender.specified=true");

        // Get all the applicantPersonalInfoList where gender is null
        defaultApplicantPersonalInfoShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByReligionIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where religion equals to DEFAULT_RELIGION
        defaultApplicantPersonalInfoShouldBeFound("religion.equals=" + DEFAULT_RELIGION);

        // Get all the applicantPersonalInfoList where religion equals to UPDATED_RELIGION
        defaultApplicantPersonalInfoShouldNotBeFound("religion.equals=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByReligionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where religion not equals to DEFAULT_RELIGION
        defaultApplicantPersonalInfoShouldNotBeFound("religion.notEquals=" + DEFAULT_RELIGION);

        // Get all the applicantPersonalInfoList where religion not equals to UPDATED_RELIGION
        defaultApplicantPersonalInfoShouldBeFound("religion.notEquals=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByReligionIsInShouldWork() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where religion in DEFAULT_RELIGION or UPDATED_RELIGION
        defaultApplicantPersonalInfoShouldBeFound("religion.in=" + DEFAULT_RELIGION + "," + UPDATED_RELIGION);

        // Get all the applicantPersonalInfoList where religion equals to UPDATED_RELIGION
        defaultApplicantPersonalInfoShouldNotBeFound("religion.in=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByReligionIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where religion is not null
        defaultApplicantPersonalInfoShouldBeFound("religion.specified=true");

        // Get all the applicantPersonalInfoList where religion is null
        defaultApplicantPersonalInfoShouldNotBeFound("religion.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByNationalityIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where nationality equals to DEFAULT_NATIONALITY
        defaultApplicantPersonalInfoShouldBeFound("nationality.equals=" + DEFAULT_NATIONALITY);

        // Get all the applicantPersonalInfoList where nationality equals to UPDATED_NATIONALITY
        defaultApplicantPersonalInfoShouldNotBeFound("nationality.equals=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByNationalityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where nationality not equals to DEFAULT_NATIONALITY
        defaultApplicantPersonalInfoShouldNotBeFound("nationality.notEquals=" + DEFAULT_NATIONALITY);

        // Get all the applicantPersonalInfoList where nationality not equals to UPDATED_NATIONALITY
        defaultApplicantPersonalInfoShouldBeFound("nationality.notEquals=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByNationalityIsInShouldWork() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where nationality in DEFAULT_NATIONALITY or UPDATED_NATIONALITY
        defaultApplicantPersonalInfoShouldBeFound("nationality.in=" + DEFAULT_NATIONALITY + "," + UPDATED_NATIONALITY);

        // Get all the applicantPersonalInfoList where nationality equals to UPDATED_NATIONALITY
        defaultApplicantPersonalInfoShouldNotBeFound("nationality.in=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByNationalityIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where nationality is not null
        defaultApplicantPersonalInfoShouldBeFound("nationality.specified=true");

        // Get all the applicantPersonalInfoList where nationality is null
        defaultApplicantPersonalInfoShouldNotBeFound("nationality.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicantPersonalInfosByNationalityContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where nationality contains DEFAULT_NATIONALITY
        defaultApplicantPersonalInfoShouldBeFound("nationality.contains=" + DEFAULT_NATIONALITY);

        // Get all the applicantPersonalInfoList where nationality contains UPDATED_NATIONALITY
        defaultApplicantPersonalInfoShouldNotBeFound("nationality.contains=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByNationalityNotContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where nationality does not contain DEFAULT_NATIONALITY
        defaultApplicantPersonalInfoShouldNotBeFound("nationality.doesNotContain=" + DEFAULT_NATIONALITY);

        // Get all the applicantPersonalInfoList where nationality does not contain UPDATED_NATIONALITY
        defaultApplicantPersonalInfoShouldBeFound("nationality.doesNotContain=" + UPDATED_NATIONALITY);
    }


    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where dateOfBirth equals to DEFAULT_DATE_OF_BIRTH
        defaultApplicantPersonalInfoShouldBeFound("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the applicantPersonalInfoList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultApplicantPersonalInfoShouldNotBeFound("dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByDateOfBirthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where dateOfBirth not equals to DEFAULT_DATE_OF_BIRTH
        defaultApplicantPersonalInfoShouldNotBeFound("dateOfBirth.notEquals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the applicantPersonalInfoList where dateOfBirth not equals to UPDATED_DATE_OF_BIRTH
        defaultApplicantPersonalInfoShouldBeFound("dateOfBirth.notEquals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where dateOfBirth in DEFAULT_DATE_OF_BIRTH or UPDATED_DATE_OF_BIRTH
        defaultApplicantPersonalInfoShouldBeFound("dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH);

        // Get all the applicantPersonalInfoList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultApplicantPersonalInfoShouldNotBeFound("dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where dateOfBirth is not null
        defaultApplicantPersonalInfoShouldBeFound("dateOfBirth.specified=true");

        // Get all the applicantPersonalInfoList where dateOfBirth is null
        defaultApplicantPersonalInfoShouldNotBeFound("dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByDateOfBirthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where dateOfBirth is greater than or equal to DEFAULT_DATE_OF_BIRTH
        defaultApplicantPersonalInfoShouldBeFound("dateOfBirth.greaterThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the applicantPersonalInfoList where dateOfBirth is greater than or equal to UPDATED_DATE_OF_BIRTH
        defaultApplicantPersonalInfoShouldNotBeFound("dateOfBirth.greaterThanOrEqual=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByDateOfBirthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where dateOfBirth is less than or equal to DEFAULT_DATE_OF_BIRTH
        defaultApplicantPersonalInfoShouldBeFound("dateOfBirth.lessThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the applicantPersonalInfoList where dateOfBirth is less than or equal to SMALLER_DATE_OF_BIRTH
        defaultApplicantPersonalInfoShouldNotBeFound("dateOfBirth.lessThanOrEqual=" + SMALLER_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByDateOfBirthIsLessThanSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where dateOfBirth is less than DEFAULT_DATE_OF_BIRTH
        defaultApplicantPersonalInfoShouldNotBeFound("dateOfBirth.lessThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the applicantPersonalInfoList where dateOfBirth is less than UPDATED_DATE_OF_BIRTH
        defaultApplicantPersonalInfoShouldBeFound("dateOfBirth.lessThan=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByDateOfBirthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where dateOfBirth is greater than DEFAULT_DATE_OF_BIRTH
        defaultApplicantPersonalInfoShouldNotBeFound("dateOfBirth.greaterThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the applicantPersonalInfoList where dateOfBirth is greater than SMALLER_DATE_OF_BIRTH
        defaultApplicantPersonalInfoShouldBeFound("dateOfBirth.greaterThan=" + SMALLER_DATE_OF_BIRTH);
    }


    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByPlaceOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where placeOfBirth equals to DEFAULT_PLACE_OF_BIRTH
        defaultApplicantPersonalInfoShouldBeFound("placeOfBirth.equals=" + DEFAULT_PLACE_OF_BIRTH);

        // Get all the applicantPersonalInfoList where placeOfBirth equals to UPDATED_PLACE_OF_BIRTH
        defaultApplicantPersonalInfoShouldNotBeFound("placeOfBirth.equals=" + UPDATED_PLACE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByPlaceOfBirthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where placeOfBirth not equals to DEFAULT_PLACE_OF_BIRTH
        defaultApplicantPersonalInfoShouldNotBeFound("placeOfBirth.notEquals=" + DEFAULT_PLACE_OF_BIRTH);

        // Get all the applicantPersonalInfoList where placeOfBirth not equals to UPDATED_PLACE_OF_BIRTH
        defaultApplicantPersonalInfoShouldBeFound("placeOfBirth.notEquals=" + UPDATED_PLACE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByPlaceOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where placeOfBirth in DEFAULT_PLACE_OF_BIRTH or UPDATED_PLACE_OF_BIRTH
        defaultApplicantPersonalInfoShouldBeFound("placeOfBirth.in=" + DEFAULT_PLACE_OF_BIRTH + "," + UPDATED_PLACE_OF_BIRTH);

        // Get all the applicantPersonalInfoList where placeOfBirth equals to UPDATED_PLACE_OF_BIRTH
        defaultApplicantPersonalInfoShouldNotBeFound("placeOfBirth.in=" + UPDATED_PLACE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByPlaceOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where placeOfBirth is not null
        defaultApplicantPersonalInfoShouldBeFound("placeOfBirth.specified=true");

        // Get all the applicantPersonalInfoList where placeOfBirth is null
        defaultApplicantPersonalInfoShouldNotBeFound("placeOfBirth.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicantPersonalInfosByPlaceOfBirthContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where placeOfBirth contains DEFAULT_PLACE_OF_BIRTH
        defaultApplicantPersonalInfoShouldBeFound("placeOfBirth.contains=" + DEFAULT_PLACE_OF_BIRTH);

        // Get all the applicantPersonalInfoList where placeOfBirth contains UPDATED_PLACE_OF_BIRTH
        defaultApplicantPersonalInfoShouldNotBeFound("placeOfBirth.contains=" + UPDATED_PLACE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByPlaceOfBirthNotContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where placeOfBirth does not contain DEFAULT_PLACE_OF_BIRTH
        defaultApplicantPersonalInfoShouldNotBeFound("placeOfBirth.doesNotContain=" + DEFAULT_PLACE_OF_BIRTH);

        // Get all the applicantPersonalInfoList where placeOfBirth does not contain UPDATED_PLACE_OF_BIRTH
        defaultApplicantPersonalInfoShouldBeFound("placeOfBirth.doesNotContain=" + UPDATED_PLACE_OF_BIRTH);
    }


    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMobileNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where mobileNumber equals to DEFAULT_MOBILE_NUMBER
        defaultApplicantPersonalInfoShouldBeFound("mobileNumber.equals=" + DEFAULT_MOBILE_NUMBER);

        // Get all the applicantPersonalInfoList where mobileNumber equals to UPDATED_MOBILE_NUMBER
        defaultApplicantPersonalInfoShouldNotBeFound("mobileNumber.equals=" + UPDATED_MOBILE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMobileNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where mobileNumber not equals to DEFAULT_MOBILE_NUMBER
        defaultApplicantPersonalInfoShouldNotBeFound("mobileNumber.notEquals=" + DEFAULT_MOBILE_NUMBER);

        // Get all the applicantPersonalInfoList where mobileNumber not equals to UPDATED_MOBILE_NUMBER
        defaultApplicantPersonalInfoShouldBeFound("mobileNumber.notEquals=" + UPDATED_MOBILE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMobileNumberIsInShouldWork() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where mobileNumber in DEFAULT_MOBILE_NUMBER or UPDATED_MOBILE_NUMBER
        defaultApplicantPersonalInfoShouldBeFound("mobileNumber.in=" + DEFAULT_MOBILE_NUMBER + "," + UPDATED_MOBILE_NUMBER);

        // Get all the applicantPersonalInfoList where mobileNumber equals to UPDATED_MOBILE_NUMBER
        defaultApplicantPersonalInfoShouldNotBeFound("mobileNumber.in=" + UPDATED_MOBILE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMobileNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where mobileNumber is not null
        defaultApplicantPersonalInfoShouldBeFound("mobileNumber.specified=true");

        // Get all the applicantPersonalInfoList where mobileNumber is null
        defaultApplicantPersonalInfoShouldNotBeFound("mobileNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMobileNumberContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where mobileNumber contains DEFAULT_MOBILE_NUMBER
        defaultApplicantPersonalInfoShouldBeFound("mobileNumber.contains=" + DEFAULT_MOBILE_NUMBER);

        // Get all the applicantPersonalInfoList where mobileNumber contains UPDATED_MOBILE_NUMBER
        defaultApplicantPersonalInfoShouldNotBeFound("mobileNumber.contains=" + UPDATED_MOBILE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMobileNumberNotContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where mobileNumber does not contain DEFAULT_MOBILE_NUMBER
        defaultApplicantPersonalInfoShouldNotBeFound("mobileNumber.doesNotContain=" + DEFAULT_MOBILE_NUMBER);

        // Get all the applicantPersonalInfoList where mobileNumber does not contain UPDATED_MOBILE_NUMBER
        defaultApplicantPersonalInfoShouldBeFound("mobileNumber.doesNotContain=" + UPDATED_MOBILE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByEmailAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where emailAddress equals to DEFAULT_EMAIL_ADDRESS
        defaultApplicantPersonalInfoShouldBeFound("emailAddress.equals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the applicantPersonalInfoList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultApplicantPersonalInfoShouldNotBeFound("emailAddress.equals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByEmailAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where emailAddress not equals to DEFAULT_EMAIL_ADDRESS
        defaultApplicantPersonalInfoShouldNotBeFound("emailAddress.notEquals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the applicantPersonalInfoList where emailAddress not equals to UPDATED_EMAIL_ADDRESS
        defaultApplicantPersonalInfoShouldBeFound("emailAddress.notEquals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByEmailAddressIsInShouldWork() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where emailAddress in DEFAULT_EMAIL_ADDRESS or UPDATED_EMAIL_ADDRESS
        defaultApplicantPersonalInfoShouldBeFound("emailAddress.in=" + DEFAULT_EMAIL_ADDRESS + "," + UPDATED_EMAIL_ADDRESS);

        // Get all the applicantPersonalInfoList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultApplicantPersonalInfoShouldNotBeFound("emailAddress.in=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByEmailAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where emailAddress is not null
        defaultApplicantPersonalInfoShouldBeFound("emailAddress.specified=true");

        // Get all the applicantPersonalInfoList where emailAddress is null
        defaultApplicantPersonalInfoShouldNotBeFound("emailAddress.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicantPersonalInfosByEmailAddressContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where emailAddress contains DEFAULT_EMAIL_ADDRESS
        defaultApplicantPersonalInfoShouldBeFound("emailAddress.contains=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the applicantPersonalInfoList where emailAddress contains UPDATED_EMAIL_ADDRESS
        defaultApplicantPersonalInfoShouldNotBeFound("emailAddress.contains=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByEmailAddressNotContainsSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where emailAddress does not contain DEFAULT_EMAIL_ADDRESS
        defaultApplicantPersonalInfoShouldNotBeFound("emailAddress.doesNotContain=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the applicantPersonalInfoList where emailAddress does not contain UPDATED_EMAIL_ADDRESS
        defaultApplicantPersonalInfoShouldBeFound("emailAddress.doesNotContain=" + UPDATED_EMAIL_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMaritalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where maritalStatus equals to DEFAULT_MARITAL_STATUS
        defaultApplicantPersonalInfoShouldBeFound("maritalStatus.equals=" + DEFAULT_MARITAL_STATUS);

        // Get all the applicantPersonalInfoList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultApplicantPersonalInfoShouldNotBeFound("maritalStatus.equals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMaritalStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where maritalStatus not equals to DEFAULT_MARITAL_STATUS
        defaultApplicantPersonalInfoShouldNotBeFound("maritalStatus.notEquals=" + DEFAULT_MARITAL_STATUS);

        // Get all the applicantPersonalInfoList where maritalStatus not equals to UPDATED_MARITAL_STATUS
        defaultApplicantPersonalInfoShouldBeFound("maritalStatus.notEquals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMaritalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where maritalStatus in DEFAULT_MARITAL_STATUS or UPDATED_MARITAL_STATUS
        defaultApplicantPersonalInfoShouldBeFound("maritalStatus.in=" + DEFAULT_MARITAL_STATUS + "," + UPDATED_MARITAL_STATUS);

        // Get all the applicantPersonalInfoList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultApplicantPersonalInfoShouldNotBeFound("maritalStatus.in=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByMaritalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);

        // Get all the applicantPersonalInfoList where maritalStatus is not null
        defaultApplicantPersonalInfoShouldBeFound("maritalStatus.specified=true");

        // Get all the applicantPersonalInfoList where maritalStatus is null
        defaultApplicantPersonalInfoShouldNotBeFound("maritalStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicantPersonalInfosByApplicantIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);
        Applicant applicant = ApplicantResourceIT.createEntity(em);
        em.persist(applicant);
        em.flush();
        applicantPersonalInfo.setApplicant(applicant);
        applicantPersonalInfoRepository.saveAndFlush(applicantPersonalInfo);
        Long applicantId = applicant.getId();

        // Get all the applicantPersonalInfoList where applicant equals to applicantId
        defaultApplicantPersonalInfoShouldBeFound("applicantId.equals=" + applicantId);

        // Get all the applicantPersonalInfoList where applicant equals to applicantId + 1
        defaultApplicantPersonalInfoShouldNotBeFound("applicantId.equals=" + (applicantId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicantPersonalInfoShouldBeFound(String filter) throws Exception {
        restApplicantPersonalInfoMockMvc.perform(get("/api/applicant-personal-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantPersonalInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicationSerial").value(hasItem(DEFAULT_APPLICATION_SERIAL)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].placeOfBirth").value(hasItem(DEFAULT_PLACE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())));

        // Check, that the count call also returns 1
        restApplicantPersonalInfoMockMvc.perform(get("/api/applicant-personal-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicantPersonalInfoShouldNotBeFound(String filter) throws Exception {
        restApplicantPersonalInfoMockMvc.perform(get("/api/applicant-personal-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicantPersonalInfoMockMvc.perform(get("/api/applicant-personal-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingApplicantPersonalInfo() throws Exception {
        // Get the applicantPersonalInfo
        restApplicantPersonalInfoMockMvc.perform(get("/api/applicant-personal-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicantPersonalInfo() throws Exception {
        // Initialize the database
        applicantPersonalInfoService.save(applicantPersonalInfo);

        int databaseSizeBeforeUpdate = applicantPersonalInfoRepository.findAll().size();

        // Update the applicantPersonalInfo
        ApplicantPersonalInfo updatedApplicantPersonalInfo = applicantPersonalInfoRepository.findById(applicantPersonalInfo.getId()).get();
        // Disconnect from session so that the updates on updatedApplicantPersonalInfo are not directly saved in db
        em.detach(updatedApplicantPersonalInfo);
        updatedApplicantPersonalInfo
            .applicationSerial(UPDATED_APPLICATION_SERIAL)
            .fullName(UPDATED_FULL_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .gender(UPDATED_GENDER)
            .religion(UPDATED_RELIGION)
            .nationality(UPDATED_NATIONALITY)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .maritalStatus(UPDATED_MARITAL_STATUS);

        restApplicantPersonalInfoMockMvc.perform(put("/api/applicant-personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplicantPersonalInfo)))
            .andExpect(status().isOk());

        // Validate the ApplicantPersonalInfo in the database
        List<ApplicantPersonalInfo> applicantPersonalInfoList = applicantPersonalInfoRepository.findAll();
        assertThat(applicantPersonalInfoList).hasSize(databaseSizeBeforeUpdate);
        ApplicantPersonalInfo testApplicantPersonalInfo = applicantPersonalInfoList.get(applicantPersonalInfoList.size() - 1);
        assertThat(testApplicantPersonalInfo.getApplicationSerial()).isEqualTo(UPDATED_APPLICATION_SERIAL);
        assertThat(testApplicantPersonalInfo.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testApplicantPersonalInfo.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testApplicantPersonalInfo.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testApplicantPersonalInfo.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testApplicantPersonalInfo.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testApplicantPersonalInfo.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testApplicantPersonalInfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testApplicantPersonalInfo.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testApplicantPersonalInfo.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testApplicantPersonalInfo.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testApplicantPersonalInfo.getPlaceOfBirth()).isEqualTo(UPDATED_PLACE_OF_BIRTH);
        assertThat(testApplicantPersonalInfo.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testApplicantPersonalInfo.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testApplicantPersonalInfo.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicantPersonalInfo() throws Exception {
        int databaseSizeBeforeUpdate = applicantPersonalInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicantPersonalInfoMockMvc.perform(put("/api/applicant-personal-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantPersonalInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicantPersonalInfo in the database
        List<ApplicantPersonalInfo> applicantPersonalInfoList = applicantPersonalInfoRepository.findAll();
        assertThat(applicantPersonalInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicantPersonalInfo() throws Exception {
        // Initialize the database
        applicantPersonalInfoService.save(applicantPersonalInfo);

        int databaseSizeBeforeDelete = applicantPersonalInfoRepository.findAll().size();

        // Delete the applicantPersonalInfo
        restApplicantPersonalInfoMockMvc.perform(delete("/api/applicant-personal-infos/{id}", applicantPersonalInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicantPersonalInfo> applicantPersonalInfoList = applicantPersonalInfoRepository.findAll();
        assertThat(applicantPersonalInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
