package aust.edu.web.rest;

import aust.edu.PgadmissionApp;
import aust.edu.domain.Program;
import aust.edu.repository.ProgramRepository;
import aust.edu.service.ProgramService;
import aust.edu.service.dto.ProgramCriteria;
import aust.edu.service.ProgramQueryService;

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
 * Integration tests for the {@link ProgramResource} REST controller.
 */
@SpringBootTest(classes = PgadmissionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProgramResourceIT {

    private static final String DEFAULT_PROGRAM_NAME_SHORT = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAM_NAME_SHORT = "BBBBBBBBBB";

    private static final String DEFAULT_PROGRAM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAM_NAME = "BBBBBBBBBB";

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private ProgramService programService;

    @Autowired
    private ProgramQueryService programQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgramMockMvc;

    private Program program;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Program createEntity(EntityManager em) {
        Program program = new Program()
            .programNameShort(DEFAULT_PROGRAM_NAME_SHORT)
            .programName(DEFAULT_PROGRAM_NAME);
        return program;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Program createUpdatedEntity(EntityManager em) {
        Program program = new Program()
            .programNameShort(UPDATED_PROGRAM_NAME_SHORT)
            .programName(UPDATED_PROGRAM_NAME);
        return program;
    }

    @BeforeEach
    public void initTest() {
        program = createEntity(em);
    }

    @Test
    @Transactional
    public void createProgram() throws Exception {
        int databaseSizeBeforeCreate = programRepository.findAll().size();
        // Create the Program
        restProgramMockMvc.perform(post("/api/programs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(program)))
            .andExpect(status().isCreated());

        // Validate the Program in the database
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeCreate + 1);
        Program testProgram = programList.get(programList.size() - 1);
        assertThat(testProgram.getProgramNameShort()).isEqualTo(DEFAULT_PROGRAM_NAME_SHORT);
        assertThat(testProgram.getProgramName()).isEqualTo(DEFAULT_PROGRAM_NAME);
    }

    @Test
    @Transactional
    public void createProgramWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = programRepository.findAll().size();

        // Create the Program with an existing ID
        program.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramMockMvc.perform(post("/api/programs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(program)))
            .andExpect(status().isBadRequest());

        // Validate the Program in the database
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProgramNameShortIsRequired() throws Exception {
        int databaseSizeBeforeTest = programRepository.findAll().size();
        // set the field null
        program.setProgramNameShort(null);

        // Create the Program, which fails.


        restProgramMockMvc.perform(post("/api/programs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(program)))
            .andExpect(status().isBadRequest());

        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProgramNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = programRepository.findAll().size();
        // set the field null
        program.setProgramName(null);

        // Create the Program, which fails.


        restProgramMockMvc.perform(post("/api/programs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(program)))
            .andExpect(status().isBadRequest());

        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrograms() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get all the programList
        restProgramMockMvc.perform(get("/api/programs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(program.getId().intValue())))
            .andExpect(jsonPath("$.[*].programNameShort").value(hasItem(DEFAULT_PROGRAM_NAME_SHORT)))
            .andExpect(jsonPath("$.[*].programName").value(hasItem(DEFAULT_PROGRAM_NAME)));
    }
    
    @Test
    @Transactional
    public void getProgram() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get the program
        restProgramMockMvc.perform(get("/api/programs/{id}", program.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(program.getId().intValue()))
            .andExpect(jsonPath("$.programNameShort").value(DEFAULT_PROGRAM_NAME_SHORT))
            .andExpect(jsonPath("$.programName").value(DEFAULT_PROGRAM_NAME));
    }


    @Test
    @Transactional
    public void getProgramsByIdFiltering() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        Long id = program.getId();

        defaultProgramShouldBeFound("id.equals=" + id);
        defaultProgramShouldNotBeFound("id.notEquals=" + id);

        defaultProgramShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProgramShouldNotBeFound("id.greaterThan=" + id);

        defaultProgramShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProgramShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProgramsByProgramNameShortIsEqualToSomething() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get all the programList where programNameShort equals to DEFAULT_PROGRAM_NAME_SHORT
        defaultProgramShouldBeFound("programNameShort.equals=" + DEFAULT_PROGRAM_NAME_SHORT);

        // Get all the programList where programNameShort equals to UPDATED_PROGRAM_NAME_SHORT
        defaultProgramShouldNotBeFound("programNameShort.equals=" + UPDATED_PROGRAM_NAME_SHORT);
    }

    @Test
    @Transactional
    public void getAllProgramsByProgramNameShortIsNotEqualToSomething() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get all the programList where programNameShort not equals to DEFAULT_PROGRAM_NAME_SHORT
        defaultProgramShouldNotBeFound("programNameShort.notEquals=" + DEFAULT_PROGRAM_NAME_SHORT);

        // Get all the programList where programNameShort not equals to UPDATED_PROGRAM_NAME_SHORT
        defaultProgramShouldBeFound("programNameShort.notEquals=" + UPDATED_PROGRAM_NAME_SHORT);
    }

    @Test
    @Transactional
    public void getAllProgramsByProgramNameShortIsInShouldWork() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get all the programList where programNameShort in DEFAULT_PROGRAM_NAME_SHORT or UPDATED_PROGRAM_NAME_SHORT
        defaultProgramShouldBeFound("programNameShort.in=" + DEFAULT_PROGRAM_NAME_SHORT + "," + UPDATED_PROGRAM_NAME_SHORT);

        // Get all the programList where programNameShort equals to UPDATED_PROGRAM_NAME_SHORT
        defaultProgramShouldNotBeFound("programNameShort.in=" + UPDATED_PROGRAM_NAME_SHORT);
    }

    @Test
    @Transactional
    public void getAllProgramsByProgramNameShortIsNullOrNotNull() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get all the programList where programNameShort is not null
        defaultProgramShouldBeFound("programNameShort.specified=true");

        // Get all the programList where programNameShort is null
        defaultProgramShouldNotBeFound("programNameShort.specified=false");
    }
                @Test
    @Transactional
    public void getAllProgramsByProgramNameShortContainsSomething() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get all the programList where programNameShort contains DEFAULT_PROGRAM_NAME_SHORT
        defaultProgramShouldBeFound("programNameShort.contains=" + DEFAULT_PROGRAM_NAME_SHORT);

        // Get all the programList where programNameShort contains UPDATED_PROGRAM_NAME_SHORT
        defaultProgramShouldNotBeFound("programNameShort.contains=" + UPDATED_PROGRAM_NAME_SHORT);
    }

    @Test
    @Transactional
    public void getAllProgramsByProgramNameShortNotContainsSomething() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get all the programList where programNameShort does not contain DEFAULT_PROGRAM_NAME_SHORT
        defaultProgramShouldNotBeFound("programNameShort.doesNotContain=" + DEFAULT_PROGRAM_NAME_SHORT);

        // Get all the programList where programNameShort does not contain UPDATED_PROGRAM_NAME_SHORT
        defaultProgramShouldBeFound("programNameShort.doesNotContain=" + UPDATED_PROGRAM_NAME_SHORT);
    }


    @Test
    @Transactional
    public void getAllProgramsByProgramNameIsEqualToSomething() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get all the programList where programName equals to DEFAULT_PROGRAM_NAME
        defaultProgramShouldBeFound("programName.equals=" + DEFAULT_PROGRAM_NAME);

        // Get all the programList where programName equals to UPDATED_PROGRAM_NAME
        defaultProgramShouldNotBeFound("programName.equals=" + UPDATED_PROGRAM_NAME);
    }

    @Test
    @Transactional
    public void getAllProgramsByProgramNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get all the programList where programName not equals to DEFAULT_PROGRAM_NAME
        defaultProgramShouldNotBeFound("programName.notEquals=" + DEFAULT_PROGRAM_NAME);

        // Get all the programList where programName not equals to UPDATED_PROGRAM_NAME
        defaultProgramShouldBeFound("programName.notEquals=" + UPDATED_PROGRAM_NAME);
    }

    @Test
    @Transactional
    public void getAllProgramsByProgramNameIsInShouldWork() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get all the programList where programName in DEFAULT_PROGRAM_NAME or UPDATED_PROGRAM_NAME
        defaultProgramShouldBeFound("programName.in=" + DEFAULT_PROGRAM_NAME + "," + UPDATED_PROGRAM_NAME);

        // Get all the programList where programName equals to UPDATED_PROGRAM_NAME
        defaultProgramShouldNotBeFound("programName.in=" + UPDATED_PROGRAM_NAME);
    }

    @Test
    @Transactional
    public void getAllProgramsByProgramNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get all the programList where programName is not null
        defaultProgramShouldBeFound("programName.specified=true");

        // Get all the programList where programName is null
        defaultProgramShouldNotBeFound("programName.specified=false");
    }
                @Test
    @Transactional
    public void getAllProgramsByProgramNameContainsSomething() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get all the programList where programName contains DEFAULT_PROGRAM_NAME
        defaultProgramShouldBeFound("programName.contains=" + DEFAULT_PROGRAM_NAME);

        // Get all the programList where programName contains UPDATED_PROGRAM_NAME
        defaultProgramShouldNotBeFound("programName.contains=" + UPDATED_PROGRAM_NAME);
    }

    @Test
    @Transactional
    public void getAllProgramsByProgramNameNotContainsSomething() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get all the programList where programName does not contain DEFAULT_PROGRAM_NAME
        defaultProgramShouldNotBeFound("programName.doesNotContain=" + DEFAULT_PROGRAM_NAME);

        // Get all the programList where programName does not contain UPDATED_PROGRAM_NAME
        defaultProgramShouldBeFound("programName.doesNotContain=" + UPDATED_PROGRAM_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProgramShouldBeFound(String filter) throws Exception {
        restProgramMockMvc.perform(get("/api/programs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(program.getId().intValue())))
            .andExpect(jsonPath("$.[*].programNameShort").value(hasItem(DEFAULT_PROGRAM_NAME_SHORT)))
            .andExpect(jsonPath("$.[*].programName").value(hasItem(DEFAULT_PROGRAM_NAME)));

        // Check, that the count call also returns 1
        restProgramMockMvc.perform(get("/api/programs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProgramShouldNotBeFound(String filter) throws Exception {
        restProgramMockMvc.perform(get("/api/programs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProgramMockMvc.perform(get("/api/programs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProgram() throws Exception {
        // Get the program
        restProgramMockMvc.perform(get("/api/programs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProgram() throws Exception {
        // Initialize the database
        programService.save(program);

        int databaseSizeBeforeUpdate = programRepository.findAll().size();

        // Update the program
        Program updatedProgram = programRepository.findById(program.getId()).get();
        // Disconnect from session so that the updates on updatedProgram are not directly saved in db
        em.detach(updatedProgram);
        updatedProgram
            .programNameShort(UPDATED_PROGRAM_NAME_SHORT)
            .programName(UPDATED_PROGRAM_NAME);

        restProgramMockMvc.perform(put("/api/programs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProgram)))
            .andExpect(status().isOk());

        // Validate the Program in the database
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeUpdate);
        Program testProgram = programList.get(programList.size() - 1);
        assertThat(testProgram.getProgramNameShort()).isEqualTo(UPDATED_PROGRAM_NAME_SHORT);
        assertThat(testProgram.getProgramName()).isEqualTo(UPDATED_PROGRAM_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProgram() throws Exception {
        int databaseSizeBeforeUpdate = programRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramMockMvc.perform(put("/api/programs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(program)))
            .andExpect(status().isBadRequest());

        // Validate the Program in the database
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProgram() throws Exception {
        // Initialize the database
        programService.save(program);

        int databaseSizeBeforeDelete = programRepository.findAll().size();

        // Delete the program
        restProgramMockMvc.perform(delete("/api/programs/{id}", program.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
