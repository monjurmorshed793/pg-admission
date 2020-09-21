package aust.edu.web.rest;

import aust.edu.PgadmissionApp;
import aust.edu.domain.ApplicationDeadline;
import aust.edu.domain.Semester;
import aust.edu.domain.Program;
import aust.edu.repository.ApplicationDeadlineRepository;
import aust.edu.service.ApplicationDeadlineService;

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

/**
 * Integration tests for the {@link ApplicationDeadlineResource} REST controller.
 */
@SpringBootTest(classes = PgadmissionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ApplicationDeadlineResourceIT {

    private static final Instant DEFAULT_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TO_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TO_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ApplicationDeadlineRepository applicationDeadlineRepository;

    @Autowired
    private ApplicationDeadlineService applicationDeadlineService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationDeadlineMockMvc;

    private ApplicationDeadline applicationDeadline;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationDeadline createEntity(EntityManager em) {
        ApplicationDeadline applicationDeadline = new ApplicationDeadline()
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TO_DATE);
        // Add required entity
        Semester semester;
        if (TestUtil.findAll(em, Semester.class).isEmpty()) {
            semester = SemesterResourceIT.createEntity(em);
            em.persist(semester);
            em.flush();
        } else {
            semester = TestUtil.findAll(em, Semester.class).get(0);
        }
        applicationDeadline.setSemester(semester);
        // Add required entity
        Program program;
        if (TestUtil.findAll(em, Program.class).isEmpty()) {
            program = ProgramResourceIT.createEntity(em);
            em.persist(program);
            em.flush();
        } else {
            program = TestUtil.findAll(em, Program.class).get(0);
        }
        applicationDeadline.setProgram(program);
        return applicationDeadline;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationDeadline createUpdatedEntity(EntityManager em) {
        ApplicationDeadline applicationDeadline = new ApplicationDeadline()
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE);
        // Add required entity
        Semester semester;
        if (TestUtil.findAll(em, Semester.class).isEmpty()) {
            semester = SemesterResourceIT.createUpdatedEntity(em);
            em.persist(semester);
            em.flush();
        } else {
            semester = TestUtil.findAll(em, Semester.class).get(0);
        }
        applicationDeadline.setSemester(semester);
        // Add required entity
        Program program;
        if (TestUtil.findAll(em, Program.class).isEmpty()) {
            program = ProgramResourceIT.createUpdatedEntity(em);
            em.persist(program);
            em.flush();
        } else {
            program = TestUtil.findAll(em, Program.class).get(0);
        }
        applicationDeadline.setProgram(program);
        return applicationDeadline;
    }

    @BeforeEach
    public void initTest() {
        applicationDeadline = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationDeadline() throws Exception {
        int databaseSizeBeforeCreate = applicationDeadlineRepository.findAll().size();
        // Create the ApplicationDeadline
        restApplicationDeadlineMockMvc.perform(post("/api/application-deadlines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDeadline)))
            .andExpect(status().isCreated());

        // Validate the ApplicationDeadline in the database
        List<ApplicationDeadline> applicationDeadlineList = applicationDeadlineRepository.findAll();
        assertThat(applicationDeadlineList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationDeadline testApplicationDeadline = applicationDeadlineList.get(applicationDeadlineList.size() - 1);
        assertThat(testApplicationDeadline.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testApplicationDeadline.getToDate()).isEqualTo(DEFAULT_TO_DATE);
    }

    @Test
    @Transactional
    public void createApplicationDeadlineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationDeadlineRepository.findAll().size();

        // Create the ApplicationDeadline with an existing ID
        applicationDeadline.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationDeadlineMockMvc.perform(post("/api/application-deadlines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDeadline)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationDeadline in the database
        List<ApplicationDeadline> applicationDeadlineList = applicationDeadlineRepository.findAll();
        assertThat(applicationDeadlineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFromDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationDeadlineRepository.findAll().size();
        // set the field null
        applicationDeadline.setFromDate(null);

        // Create the ApplicationDeadline, which fails.


        restApplicationDeadlineMockMvc.perform(post("/api/application-deadlines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDeadline)))
            .andExpect(status().isBadRequest());

        List<ApplicationDeadline> applicationDeadlineList = applicationDeadlineRepository.findAll();
        assertThat(applicationDeadlineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkToDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationDeadlineRepository.findAll().size();
        // set the field null
        applicationDeadline.setToDate(null);

        // Create the ApplicationDeadline, which fails.


        restApplicationDeadlineMockMvc.perform(post("/api/application-deadlines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDeadline)))
            .andExpect(status().isBadRequest());

        List<ApplicationDeadline> applicationDeadlineList = applicationDeadlineRepository.findAll();
        assertThat(applicationDeadlineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationDeadlines() throws Exception {
        // Initialize the database
        applicationDeadlineRepository.saveAndFlush(applicationDeadline);

        // Get all the applicationDeadlineList
        restApplicationDeadlineMockMvc.perform(get("/api/application-deadlines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationDeadline.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getApplicationDeadline() throws Exception {
        // Initialize the database
        applicationDeadlineRepository.saveAndFlush(applicationDeadline);

        // Get the applicationDeadline
        restApplicationDeadlineMockMvc.perform(get("/api/application-deadlines/{id}", applicationDeadline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationDeadline.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingApplicationDeadline() throws Exception {
        // Get the applicationDeadline
        restApplicationDeadlineMockMvc.perform(get("/api/application-deadlines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationDeadline() throws Exception {
        // Initialize the database
        applicationDeadlineService.save(applicationDeadline);

        int databaseSizeBeforeUpdate = applicationDeadlineRepository.findAll().size();

        // Update the applicationDeadline
        ApplicationDeadline updatedApplicationDeadline = applicationDeadlineRepository.findById(applicationDeadline.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationDeadline are not directly saved in db
        em.detach(updatedApplicationDeadline);
        updatedApplicationDeadline
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE);

        restApplicationDeadlineMockMvc.perform(put("/api/application-deadlines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplicationDeadline)))
            .andExpect(status().isOk());

        // Validate the ApplicationDeadline in the database
        List<ApplicationDeadline> applicationDeadlineList = applicationDeadlineRepository.findAll();
        assertThat(applicationDeadlineList).hasSize(databaseSizeBeforeUpdate);
        ApplicationDeadline testApplicationDeadline = applicationDeadlineList.get(applicationDeadlineList.size() - 1);
        assertThat(testApplicationDeadline.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testApplicationDeadline.getToDate()).isEqualTo(UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationDeadline() throws Exception {
        int databaseSizeBeforeUpdate = applicationDeadlineRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationDeadlineMockMvc.perform(put("/api/application-deadlines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDeadline)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationDeadline in the database
        List<ApplicationDeadline> applicationDeadlineList = applicationDeadlineRepository.findAll();
        assertThat(applicationDeadlineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationDeadline() throws Exception {
        // Initialize the database
        applicationDeadlineService.save(applicationDeadline);

        int databaseSizeBeforeDelete = applicationDeadlineRepository.findAll().size();

        // Delete the applicationDeadline
        restApplicationDeadlineMockMvc.perform(delete("/api/application-deadlines/{id}", applicationDeadline.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationDeadline> applicationDeadlineList = applicationDeadlineRepository.findAll();
        assertThat(applicationDeadlineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
