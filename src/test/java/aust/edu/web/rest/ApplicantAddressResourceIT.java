package aust.edu.web.rest;

import aust.edu.PgadmissionApp;
import aust.edu.domain.ApplicantAddress;
import aust.edu.repository.ApplicantAddressRepository;
import aust.edu.service.ApplicantAddressService;

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
