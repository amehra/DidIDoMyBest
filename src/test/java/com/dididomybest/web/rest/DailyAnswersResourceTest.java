package com.dididomybest.web.rest;

import com.dididomybest.Application;
import com.dididomybest.domain.DailyAnswers;
import com.dididomybest.repository.DailyAnswersRepository;
import com.dididomybest.web.rest.dto.DailyAnswersDTO;
import com.dididomybest.web.rest.mapper.DailyAnswersMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the DailyAnswersResource REST controller.
 *
 * @see DailyAnswersResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DailyAnswersResourceTest {


    private static final Integer DEFAULT_VALUE = 0;
    private static final Integer UPDATED_VALUE = 1;
    private static final String DEFAULT_NOTES = "AAAAA";
    private static final String UPDATED_NOTES = "BBBBB";

    @Inject
    private DailyAnswersRepository dailyAnswersRepository;

    @Inject
    private DailyAnswersMapper dailyAnswersMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDailyAnswersMockMvc;

    private DailyAnswers dailyAnswers;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DailyAnswersResource dailyAnswersResource = new DailyAnswersResource();
        ReflectionTestUtils.setField(dailyAnswersResource, "dailyAnswersRepository", dailyAnswersRepository);
        ReflectionTestUtils.setField(dailyAnswersResource, "dailyAnswersMapper", dailyAnswersMapper);
        this.restDailyAnswersMockMvc = MockMvcBuilders.standaloneSetup(dailyAnswersResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dailyAnswers = new DailyAnswers();
        dailyAnswers.setValue(DEFAULT_VALUE);
        dailyAnswers.setNotes(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createDailyAnswers() throws Exception {
        int databaseSizeBeforeCreate = dailyAnswersRepository.findAll().size();

        // Create the DailyAnswers
        DailyAnswersDTO dailyAnswersDTO = dailyAnswersMapper.dailyAnswersToDailyAnswersDTO(dailyAnswers);

        restDailyAnswersMockMvc.perform(post("/api/dailyAnswerss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dailyAnswersDTO)))
                .andExpect(status().isCreated());

        // Validate the DailyAnswers in the database
        List<DailyAnswers> dailyAnswerss = dailyAnswersRepository.findAll();
        assertThat(dailyAnswerss).hasSize(databaseSizeBeforeCreate + 1);
        DailyAnswers testDailyAnswers = dailyAnswerss.get(dailyAnswerss.size() - 1);
        assertThat(testDailyAnswers.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testDailyAnswers.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyAnswersRepository.findAll().size();
        // set the field null
        dailyAnswers.setValue(null);

        // Create the DailyAnswers, which fails.
        DailyAnswersDTO dailyAnswersDTO = dailyAnswersMapper.dailyAnswersToDailyAnswersDTO(dailyAnswers);

        restDailyAnswersMockMvc.perform(post("/api/dailyAnswerss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dailyAnswersDTO)))
                .andExpect(status().isBadRequest());

        List<DailyAnswers> dailyAnswerss = dailyAnswersRepository.findAll();
        assertThat(dailyAnswerss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDailyAnswerss() throws Exception {
        // Initialize the database
        dailyAnswersRepository.saveAndFlush(dailyAnswers);

        // Get all the dailyAnswerss
        restDailyAnswersMockMvc.perform(get("/api/dailyAnswerss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dailyAnswers.getId().intValue())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
                .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getDailyAnswers() throws Exception {
        // Initialize the database
        dailyAnswersRepository.saveAndFlush(dailyAnswers);

        // Get the dailyAnswers
        restDailyAnswersMockMvc.perform(get("/api/dailyAnswerss/{id}", dailyAnswers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dailyAnswers.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDailyAnswers() throws Exception {
        // Get the dailyAnswers
        restDailyAnswersMockMvc.perform(get("/api/dailyAnswerss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDailyAnswers() throws Exception {
        // Initialize the database
        dailyAnswersRepository.saveAndFlush(dailyAnswers);

		int databaseSizeBeforeUpdate = dailyAnswersRepository.findAll().size();

        // Update the dailyAnswers
        dailyAnswers.setValue(UPDATED_VALUE);
        dailyAnswers.setNotes(UPDATED_NOTES);
        DailyAnswersDTO dailyAnswersDTO = dailyAnswersMapper.dailyAnswersToDailyAnswersDTO(dailyAnswers);

        restDailyAnswersMockMvc.perform(put("/api/dailyAnswerss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dailyAnswersDTO)))
                .andExpect(status().isOk());

        // Validate the DailyAnswers in the database
        List<DailyAnswers> dailyAnswerss = dailyAnswersRepository.findAll();
        assertThat(dailyAnswerss).hasSize(databaseSizeBeforeUpdate);
        DailyAnswers testDailyAnswers = dailyAnswerss.get(dailyAnswerss.size() - 1);
        assertThat(testDailyAnswers.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testDailyAnswers.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void deleteDailyAnswers() throws Exception {
        // Initialize the database
        dailyAnswersRepository.saveAndFlush(dailyAnswers);

		int databaseSizeBeforeDelete = dailyAnswersRepository.findAll().size();

        // Get the dailyAnswers
        restDailyAnswersMockMvc.perform(delete("/api/dailyAnswerss/{id}", dailyAnswers.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DailyAnswers> dailyAnswerss = dailyAnswersRepository.findAll();
        assertThat(dailyAnswerss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
