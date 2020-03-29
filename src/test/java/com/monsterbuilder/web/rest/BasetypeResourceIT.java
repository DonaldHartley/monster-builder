package com.monsterbuilder.web.rest;

import com.monsterbuilder.MonsterbuilderApp;
import com.monsterbuilder.domain.Basetype;
import com.monsterbuilder.repository.BasetypeRepository;
import com.monsterbuilder.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.monsterbuilder.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BasetypeResource} REST controller.
 */
@SpringBootTest(classes = MonsterbuilderApp.class)
public class BasetypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BasetypeRepository basetypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBasetypeMockMvc;

    private Basetype basetype;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BasetypeResource basetypeResource = new BasetypeResource(basetypeRepository);
        this.restBasetypeMockMvc = MockMvcBuilders.standaloneSetup(basetypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Basetype createEntity(EntityManager em) {
        Basetype basetype = new Basetype()
            .name(DEFAULT_NAME);
        return basetype;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Basetype createUpdatedEntity(EntityManager em) {
        Basetype basetype = new Basetype()
            .name(UPDATED_NAME);
        return basetype;
    }

    @BeforeEach
    public void initTest() {
        basetype = createEntity(em);
    }

    @Test
    @Transactional
    public void createBasetype() throws Exception {
        int databaseSizeBeforeCreate = basetypeRepository.findAll().size();

        // Create the Basetype
        restBasetypeMockMvc.perform(post("/api/basetypes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(basetype)))
            .andExpect(status().isCreated());

        // Validate the Basetype in the database
        List<Basetype> basetypeList = basetypeRepository.findAll();
        assertThat(basetypeList).hasSize(databaseSizeBeforeCreate + 1);
        Basetype testBasetype = basetypeList.get(basetypeList.size() - 1);
        assertThat(testBasetype.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBasetypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = basetypeRepository.findAll().size();

        // Create the Basetype with an existing ID
        basetype.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBasetypeMockMvc.perform(post("/api/basetypes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(basetype)))
            .andExpect(status().isBadRequest());

        // Validate the Basetype in the database
        List<Basetype> basetypeList = basetypeRepository.findAll();
        assertThat(basetypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = basetypeRepository.findAll().size();
        // set the field null
        basetype.setName(null);

        // Create the Basetype, which fails.

        restBasetypeMockMvc.perform(post("/api/basetypes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(basetype)))
            .andExpect(status().isBadRequest());

        List<Basetype> basetypeList = basetypeRepository.findAll();
        assertThat(basetypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBasetypes() throws Exception {
        // Initialize the database
        basetypeRepository.saveAndFlush(basetype);

        // Get all the basetypeList
        restBasetypeMockMvc.perform(get("/api/basetypes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(basetype.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getBasetype() throws Exception {
        // Initialize the database
        basetypeRepository.saveAndFlush(basetype);

        // Get the basetype
        restBasetypeMockMvc.perform(get("/api/basetypes/{id}", basetype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(basetype.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingBasetype() throws Exception {
        // Get the basetype
        restBasetypeMockMvc.perform(get("/api/basetypes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBasetype() throws Exception {
        // Initialize the database
        basetypeRepository.saveAndFlush(basetype);

        int databaseSizeBeforeUpdate = basetypeRepository.findAll().size();

        // Update the basetype
        Basetype updatedBasetype = basetypeRepository.findById(basetype.getId()).get();
        // Disconnect from session so that the updates on updatedBasetype are not directly saved in db
        em.detach(updatedBasetype);
        updatedBasetype
            .name(UPDATED_NAME);

        restBasetypeMockMvc.perform(put("/api/basetypes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBasetype)))
            .andExpect(status().isOk());

        // Validate the Basetype in the database
        List<Basetype> basetypeList = basetypeRepository.findAll();
        assertThat(basetypeList).hasSize(databaseSizeBeforeUpdate);
        Basetype testBasetype = basetypeList.get(basetypeList.size() - 1);
        assertThat(testBasetype.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBasetype() throws Exception {
        int databaseSizeBeforeUpdate = basetypeRepository.findAll().size();

        // Create the Basetype

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBasetypeMockMvc.perform(put("/api/basetypes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(basetype)))
            .andExpect(status().isBadRequest());

        // Validate the Basetype in the database
        List<Basetype> basetypeList = basetypeRepository.findAll();
        assertThat(basetypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBasetype() throws Exception {
        // Initialize the database
        basetypeRepository.saveAndFlush(basetype);

        int databaseSizeBeforeDelete = basetypeRepository.findAll().size();

        // Delete the basetype
        restBasetypeMockMvc.perform(delete("/api/basetypes/{id}", basetype.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Basetype> basetypeList = basetypeRepository.findAll();
        assertThat(basetypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
