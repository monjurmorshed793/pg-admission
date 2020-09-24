package aust.edu.web.rest;

import aust.edu.PgadmissionApp;
import aust.edu.domain.ApplicantAddress;
import aust.edu.domain.District;
import aust.edu.domain.Division;
import aust.edu.domain.Thana;
import aust.edu.domain.Applicant;
import aust.edu.repository.ApplicantAddressRepository;
import aust.edu.service.ApplicantAddressService;
import aust.edu.service.dto.ApplicantAddressCriteria;
import aust.edu.service.ApplicantAddressQueryService;

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

import aust.edu.domain.enumeration.AddressType;
/**
 * Integration tests for the {@link ApplicantAddressResource} REST controller.
 */
@SpringBootTest(classes = PgadmissionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ApplicantAddressResourceIT {

    private static final Integer DEFAULT_APPLICATION_SERIAL = 1;
    private static final Integer UPDATED_APPLICATION_SERIAL = 2;
    private static final Integer SMALLER_APPLICATION_SERIAL = 1 - 1;

    private static final AddressType DEFAULT_ADDRESS_TYPE = AddressType.PRESENT;
    private static final AddressType UPDATED_ADDRESS_TYPE = AddressType.PERMANENT;

    private static final String DEFAULT_THANA_OTHER = "AAAAAAAAAA";
    private static final String UPDATED_THANA_OTHER = "BBBBBBBBBB";

    private static final String DEFAULT_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_LINE_2 = "BBBBBBBBBB";

    @Autowired
    private ApplicantAddressRepository applicantAddressRepository;

    @Autowired
    private ApplicantAddressService applicantAddressService;

    @Autowired
    private ApplicantAddressQueryService applicantAddressQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicantAddressMockMvc;

    private ApplicantAddress applicantAddress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantAddress createEntity(EntityManager em) {
        ApplicantAddress applicantAddress = new ApplicantAddress()
            .applicationSerial(DEFAULT_APPLICATION_SERIAL)
            .addressType(DEFAULT_ADDRESS_TYPE)
            .thanaOther(DEFAULT_THANA_OTHER)
            .line1(DEFAULT_LINE_1)
            .line2(DEFAULT_LINE_2);
        return applicantAddress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicantAddress createUpdatedEntity(EntityManager em) {
        ApplicantAddress applicantAddress = new ApplicantAddress()
            .applicationSerial(UPDATED_APPLICATION_SERIAL)
            .addressType(UPDATED_ADDRESS_TYPE)
            .thanaOther(UPDATED_THANA_OTHER)
            .line1(UPDATED_LINE_1)
            .line2(UPDATED_LINE_2);
        return applicantAddress;
    }

    @BeforeEach
    public void initTest() {
        applicantAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicantAddress() throws Exception {
        int databaseSizeBeforeCreate = applicantAddressRepository.findAll().size();
        // Create the ApplicantAddress
        restApplicantAddressMockMvc.perform(post("/api/applicant-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantAddress)))
            .andExpect(status().isCreated());

        // Validate the ApplicantAddress in the database
        List<ApplicantAddress> applicantAddressList = applicantAddressRepository.findAll();
        assertThat(applicantAddressList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicantAddress testApplicantAddress = applicantAddressList.get(applicantAddressList.size() - 1);
        assertThat(testApplicantAddress.getApplicationSerial()).isEqualTo(DEFAULT_APPLICATION_SERIAL);
        assertThat(testApplicantAddress.getAddressType()).isEqualTo(DEFAULT_ADDRESS_TYPE);
        assertThat(testApplicantAddress.getThanaOther()).isEqualTo(DEFAULT_THANA_OTHER);
        assertThat(testApplicantAddress.getLine1()).isEqualTo(DEFAULT_LINE_1);
        assertThat(testApplicantAddress.getLine2()).isEqualTo(DEFAULT_LINE_2);
    }

    @Test
    @Transactional
    public void createApplicantAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicantAddressRepository.findAll().size();

        // Create the ApplicantAddress with an existing ID
        applicantAddress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicantAddressMockMvc.perform(post("/api/applicant-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantAddress)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicantAddress in the database
        List<ApplicantAddress> applicantAddressList = applicantAddressRepository.findAll();
        assertThat(applicantAddressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAddressTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantAddressRepository.findAll().size();
        // set the field null
        applicantAddress.setAddressType(null);

        // Create the ApplicantAddress, which fails.


        restApplicantAddressMockMvc.perform(post("/api/applicant-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantAddress)))
            .andExpect(status().isBadRequest());

        List<ApplicantAddress> applicantAddressList = applicantAddressRepository.findAll();
        assertThat(applicantAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLine1IsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantAddressRepository.findAll().size();
        // set the field null
        applicantAddress.setLine1(null);

        // Create the ApplicantAddress, which fails.


        restApplicantAddressMockMvc.perform(post("/api/applicant-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantAddress)))
            .andExpect(status().isBadRequest());

        List<ApplicantAddress> applicantAddressList = applicantAddressRepository.findAll();
        assertThat(applicantAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicantAddresses() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList
        restApplicantAddressMockMvc.perform(get("/api/applicant-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicationSerial").value(hasItem(DEFAULT_APPLICATION_SERIAL)))
            .andExpect(jsonPath("$.[*].addressType").value(hasItem(DEFAULT_ADDRESS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].thanaOther").value(hasItem(DEFAULT_THANA_OTHER)))
            .andExpect(jsonPath("$.[*].line1").value(hasItem(DEFAULT_LINE_1)))
            .andExpect(jsonPath("$.[*].line2").value(hasItem(DEFAULT_LINE_2)));
    }
    
    @Test
    @Transactional
    public void getApplicantAddress() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get the applicantAddress
        restApplicantAddressMockMvc.perform(get("/api/applicant-addresses/{id}", applicantAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicantAddress.getId().intValue()))
            .andExpect(jsonPath("$.applicationSerial").value(DEFAULT_APPLICATION_SERIAL))
            .andExpect(jsonPath("$.addressType").value(DEFAULT_ADDRESS_TYPE.toString()))
            .andExpect(jsonPath("$.thanaOther").value(DEFAULT_THANA_OTHER))
            .andExpect(jsonPath("$.line1").value(DEFAULT_LINE_1))
            .andExpect(jsonPath("$.line2").value(DEFAULT_LINE_2));
    }


    @Test
    @Transactional
    public void getApplicantAddressesByIdFiltering() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        Long id = applicantAddress.getId();

        defaultApplicantAddressShouldBeFound("id.equals=" + id);
        defaultApplicantAddressShouldNotBeFound("id.notEquals=" + id);

        defaultApplicantAddressShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApplicantAddressShouldNotBeFound("id.greaterThan=" + id);

        defaultApplicantAddressShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApplicantAddressShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllApplicantAddressesByApplicationSerialIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where applicationSerial equals to DEFAULT_APPLICATION_SERIAL
        defaultApplicantAddressShouldBeFound("applicationSerial.equals=" + DEFAULT_APPLICATION_SERIAL);

        // Get all the applicantAddressList where applicationSerial equals to UPDATED_APPLICATION_SERIAL
        defaultApplicantAddressShouldNotBeFound("applicationSerial.equals=" + UPDATED_APPLICATION_SERIAL);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByApplicationSerialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where applicationSerial not equals to DEFAULT_APPLICATION_SERIAL
        defaultApplicantAddressShouldNotBeFound("applicationSerial.notEquals=" + DEFAULT_APPLICATION_SERIAL);

        // Get all the applicantAddressList where applicationSerial not equals to UPDATED_APPLICATION_SERIAL
        defaultApplicantAddressShouldBeFound("applicationSerial.notEquals=" + UPDATED_APPLICATION_SERIAL);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByApplicationSerialIsInShouldWork() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where applicationSerial in DEFAULT_APPLICATION_SERIAL or UPDATED_APPLICATION_SERIAL
        defaultApplicantAddressShouldBeFound("applicationSerial.in=" + DEFAULT_APPLICATION_SERIAL + "," + UPDATED_APPLICATION_SERIAL);

        // Get all the applicantAddressList where applicationSerial equals to UPDATED_APPLICATION_SERIAL
        defaultApplicantAddressShouldNotBeFound("applicationSerial.in=" + UPDATED_APPLICATION_SERIAL);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByApplicationSerialIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where applicationSerial is not null
        defaultApplicantAddressShouldBeFound("applicationSerial.specified=true");

        // Get all the applicantAddressList where applicationSerial is null
        defaultApplicantAddressShouldNotBeFound("applicationSerial.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByApplicationSerialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where applicationSerial is greater than or equal to DEFAULT_APPLICATION_SERIAL
        defaultApplicantAddressShouldBeFound("applicationSerial.greaterThanOrEqual=" + DEFAULT_APPLICATION_SERIAL);

        // Get all the applicantAddressList where applicationSerial is greater than or equal to UPDATED_APPLICATION_SERIAL
        defaultApplicantAddressShouldNotBeFound("applicationSerial.greaterThanOrEqual=" + UPDATED_APPLICATION_SERIAL);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByApplicationSerialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where applicationSerial is less than or equal to DEFAULT_APPLICATION_SERIAL
        defaultApplicantAddressShouldBeFound("applicationSerial.lessThanOrEqual=" + DEFAULT_APPLICATION_SERIAL);

        // Get all the applicantAddressList where applicationSerial is less than or equal to SMALLER_APPLICATION_SERIAL
        defaultApplicantAddressShouldNotBeFound("applicationSerial.lessThanOrEqual=" + SMALLER_APPLICATION_SERIAL);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByApplicationSerialIsLessThanSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where applicationSerial is less than DEFAULT_APPLICATION_SERIAL
        defaultApplicantAddressShouldNotBeFound("applicationSerial.lessThan=" + DEFAULT_APPLICATION_SERIAL);

        // Get all the applicantAddressList where applicationSerial is less than UPDATED_APPLICATION_SERIAL
        defaultApplicantAddressShouldBeFound("applicationSerial.lessThan=" + UPDATED_APPLICATION_SERIAL);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByApplicationSerialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where applicationSerial is greater than DEFAULT_APPLICATION_SERIAL
        defaultApplicantAddressShouldNotBeFound("applicationSerial.greaterThan=" + DEFAULT_APPLICATION_SERIAL);

        // Get all the applicantAddressList where applicationSerial is greater than SMALLER_APPLICATION_SERIAL
        defaultApplicantAddressShouldBeFound("applicationSerial.greaterThan=" + SMALLER_APPLICATION_SERIAL);
    }


    @Test
    @Transactional
    public void getAllApplicantAddressesByAddressTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where addressType equals to DEFAULT_ADDRESS_TYPE
        defaultApplicantAddressShouldBeFound("addressType.equals=" + DEFAULT_ADDRESS_TYPE);

        // Get all the applicantAddressList where addressType equals to UPDATED_ADDRESS_TYPE
        defaultApplicantAddressShouldNotBeFound("addressType.equals=" + UPDATED_ADDRESS_TYPE);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByAddressTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where addressType not equals to DEFAULT_ADDRESS_TYPE
        defaultApplicantAddressShouldNotBeFound("addressType.notEquals=" + DEFAULT_ADDRESS_TYPE);

        // Get all the applicantAddressList where addressType not equals to UPDATED_ADDRESS_TYPE
        defaultApplicantAddressShouldBeFound("addressType.notEquals=" + UPDATED_ADDRESS_TYPE);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByAddressTypeIsInShouldWork() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where addressType in DEFAULT_ADDRESS_TYPE or UPDATED_ADDRESS_TYPE
        defaultApplicantAddressShouldBeFound("addressType.in=" + DEFAULT_ADDRESS_TYPE + "," + UPDATED_ADDRESS_TYPE);

        // Get all the applicantAddressList where addressType equals to UPDATED_ADDRESS_TYPE
        defaultApplicantAddressShouldNotBeFound("addressType.in=" + UPDATED_ADDRESS_TYPE);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByAddressTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where addressType is not null
        defaultApplicantAddressShouldBeFound("addressType.specified=true");

        // Get all the applicantAddressList where addressType is null
        defaultApplicantAddressShouldNotBeFound("addressType.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByThanaOtherIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where thanaOther equals to DEFAULT_THANA_OTHER
        defaultApplicantAddressShouldBeFound("thanaOther.equals=" + DEFAULT_THANA_OTHER);

        // Get all the applicantAddressList where thanaOther equals to UPDATED_THANA_OTHER
        defaultApplicantAddressShouldNotBeFound("thanaOther.equals=" + UPDATED_THANA_OTHER);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByThanaOtherIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where thanaOther not equals to DEFAULT_THANA_OTHER
        defaultApplicantAddressShouldNotBeFound("thanaOther.notEquals=" + DEFAULT_THANA_OTHER);

        // Get all the applicantAddressList where thanaOther not equals to UPDATED_THANA_OTHER
        defaultApplicantAddressShouldBeFound("thanaOther.notEquals=" + UPDATED_THANA_OTHER);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByThanaOtherIsInShouldWork() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where thanaOther in DEFAULT_THANA_OTHER or UPDATED_THANA_OTHER
        defaultApplicantAddressShouldBeFound("thanaOther.in=" + DEFAULT_THANA_OTHER + "," + UPDATED_THANA_OTHER);

        // Get all the applicantAddressList where thanaOther equals to UPDATED_THANA_OTHER
        defaultApplicantAddressShouldNotBeFound("thanaOther.in=" + UPDATED_THANA_OTHER);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByThanaOtherIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where thanaOther is not null
        defaultApplicantAddressShouldBeFound("thanaOther.specified=true");

        // Get all the applicantAddressList where thanaOther is null
        defaultApplicantAddressShouldNotBeFound("thanaOther.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicantAddressesByThanaOtherContainsSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where thanaOther contains DEFAULT_THANA_OTHER
        defaultApplicantAddressShouldBeFound("thanaOther.contains=" + DEFAULT_THANA_OTHER);

        // Get all the applicantAddressList where thanaOther contains UPDATED_THANA_OTHER
        defaultApplicantAddressShouldNotBeFound("thanaOther.contains=" + UPDATED_THANA_OTHER);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByThanaOtherNotContainsSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where thanaOther does not contain DEFAULT_THANA_OTHER
        defaultApplicantAddressShouldNotBeFound("thanaOther.doesNotContain=" + DEFAULT_THANA_OTHER);

        // Get all the applicantAddressList where thanaOther does not contain UPDATED_THANA_OTHER
        defaultApplicantAddressShouldBeFound("thanaOther.doesNotContain=" + UPDATED_THANA_OTHER);
    }


    @Test
    @Transactional
    public void getAllApplicantAddressesByLine1IsEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where line1 equals to DEFAULT_LINE_1
        defaultApplicantAddressShouldBeFound("line1.equals=" + DEFAULT_LINE_1);

        // Get all the applicantAddressList where line1 equals to UPDATED_LINE_1
        defaultApplicantAddressShouldNotBeFound("line1.equals=" + UPDATED_LINE_1);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByLine1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where line1 not equals to DEFAULT_LINE_1
        defaultApplicantAddressShouldNotBeFound("line1.notEquals=" + DEFAULT_LINE_1);

        // Get all the applicantAddressList where line1 not equals to UPDATED_LINE_1
        defaultApplicantAddressShouldBeFound("line1.notEquals=" + UPDATED_LINE_1);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByLine1IsInShouldWork() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where line1 in DEFAULT_LINE_1 or UPDATED_LINE_1
        defaultApplicantAddressShouldBeFound("line1.in=" + DEFAULT_LINE_1 + "," + UPDATED_LINE_1);

        // Get all the applicantAddressList where line1 equals to UPDATED_LINE_1
        defaultApplicantAddressShouldNotBeFound("line1.in=" + UPDATED_LINE_1);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByLine1IsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where line1 is not null
        defaultApplicantAddressShouldBeFound("line1.specified=true");

        // Get all the applicantAddressList where line1 is null
        defaultApplicantAddressShouldNotBeFound("line1.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicantAddressesByLine1ContainsSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where line1 contains DEFAULT_LINE_1
        defaultApplicantAddressShouldBeFound("line1.contains=" + DEFAULT_LINE_1);

        // Get all the applicantAddressList where line1 contains UPDATED_LINE_1
        defaultApplicantAddressShouldNotBeFound("line1.contains=" + UPDATED_LINE_1);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByLine1NotContainsSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where line1 does not contain DEFAULT_LINE_1
        defaultApplicantAddressShouldNotBeFound("line1.doesNotContain=" + DEFAULT_LINE_1);

        // Get all the applicantAddressList where line1 does not contain UPDATED_LINE_1
        defaultApplicantAddressShouldBeFound("line1.doesNotContain=" + UPDATED_LINE_1);
    }


    @Test
    @Transactional
    public void getAllApplicantAddressesByLine2IsEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where line2 equals to DEFAULT_LINE_2
        defaultApplicantAddressShouldBeFound("line2.equals=" + DEFAULT_LINE_2);

        // Get all the applicantAddressList where line2 equals to UPDATED_LINE_2
        defaultApplicantAddressShouldNotBeFound("line2.equals=" + UPDATED_LINE_2);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByLine2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where line2 not equals to DEFAULT_LINE_2
        defaultApplicantAddressShouldNotBeFound("line2.notEquals=" + DEFAULT_LINE_2);

        // Get all the applicantAddressList where line2 not equals to UPDATED_LINE_2
        defaultApplicantAddressShouldBeFound("line2.notEquals=" + UPDATED_LINE_2);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByLine2IsInShouldWork() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where line2 in DEFAULT_LINE_2 or UPDATED_LINE_2
        defaultApplicantAddressShouldBeFound("line2.in=" + DEFAULT_LINE_2 + "," + UPDATED_LINE_2);

        // Get all the applicantAddressList where line2 equals to UPDATED_LINE_2
        defaultApplicantAddressShouldNotBeFound("line2.in=" + UPDATED_LINE_2);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByLine2IsNullOrNotNull() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where line2 is not null
        defaultApplicantAddressShouldBeFound("line2.specified=true");

        // Get all the applicantAddressList where line2 is null
        defaultApplicantAddressShouldNotBeFound("line2.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicantAddressesByLine2ContainsSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where line2 contains DEFAULT_LINE_2
        defaultApplicantAddressShouldBeFound("line2.contains=" + DEFAULT_LINE_2);

        // Get all the applicantAddressList where line2 contains UPDATED_LINE_2
        defaultApplicantAddressShouldNotBeFound("line2.contains=" + UPDATED_LINE_2);
    }

    @Test
    @Transactional
    public void getAllApplicantAddressesByLine2NotContainsSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);

        // Get all the applicantAddressList where line2 does not contain DEFAULT_LINE_2
        defaultApplicantAddressShouldNotBeFound("line2.doesNotContain=" + DEFAULT_LINE_2);

        // Get all the applicantAddressList where line2 does not contain UPDATED_LINE_2
        defaultApplicantAddressShouldBeFound("line2.doesNotContain=" + UPDATED_LINE_2);
    }


    @Test
    @Transactional
    public void getAllApplicantAddressesByDistrictIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);
        District district = DistrictResourceIT.createEntity(em);
        em.persist(district);
        em.flush();
        applicantAddress.setDistrict(district);
        applicantAddressRepository.saveAndFlush(applicantAddress);
        Long districtId = district.getId();

        // Get all the applicantAddressList where district equals to districtId
        defaultApplicantAddressShouldBeFound("districtId.equals=" + districtId);

        // Get all the applicantAddressList where district equals to districtId + 1
        defaultApplicantAddressShouldNotBeFound("districtId.equals=" + (districtId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicantAddressesByDivisionIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);
        Division division = DivisionResourceIT.createEntity(em);
        em.persist(division);
        em.flush();
        applicantAddress.setDivision(division);
        applicantAddressRepository.saveAndFlush(applicantAddress);
        Long divisionId = division.getId();

        // Get all the applicantAddressList where division equals to divisionId
        defaultApplicantAddressShouldBeFound("divisionId.equals=" + divisionId);

        // Get all the applicantAddressList where division equals to divisionId + 1
        defaultApplicantAddressShouldNotBeFound("divisionId.equals=" + (divisionId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicantAddressesByThanaIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);
        Thana thana = ThanaResourceIT.createEntity(em);
        em.persist(thana);
        em.flush();
        applicantAddress.setThana(thana);
        applicantAddressRepository.saveAndFlush(applicantAddress);
        Long thanaId = thana.getId();

        // Get all the applicantAddressList where thana equals to thanaId
        defaultApplicantAddressShouldBeFound("thanaId.equals=" + thanaId);

        // Get all the applicantAddressList where thana equals to thanaId + 1
        defaultApplicantAddressShouldNotBeFound("thanaId.equals=" + (thanaId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicantAddressesByApplicantIsEqualToSomething() throws Exception {
        // Initialize the database
        applicantAddressRepository.saveAndFlush(applicantAddress);
        Applicant applicant = ApplicantResourceIT.createEntity(em);
        em.persist(applicant);
        em.flush();
        applicantAddress.setApplicant(applicant);
        applicantAddressRepository.saveAndFlush(applicantAddress);
        Long applicantId = applicant.getId();

        // Get all the applicantAddressList where applicant equals to applicantId
        defaultApplicantAddressShouldBeFound("applicantId.equals=" + applicantId);

        // Get all the applicantAddressList where applicant equals to applicantId + 1
        defaultApplicantAddressShouldNotBeFound("applicantId.equals=" + (applicantId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicantAddressShouldBeFound(String filter) throws Exception {
        restApplicantAddressMockMvc.perform(get("/api/applicant-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicantAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicationSerial").value(hasItem(DEFAULT_APPLICATION_SERIAL)))
            .andExpect(jsonPath("$.[*].addressType").value(hasItem(DEFAULT_ADDRESS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].thanaOther").value(hasItem(DEFAULT_THANA_OTHER)))
            .andExpect(jsonPath("$.[*].line1").value(hasItem(DEFAULT_LINE_1)))
            .andExpect(jsonPath("$.[*].line2").value(hasItem(DEFAULT_LINE_2)));

        // Check, that the count call also returns 1
        restApplicantAddressMockMvc.perform(get("/api/applicant-addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicantAddressShouldNotBeFound(String filter) throws Exception {
        restApplicantAddressMockMvc.perform(get("/api/applicant-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicantAddressMockMvc.perform(get("/api/applicant-addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingApplicantAddress() throws Exception {
        // Get the applicantAddress
        restApplicantAddressMockMvc.perform(get("/api/applicant-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicantAddress() throws Exception {
        // Initialize the database
        applicantAddressService.save(applicantAddress);

        int databaseSizeBeforeUpdate = applicantAddressRepository.findAll().size();

        // Update the applicantAddress
        ApplicantAddress updatedApplicantAddress = applicantAddressRepository.findById(applicantAddress.getId()).get();
        // Disconnect from session so that the updates on updatedApplicantAddress are not directly saved in db
        em.detach(updatedApplicantAddress);
        updatedApplicantAddress
            .applicationSerial(UPDATED_APPLICATION_SERIAL)
            .addressType(UPDATED_ADDRESS_TYPE)
            .thanaOther(UPDATED_THANA_OTHER)
            .line1(UPDATED_LINE_1)
            .line2(UPDATED_LINE_2);

        restApplicantAddressMockMvc.perform(put("/api/applicant-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplicantAddress)))
            .andExpect(status().isOk());

        // Validate the ApplicantAddress in the database
        List<ApplicantAddress> applicantAddressList = applicantAddressRepository.findAll();
        assertThat(applicantAddressList).hasSize(databaseSizeBeforeUpdate);
        ApplicantAddress testApplicantAddress = applicantAddressList.get(applicantAddressList.size() - 1);
        assertThat(testApplicantAddress.getApplicationSerial()).isEqualTo(UPDATED_APPLICATION_SERIAL);
        assertThat(testApplicantAddress.getAddressType()).isEqualTo(UPDATED_ADDRESS_TYPE);
        assertThat(testApplicantAddress.getThanaOther()).isEqualTo(UPDATED_THANA_OTHER);
        assertThat(testApplicantAddress.getLine1()).isEqualTo(UPDATED_LINE_1);
        assertThat(testApplicantAddress.getLine2()).isEqualTo(UPDATED_LINE_2);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicantAddress() throws Exception {
        int databaseSizeBeforeUpdate = applicantAddressRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicantAddressMockMvc.perform(put("/api/applicant-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicantAddress)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicantAddress in the database
        List<ApplicantAddress> applicantAddressList = applicantAddressRepository.findAll();
        assertThat(applicantAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicantAddress() throws Exception {
        // Initialize the database
        applicantAddressService.save(applicantAddress);

        int databaseSizeBeforeDelete = applicantAddressRepository.findAll().size();

        // Delete the applicantAddress
        restApplicantAddressMockMvc.perform(delete("/api/applicant-addresses/{id}", applicantAddress.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicantAddress> applicantAddressList = applicantAddressRepository.findAll();
        assertThat(applicantAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
