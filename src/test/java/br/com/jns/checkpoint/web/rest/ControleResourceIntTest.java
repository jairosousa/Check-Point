package br.com.jns.checkpoint.web.rest;

import br.com.jns.checkpoint.CheckpointApp;

import br.com.jns.checkpoint.domain.Controle;
import br.com.jns.checkpoint.repository.ControleRepository;
import br.com.jns.checkpoint.repository.search.ControleSearchRepository;
import br.com.jns.checkpoint.service.ControleService;
import br.com.jns.checkpoint.service.dto.ControleDTO;
import br.com.jns.checkpoint.service.mapper.ControleMapper;
import br.com.jns.checkpoint.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static br.com.jns.checkpoint.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ControleResource REST controller.
 *
 * @see ControleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CheckpointApp.class)
public class ControleResourceIntTest {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HR_ENTRADA = "AAAAAAAAAA";
    private static final String UPDATED_HR_ENTRADA = "BBBBBBBBBB";

    private static final String DEFAULT_HR_ALMOCO_SAIDA = "AAAAAAAAAA";
    private static final String UPDATED_HR_ALMOCO_SAIDA = "BBBBBBBBBB";

    private static final String DEFAULT_HR_ALMOCO_RETORNO = "AAAAAAAAAA";
    private static final String UPDATED_HR_ALMOCO_RETORNO = "BBBBBBBBBB";

    private static final String DEFAULT_HR_SAIDA = "AAAAAAAAAA";
    private static final String UPDATED_HR_SAIDA = "BBBBBBBBBB";

    private static final String DEFAULT_COR_PULSEIRA = "AAAAAAAAAA";
    private static final String UPDATED_COR_PULSEIRA = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    @Autowired
    private ControleRepository controleRepository;

    @Autowired
    private ControleMapper controleMapper;

    @Autowired
    private ControleService controleService;

    /**
     * This repository is mocked in the br.com.jns.checkpoint.repository.search test package.
     *
     * @see br.com.jns.checkpoint.repository.search.ControleSearchRepositoryMockConfiguration
     */
    @Autowired
    private ControleSearchRepository mockControleSearchRepository;

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

    private MockMvc restControleMockMvc;

    private Controle controle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ControleResource controleResource = new ControleResource(controleService);
        this.restControleMockMvc = MockMvcBuilders.standaloneSetup(controleResource)
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
    public static Controle createEntity(EntityManager em) {
        Controle controle = new Controle()
            .data(DEFAULT_DATA)
            .hrEntrada(DEFAULT_HR_ENTRADA)
            .hrAlmocoSaida(DEFAULT_HR_ALMOCO_SAIDA)
            .hrAlmocoRetorno(DEFAULT_HR_ALMOCO_RETORNO)
            .hrSaida(DEFAULT_HR_SAIDA)
            .corPulseira(DEFAULT_COR_PULSEIRA)
            .observacao(DEFAULT_OBSERVACAO);
        return controle;
    }

    @Before
    public void initTest() {
        controle = createEntity(em);
    }

