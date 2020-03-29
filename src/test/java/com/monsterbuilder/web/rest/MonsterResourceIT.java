package com.monsterbuilder.web.rest;

import com.monsterbuilder.MonsterbuilderApp;
import com.monsterbuilder.domain.Monster;
import com.monsterbuilder.repository.MonsterRepository;
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
 * Integration tests for the {@link MonsterResource} REST controller.
 */
@SpringBootTest(classes = MonsterbuilderApp.class)
public class MonsterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATOR_ID = 1L;
    private static final Long UPDATED_CREATOR_ID = 2L;

    @Autowired
    private MonsterRepository monsterRepository;

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

    private MockMvc restMonsterMockMvc;

    private Monster monster;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MonsterResource monsterResource = new MonsterResource(monsterRepository);
        this.restMonsterMockMvc = MockMvcBuilders.standaloneSetup(monsterResource)
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
    public static Monster createEntity(EntityManager em) {
        Monster monster = new Monster()
            .name(DEFAULT_NAME)
            .creatorId(DEFAULT_CREATOR_ID);
        return monster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Monster createUpdatedEntity(EntityManager em) {
        Monster monster = new Monster()
            .name(UPDATED_NAME)
            .creatorId(UPDATED_CREATOR_ID);
        return monster;
    }

    @BeforeEach
    public void initTest() {
        monster = createEntity(em);
    }

    @Test
    @Transactional
    public void createMonster() throws Exception {
        int databaseSizeBeforeCreate = monsterRepository.findAll().size();

        // Create the Monster
        restMonsterMockMvc.perform(post("/api/monsters")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(monster)))
            .andExpect(status().isCreated());

        // Validate the Monster in the database
        List<Monster> monsterList = monsterRepository.findAll();
        assertThat(monsterList).hasSize(databaseSizeBeforeCreate + 1);
        Monster testMonster = monsterList.get(monsterList.size() - 1);
        assertThat(testMonster.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMonster.getCreatorId()).isEqualTo(DEFAULT_CREATOR_ID);
    }

    @Test
    @Transactional
    public void createMonsterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = monsterRepository.findAll().size();

        // Create the Monster with an existing ID
        monster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonsterMockMvc.perform(post("/api/monsters")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(monster)))
            .andExpect(status().isBadRequest());

        // Validate the Monster in the database
        List<Monster> monsterList = monsterRepository.findAll();
        assertThat(monsterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = monsterRepository.findAll().size();
        // set the field null
        monster.setName(null);

        // Create the Monster, which fails.

        restMonsterMockMvc.perform(post("/api/monsters")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(monster)))
            .andExpect(status().isBadRequest());

        List<Monster> monsterList = monsterRepository.findAll();
        assertThat(monsterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMonsters() throws Exception {
        // Initialize the database
        monsterRepository.saveAndFlush(monster);

        // Get all the monsterList
        restMonsterMockMvc.perform(get("/api/monsters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].creatorId").value(hasItem(DEFAULT_CREATOR_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getMonster() throws Exception {
        // Initialize the database
        monsterRepository.saveAndFlush(monster);

        // Get the monster
        restMonsterMockMvc.perform(get("/api/monsters/{id}", monster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(monster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.creatorId").value(DEFAULT_CREATOR_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMonster() throws Exception {
        // Get the monster
        restMonsterMockMvc.perform(get("/api/monsters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMonster() throws Exception {
        // Initialize the database
        monsterRepository.saveAndFlush(monster);

        int databaseSizeBeforeUpdate = monsterRepository.findAll().size();

        // Update the monster
        Monster updatedMonster = monsterRepository.findById(monster.getId()).get();
        // Disconnect from session so that the updates on updatedMonster are not directly saved in db
        em.detach(updatedMonster);
        updatedMonster
            .name(UPDATED_NAME)
            .creatorId(UPDATED_CREATOR_ID);

        restMonsterMockMvc.perform(put("/api/monsters")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMonster)))
            .andExpect(status().isOk());

        // Validate the Monster in the database
        List<Monster> monsterList = monsterRepository.findAll();
        assertThat(monsterList).hasSize(databaseSizeBeforeUpdate);
        Monster testMonster = monsterList.get(monsterList.size() - 1);
        assertThat(testMonster.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMonster.getCreatorId()).isEqualTo(UPDATED_CREATOR_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingMonster() throws Exception {
        int databaseSizeBeforeUpdate = monsterRepository.findAll().size();

        // Create the Monster

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonsterMockMvc.perform(put("/api/monsters")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(monster)))
            .andExpect(status().isBadRequest());

        // Validate the Monster in the database
        List<Monster> monsterList = monsterRepository.findAll();
        assertThat(monsterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMonster() throws Exception {
        // Initialize the database
        monsterRepository.saveAndFlush(monster);

        int databaseSizeBeforeDelete = monsterRepository.findAll().size();

        // Delete the monster
        restMonsterMockMvc.perform(delete("/api/monsters/{id}", monster.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Monster> monsterList = monsterRepository.findAll();
        assertThat(monsterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
