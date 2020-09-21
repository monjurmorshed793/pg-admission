package aust.edu.web.rest;

import aust.edu.PgadmissionApp;
import aust.edu.domain.ApplicantPersonalInfo;
import aust.edu.repository.ApplicantPersonalInfoRepository;
import aust.edu.service.ApplicantPersonalInfoService;

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