    @Test
    @Transactional
    public void createControle() throws Exception {
        int databaseSizeBeforeCreate = controleRepository.findAll().size();

        // Create the Controle
        ControleDTO controleDTO = controleMapper.toDto(controle);
        restControleMockMvc.perform(post("/api/controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controleDTO)))
            .andExpect(status().isCreated());

        // Validate the Controle in the database
        List<Controle> controleList = controleRepository.findAll();
        assertThat(controleList).hasSize(databaseSizeBeforeCreate + 1);
        Controle testControle = controleList.get(controleList.size() - 1);
        assertThat(testControle.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testControle.getHrEntrada()).isEqualTo(DEFAULT_HR_ENTRADA);
        assertThat(testControle.getHrAlmocoSaida()).isEqualTo(DEFAULT_HR_ALMOCO_SAIDA);
        assertThat(testControle.getHrAlmocoRetorno()).isEqualTo(DEFAULT_HR_ALMOCO_RETORNO);
        assertThat(testControle.getHrSaida()).isEqualTo(DEFAULT_HR_SAIDA);
        assertThat(testControle.getCorPulseira()).isEqualTo(DEFAULT_COR_PULSEIRA);
        assertThat(testControle.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);

        // Validate the Controle in Elasticsearch
        verify(mockControleSearchRepository, times(1)).save(testControle);
    }

    @Test
    @Transactional
    public void createControleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = controleRepository.findAll().size();

        // Create the Controle with an existing ID
        controle.setId(1L);
        ControleDTO controleDTO = controleMapper.toDto(controle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restControleMockMvc.perform(post("/api/controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Controle in the database
        List<Controle> controleList = controleRepository.findAll();
        assertThat(controleList).hasSize(databaseSizeBeforeCreate);

        // Validate the Controle in Elasticsearch
        verify(mockControleSearchRepository, times(0)).save(controle);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = controleRepository.findAll().size();
        // set the field null
        controle.setData(null);

        // Create the Controle, which fails.
        ControleDTO controleDTO = controleMapper.toDto(controle);

        restControleMockMvc.perform(post("/api/controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controleDTO)))
            .andExpect(status().isBadRequest());

        List<Controle> controleList = controleRepository.findAll();
        assertThat(controleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHrEntradaIsRequired() throws Exception {
        int databaseSizeBeforeTest = controleRepository.findAll().size();
        // set the field null
        controle.setHrEntrada(null);

        // Create the Controle, which fails.
        ControleDTO controleDTO = controleMapper.toDto(controle);

        restControleMockMvc.perform(post("/api/controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controleDTO)))
            .andExpect(status().isBadRequest());

        List<Controle> controleList = controleRepository.findAll();
        assertThat(controleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllControles() throws Exception {
        // Initialize the database
        controleRepository.saveAndFlush(controle);

        // Get all the controleList
        restControleMockMvc.perform(get("/api/controles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controle.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].hrEntrada").value(hasItem(DEFAULT_HR_ENTRADA.toString())))
            .andExpect(jsonPath("$.[*].hrAlmocoSaida").value(hasItem(DEFAULT_HR_ALMOCO_SAIDA.toString())))
            .andExpect(jsonPath("$.[*].hrAlmocoRetorno").value(hasItem(DEFAULT_HR_ALMOCO_RETORNO.toString())))
            .andExpect(jsonPath("$.[*].hrSaida").value(hasItem(DEFAULT_HR_SAIDA.toString())))
            .andExpect(jsonPath("$.[*].corPulseira").value(hasItem(DEFAULT_COR_PULSEIRA.toString())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getControle() throws Exception {
        // Initialize the database
        controleRepository.saveAndFlush(controle);

        // Get the controle
        restControleMockMvc.perform(get("/api/controles/{id}", controle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(controle.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.hrEntrada").value(DEFAULT_HR_ENTRADA.toString()))
            .andExpect(jsonPath("$.hrAlmocoSaida").value(DEFAULT_HR_ALMOCO_SAIDA.toString()))
            .andExpect(jsonPath("$.hrAlmocoRetorno").value(DEFAULT_HR_ALMOCO_RETORNO.toString()))
            .andExpect(jsonPath("$.hrSaida").value(DEFAULT_HR_SAIDA.toString()))
            .andExpect(jsonPath("$.corPulseira").value(DEFAULT_COR_PULSEIRA.toString()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingControle() throws Exception {
        // Get the controle
        restControleMockMvc.perform(get("/api/controles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateControle() throws Exception {
        // Initialize the database
        controleRepository.saveAndFlush(controle);

        int databaseSizeBeforeUpdate = controleRepository.findAll().size();

        // Update the controle
        Controle updatedControle = controleRepository.findById(controle.getId()).get();
        // Disconnect from session so that the updates on updatedControle are not directly saved in db
        em.detach(updatedControle);
        updatedControle
            .data(UPDATED_DATA)
            .hrEntrada(UPDATED_HR_ENTRADA)
            .hrAlmocoSaida(UPDATED_HR_ALMOCO_SAIDA)
            .hrAlmocoRetorno(UPDATED_HR_ALMOCO_RETORNO)
            .hrSaida(UPDATED_HR_SAIDA)
            .corPulseira(UPDATED_COR_PULSEIRA)
            .observacao(UPDATED_OBSERVACAO);
        ControleDTO controleDTO = controleMapper.toDto(updatedControle);

        restControleMockMvc.perform(put("/api/controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controleDTO)))
            .andExpect(status().isOk());

        // Validate the Controle in the database
        List<Controle> controleList = controleRepository.findAll();
        assertThat(controleList).hasSize(databaseSizeBeforeUpdate);
        Controle testControle = controleList.get(controleList.size() - 1);
        assertThat(testControle.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testControle.getHrEntrada()).isEqualTo(UPDATED_HR_ENTRADA);
        assertThat(testControle.getHrAlmocoSaida()).isEqualTo(UPDATED_HR_ALMOCO_SAIDA);
        assertThat(testControle.getHrAlmocoRetorno()).isEqualTo(UPDATED_HR_ALMOCO_RETORNO);
        assertThat(testControle.getHrSaida()).isEqualTo(UPDATED_HR_SAIDA);
        assertThat(testControle.getCorPulseira()).isEqualTo(UPDATED_COR_PULSEIRA);
        assertThat(testControle.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);

        // Validate the Controle in Elasticsearch
        verify(mockControleSearchRepository, times(1)).save(testControle);
    }

    @Test
    @Transactional
    public void updateNonExistingControle() throws Exception {
        int databaseSizeBeforeUpdate = controleRepository.findAll().size();

        // Create the Controle
        ControleDTO controleDTO = controleMapper.toDto(controle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControleMockMvc.perform(put("/api/controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Controle in the database
        List<Controle> controleList = controleRepository.findAll();
        assertThat(controleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Controle in Elasticsearch
        verify(mockControleSearchRepository, times(0)).save(controle);
    }

    @Test
    @Transactional
    public void deleteControle() throws Exception {
        // Initialize the database
        controleRepository.saveAndFlush(controle);

        int databaseSizeBeforeDelete = controleRepository.findAll().size();

        // Get the controle
        restControleMockMvc.perform(delete("/api/controles/{id}", controle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Controle> controleList = controleRepository.findAll();
        assertThat(controleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Controle in Elasticsearch
        verify(mockControleSearchRepository, times(1)).deleteById(controle.getId());
    }

    @Test
    @Transactional
    public void searchControle() throws Exception {
        // Initialize the database
        controleRepository.saveAndFlush(controle);
        when(mockControleSearchRepository.search(queryStringQuery("id:" + controle.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(controle), PageRequest.of(0, 1), 1));
        // Search the controle
        restControleMockMvc.perform(get("/api/_search/controles?query=id:" + controle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controle.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].hrEntrada").value(hasItem(DEFAULT_HR_ENTRADA)))
            .andExpect(jsonPath("$.[*].hrAlmocoSaida").value(hasItem(DEFAULT_HR_ALMOCO_SAIDA)))
            .andExpect(jsonPath("$.[*].hrAlmocoRetorno").value(hasItem(DEFAULT_HR_ALMOCO_RETORNO)))
            .andExpect(jsonPath("$.[*].hrSaida").value(hasItem(DEFAULT_HR_SAIDA)))
            .andExpect(jsonPath("$.[*].corPulseira").value(hasItem(DEFAULT_COR_PULSEIRA)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Controle.class);
        Controle controle1 = new Controle();
        controle1.setId(1L);
        Controle controle2 = new Controle();
        controle2.setId(controle1.getId());
        assertThat(controle1).isEqualTo(controle2);
        controle2.setId(2L);
        assertThat(controle1).isNotEqualTo(controle2);
        controle1.setId(null);
        assertThat(controle1).isNotEqualTo(controle2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControleDTO.class);
        ControleDTO controleDTO1 = new ControleDTO();
        controleDTO1.setId(1L);
        ControleDTO controleDTO2 = new ControleDTO();
        assertThat(controleDTO1).isNotEqualTo(controleDTO2);
        controleDTO2.setId(controleDTO1.getId());
        assertThat(controleDTO1).isEqualTo(controleDTO2);
        controleDTO2.setId(2L);
        assertThat(controleDTO1).isNotEqualTo(controleDTO2);
        controleDTO1.setId(null);
        assertThat(controleDTO1).isNotEqualTo(controleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(controleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(controleMapper.fromId(null)).isNull();
    }
}
