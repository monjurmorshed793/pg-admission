package aust.edu.web.rest;

import aust.edu.PgadmissionApp;
import aust.edu.domain.ExamType;
import aust.edu.repository.ExamTypeRepository;
import aust.edu.service.ExamTypeService;
import aust.edu.service.dto.ExamTypeCriteria;
import aust.edu.service.ExamTypeQueryService;

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
 * Integration tests for the {@link ExamTypeResource} REST controller.
 */
@SpringBootTest(classes = PgadmissionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExamTypeResourceIT {

    private static final String DEFAULT_EXAM_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_EXAM_TYPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_EXAM_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EXAM_TYPE_NAME = "BBBBBBBBBB";

    @Autowired
    private ExamTypeRepository examTypeRepository;

    @Autowired
    private ExamTypeService examTypeService;

    @Autowired
    private ExamTypeQueryService examTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExamTypeMockMvc;

    private ExamType examType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamType createEntity(EntityManager em) {
        ExamType examType = new ExamType()
            .examTypeCode(DEFAULT_EXAM_TYPE_CODE)
            .examTypeName(DEFAULT_EXAM_TYPE_NAME);
        return examType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamType createUpdatedEntity(EntityManager em) {
        ExamType examType = new ExamType()
            .examTypeCode(UPDATED_EXAM_TYPE_CODE)
            .examTypeName(UPDATED_EXAM_TYPE_NAME);
        return examType;
    }

    @BeforeEach
    public void initTest() {
        examType = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamType() throws Exception {
        int databaseSizeBeforeCreate = examTypeRepository.findAll().size();
        // Create the ExamType
        restExamTypeMockMvc.perform(post("/api/exam-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examType)))
            .andExpect(status().isCreated());

        // Validate the ExamType in the database
        List<ExamType> examTypeList = examTypeRepository.findAll();
        assertThat(examTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ExamType testExamType = examTypeList.get(examTypeList.size() - 1);
        assertThat(testExamType.getExamTypeCode()).isEqualTo(DEFAULT_EXAM_TYPE_CODE);
        assertThat(testExamType.getExamTypeName()).isEqualTo(DEFAULT_EXAM_TYPE_NAME);
    }

    @Test
    @Transactional
    public void createExamTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examTypeRepository.findAll().size();

        // Create the ExamType with an existing ID
        examType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamTypeMockMvc.perform(post("/api/exam-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examType)))
            .andExpect(status().isBadRequest());

        // Validate the ExamType in the database
        List<ExamType> examTypeList = examTypeRepository.findAll();
        assertThat(examTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkExamTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = examTypeRepository.findAll().size();
        // set the field null
        examType.setExamTypeCode(null);

        // Create the ExamType, which fails.


        restExamTypeMockMvc.perform(post("/api/exam-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examType)))
            .andExpect(status().isBadRequest());

        List<ExamType> examTypeList = examTypeRepository.findAll();
        assertThat(examTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExamTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = examTypeRepository.findAll().size();
        // set the field null
        examType.setExamTypeName(null);

        // Create the ExamType, which fails.


        restExamTypeMockMvc.perform(post("/api/exam-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examType)))
            .andExpect(status().isBadRequest());

        List<ExamType> examTypeList = examTypeRepository.findAll();
        assertThat(examTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExamTypes() throws Exception {
        // Initialize the database
        examTypeRepository.saveAndFlush(examType);

        // Get all the examTypeList
        restExamTypeMockMvc.perform(get("/api/exam-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examType.getId().intValue())))
            .andExpect(jsonPath("$.[*].examTypeCode").value(hasItem(DEFAULT_EXAM_TYPE_CODE)))
            .andExpect(jsonPath("$.[*].examTypeName").value(hasItem(DEFAULT_EXAM_TYPE_NAME)));
    }
    
    @Test
    @Transactional
    public void getExamType() throws Exception {
        // Initialize the database
        examTypeRepository.saveAndFlush(examType);

        // Get the examType
        restExamTypeMockMvc.perform(get("/api/exam-types/{id}", examType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(examType.getId().intValue()))
            .andExpect(jsonPath("$.examTypeCode").value(DEFAULT_EXAM_TYPE_CODE))
            .andExpect(jsonPath("$.examTypeName").value(DEFAULT_EXAM_TYPE_NAME));
    }


    @Test
    @Transactional
    public void getExamTypesByIdFiltering() throws Exception {
        // Initialize the database
        examTypeRepository.saveAndFlush(examType);

        Long id = examType.getId();

        defaultExamTypeShouldBeFound("id.equals=" + id);
        defaultExamTypeShouldNotBeFound("id.notEquals=" + id);

        defaultExamTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultExamTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultExamTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultExamTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllExamTypesByExamTypeCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        examTypeRepository.saveAndFlush(examType);

        // Get all the examTypeList where examTypeCode equals to DEFAULT_EXAM_TYPE_CODE
        defaultExamTypeShouldBeFound("examTypeCode.equals=" + DEFAULT_EXAM_TYPE_CODE);

        // Get all the examTypeList where examTypeCode equals to UPDATED_EXAM_TYPE_CODE
        defaultExamTypeShouldNotBeFound("examTypeCode.equals=" + UPDATED_EXAM_TYPE_CODE);
    }

    @Test
    @Transactional
    public void getAllExamTypesByExamTypeCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        examTypeRepository.saveAndFlush(examType);

        // Get all the examTypeList where examTypeCode not equals to DEFAULT_EXAM_TYPE_CODE
        defaultExamTypeShouldNotBeFound("examTypeCode.notEquals=" + DEFAULT_EXAM_TYPE_CODE);

        // Get all the examTypeList where examTypeCode not equals to UPDATED_EXAM_TYPE_CODE
        defaultExamTypeShouldBeFound("examTypeCode.notEquals=" + UPDATED_EXAM_TYPE_CODE);
    }

    @Test
    @Transactional
    public void getAllExamTypesByExamTypeCodeIsInShouldWork() throws Exception {
        // Initialize the database
        examTypeRepository.saveAndFlush(examType);

        // Get all the examTypeList where examTypeCode in DEFAULT_EXAM_TYPE_CODE or UPDATED_EXAM_TYPE_CODE
        defaultExamTypeShouldBeFound("examTypeCode.in=" + DEFAULT_EXAM_TYPE_CODE + "," + UPDATED_EXAM_TYPE_CODE);

        // Get all the examTypeList where examTypeCode equals to UPDATED_EXAM_TYPE_CODE
        defaultExamTypeShouldNotBeFound("examTypeCode.in=" + UPDATED_EXAM_TYPE_CODE);
    }

    @Test
    @Transactional
    public void getAllExamTypesByExamTypeCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        examTypeRepository.saveAndFlush(examType);

        // Get all the examTypeList where examTypeCode is not null
        defaultExamTypeShouldBeFound("examTypeCode.specified=true");

        // Get all the examTypeList where examTypeCode is null
        defaultExamTypeShouldNotBeFound("examTypeCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllExamTypesByExamTypeCodeContainsSomething() throws Exception {
        // Initialize the database
        examTypeRepository.saveAndFlush(examType);

        // Get all the examTypeList where examTypeCode contains DEFAULT_EXAM_TYPE_CODE
        defaultExamTypeShouldBeFound("examTypeCode.contains=" + DEFAULT_EXAM_TYPE_CODE);

        // Get all the examTypeList where examTypeCode contains UPDATED_EXAM_TYPE_CODE
        defaultExamTypeShouldNotBeFound("examTypeCode.contains=" + UPDATED_EXAM_TYPE_CODE);
    }

    @Test
    @Transactional
    public void getAllExamTypesByExamTypeCodeNotContainsSomething() throws Exception {
        // Initialize the database
        examTypeRepository.saveAndFlush(examType);

        // Get all the examTypeList where examTypeCode does not contain DEFAULT_EXAM_TYPE_CODE
        defaultExamTypeShouldNotBeFound("examTypeCode.doesNotContain=" + DEFAULT_EXAM_TYPE_CODE);

        // Get all the examTypeList where examTypeCode does not contain UPDATED_EXAM_TYPE_CODE
        defaultExamTypeShouldBeFound("examTypeCode.doesNotContain=" + UPDATED_EXAM_TYPE_CODE);
    }


    @Test
    @Transactional
    public void getAllExamTypesByExamTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        examTypeRepository.saveAndFlush(examType);

        // Get all the examTypeList where examTypeName equals to DEFAULT_EXAM_TYPE_NAME
        defaultExamTypeShouldBeFound("examTypeName.equals=" + DEFAULT_EXAM_TYPE_NAME);

        // Get all the examTypeList where examTypeName equals to UPDATED_EXAM_TYPE_NAME
        defaultExamTypeShouldNotBeFound("examTypeName.equals=" + UPDATED_EXAM_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllExamTypesByExamTypeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        examTypeRepository.saveAndFlush(examType);

        // Get all the examTypeList where examTypeName not equals to DEFAULT_EXAM_TYPE_NAME
        defaultExamTypeShouldNotBeFound("examTypeName.notEquals=" + DEFAULT_EXAM_TYPE_NAME);

        // Get all the examTypeList where examTypeName not equals to UPDATED_EXAM_TYPE_NAME
        defaultExamTypeShouldBeFound("examTypeName.notEquals=" + UPDATED_EXAM_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllExamTypesByExamTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        examTypeRepository.saveAndFlush(examType);

        // Get all the examTypeList where examTypeName in DEFAULT_EXAM_TYPE_NAME or UPDATED_EXAM_TYPE_NAME
        defaultExamTypeShouldBeFound("examTypeName.in=" + DEFAULT_EXAM_TYPE_NAME + "," + UPDATED_EXAM_TYPE_NAME);

        // Get all the examTypeList where examTypeName equals to UPDATED_EXAM_TYPE_NAME
        defaultExamTypeShouldNotBeFound("examTypeName.in=" + UPDATED_EXAM_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllExamTypesByExamTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        examTypeRepository.saveAndFlush(examType);

        // Get all the examTypeList where examTypeName is not null
        defaultExamTypeShouldBeFound("examTypeName.specified=true");

        // Get all the examTypeList where examTypeName is null
        defaultExamTypeShouldNotBeFound("examTypeName.specified=false");
    }
                @Test
    @Transactional
    public void getAllExamTypesByExamTypeNameContainsSomething() throws Exception {
        // Initialize the database
        examTypeRepository.saveAndFlush(examType);

        // Get all the examTypeList where examTypeName contains DEFAULT_EXAM_TYPE_NAME
        defaultExamTypeShouldBeFound("examTypeName.contains=" + DEFAULT_EXAM_TYPE_NAME);

        // Get all the examTypeList where examTypeName contains UPDATED_EXAM_TYPE_NAME
        defaultExamTypeShouldNotBeFound("examTypeName.contains=" + UPDATED_EXAM_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllExamTypesByExamTypeNameNotContainsSomething() throws Exception {
        // Initialize the database
        examTypeRepository.saveAndFlush(examType);

        // Get all the examTypeList where examTypeName does not contain DEFAULT_EXAM_TYPE_NAME
        defaultExamTypeShouldNotBeFound("examTypeName.doesNotContain=" + DEFAULT_EXAM_TYPE_NAME);

        // Get all the examTypeList where examTypeName does not contain UPDATED_EXAM_TYPE_NAME
        defaultExamTypeShouldBeFound("examTypeName.doesNotContain=" + UPDATED_EXAM_TYPE_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExamTypeShouldBeFound(String filter) throws Exception {
        restExamTypeMockMvc.perform(get("/api/exam-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examType.getId().intValue())))
            .andExpect(jsonPath("$.[*].examTypeCode").value(hasItem(DEFAULT_EXAM_TYPE_CODE)))
            .andExpect(jsonPath("$.[*].examTypeName").value(hasItem(DEFAULT_EXAM_TYPE_NAME)));

        // Check, that the count call also returns 1
        restExamTypeMockMvc.perform(get("/api/exam-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExamTypeShouldNotBeFound(String filter) throws Exception {
        restExamTypeMockMvc.perform(get("/api/exam-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExamTypeMockMvc.perform(get("/api/exam-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingExamType() throws Exception {
        // Get the examType
        restExamTypeMockMvc.perform(get("/api/exam-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamType() throws Exception {
        // Initialize the database
        examTypeService.save(examType);

        int databaseSizeBeforeUpdate = examTypeRepository.findAll().size();

        // Update the examType
        ExamType updatedExamType = examTypeRepository.findById(examType.getId()).get();
        // Disconnect from session so that the updates on updatedExamType are not directly saved in db
        em.detach(updatedExamType);
        updatedExamType
            .examTypeCode(UPDATED_EXAM_TYPE_CODE)
            .examTypeName(UPDATED_EXAM_TYPE_NAME);

        restExamTypeMockMvc.perform(put("/api/exam-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedExamType)))
            .andExpect(status().isOk());

        // Validate the ExamType in the database
        List<ExamType> examTypeList = examTypeRepository.findAll();
        assertThat(examTypeList).hasSize(databaseSizeBeforeUpdate);
        ExamType testExamType = examTypeList.get(examTypeList.size() - 1);
        assertThat(testExamType.getExamTypeCode()).isEqualTo(UPDATED_EXAM_TYPE_CODE);
        assertThat(testExamType.getExamTypeName()).isEqualTo(UPDATED_EXAM_TYPE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingExamType() throws Exception {
        int databaseSizeBeforeUpdate = examTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamTypeMockMvc.perform(put("/api/exam-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examType)))
            .andExpect(status().isBadRequest());

        // Validate the ExamType in the database
        List<ExamType> examTypeList = examTypeRepository.findAll();
        assertThat(examTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExamType() throws Exception {
        // Initialize the database
        examTypeService.save(examType);

        int databaseSizeBeforeDelete = examTypeRepository.findAll().size();

        // Delete the examType
        restExamTypeMockMvc.perform(delete("/api/exam-types/{id}", examType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExamType> examTypeList = examTypeRepository.findAll();
        assertThat(examTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
